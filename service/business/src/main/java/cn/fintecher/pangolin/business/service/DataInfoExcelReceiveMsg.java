package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.repository.ProductRepository;
import cn.fintecher.pangolin.business.repository.ProductSeriesRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.ConfirmDataInfoMessage;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author: PeiShouWen
 * @Description: 接收案件确认的消息处理类
 * @Date 15:22 2017/7/24
 */
@Component("dataInfoExcelReceiveMsg")
@RabbitListener(queues = Constants.DATAINFO_CONFIRM_QE)
public class DataInfoExcelReceiveMsg {

    private final Logger logger = LoggerFactory.getLogger(DataInfoExcelReceiveMsg.class);
    @Autowired
    ProcessDataInfoExcelService processDataInfoExcelService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProductSeriesRepository productSeriesRepository;
    @Autowired
    ProductRepository productRepository;
    //待分配案件MAP KEY :客户名称_身份证号_委托方CODE_公司码
    ConcurrentHashMap<String, DataInfoExcelModel> dataInfoExcelModelMap = new ConcurrentHashMap<>();

    /**
     * 接收案件确认数据(多线程的方式处理数据)
     *
     * @param confirmDataInfoMessage
     */
    @RabbitHandler
    public void receiveMsg(ConfirmDataInfoMessage confirmDataInfoMessage) {
        logger.info("收到案件确认消息数量 {}", confirmDataInfoMessage.getDataCount());
        List<DataInfoExcelModel> dataInfoExcelModelList = confirmDataInfoMessage.getDataInfoExcelModelList();
        overCase(dataInfoExcelModelList, confirmDataInfoMessage.getUser());
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setTitle("案件确认后数据查看");
        logger.debug("案件确认后发送消息");
        sendReminderMessage.setUserId(confirmDataInfoMessage.getUser().getId());
        sendReminderMessage.setRemindTime(ZWDateUtil.getNowDateTime());
        sendReminderMessage.setContent("案件确认完毕，请前往待分配案件界面查看。");
        sendReminderMessage.setType(ReminderType.FLLOWUP);
        sendReminderMessage.setMode(ReminderMode.POPUP);
        restTemplate.postForLocation("http://reminder-service/api/reminderCalendars", sendReminderMessage);
    }

    public void overCase(List<DataInfoExcelModel> dataInfoExcelModelList, User user) {
        if(!dataInfoExcelModelList.isEmpty()){
            List<String> caseNumberList = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("申请号为");
            StringBuilder stringBuilder3 = new StringBuilder();
            try {
                DataInfoExcelModel dataInfoExcelModelz = dataInfoExcelModelList.get(0);
                if (ZWStringUtils.isEmpty(dataInfoExcelModelz.getCaseStatus())) {
                    List<Product> products = productRepository.findAll();
                    List<String> strings = products.stream().map(product -> {
                        return product.getProductSeries().getSeriesName() + "_" + product.getProductName();
                    }).collect(Collectors.toList());
                    Set<String> stringSet = dataInfoExcelModelList.stream().map(dataInfoExcelModel -> {
                        return (StringUtils.isEmpty(dataInfoExcelModel.getProductSeriesName()) ? "未知" : dataInfoExcelModel.getProductSeriesName()) + "_" + (StringUtils.isEmpty(dataInfoExcelModel.getProductName()) ? "未知" : dataInfoExcelModel.getProductName());
                    }).collect(Collectors.toSet());
                    List<String> stringList = new ArrayList<>();
                    if (strings.size() == 0) {
                        stringList.addAll(stringSet);
                    } else {
                        stringList = Lists.newArrayList(Iterables.filter(stringSet, input -> !strings.contains(input)));
                    }
                    if (stringList.size() != 0) {
                        addProducts(stringList, dataInfoExcelModelList.get(0), user);
                    }
                    dataInfoExcelModelMap.clear();
                    if (dataInfoExcelModelList.size() <= 1000) {
                        for (int i = 0; i < dataInfoExcelModelList.size(); i++) {
                            processDataInfoExcelService.doTask(dataInfoExcelModelList.get(i), dataInfoExcelModelMap, user, i);
                        }
                    } else {
                        List<DataInfoExcelModel> all = dataInfoExcelModelList.subList(0, 1000);
                        if (all.size() > 0) {
                            for (int i = 0; i < all.size(); i++) {
                                processDataInfoExcelService.doTask(all.get(i), dataInfoExcelModelMap, user, i);
                            }
                            dataInfoExcelModelList.removeAll(all);
                        }
                        overCase(dataInfoExcelModelList, user);
                    }
                } else {
                    for (int i = 0; i < dataInfoExcelModelList.size(); i++) {
                        String caseNumber = processDataInfoExcelService.doNewTask(dataInfoExcelModelList.get(i), user, i);
                        caseNumberList.add(caseNumber);
                    }
                    int i = 0;
                    int j = caseNumberList.size();
                    for (String str : caseNumberList) {
                        if (!ZWStringUtils.isEmpty(str.trim())) {
                            stringBuilder3.append(str.toString() + ",");
                            i++;
                        }
                    }
                    if (j - i > 0) {
                        stringBuilder.append(stringBuilder3 + "的停催特殊案件，导入分案失败。");
                        sendMessage(user, stringBuilder);
                    } else {
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("停催特殊案件，导入分案成功。");
                        sendMessage(user, stringBuilder1);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("案件确认失败");
            }
        }
    }

    private void addProducts(List<String> strings, DataInfoExcelModel dataInfoExcelModel, User user) {
        for (String s : strings) {
            String[] sa = s.split("_");
            String productSeriesName = sa[0];
            String productName = sa[1];
            ProductSeries productSeries = productSeriesRepository.findOne(QProductSeries.productSeries.seriesName.eq(productSeriesName));
            if (Objects.isNull(productSeries)) {
                productSeries = new ProductSeries();
                productSeries.setSeriesName(productSeriesName);
                productSeries.setOperator(user.getId());
                productSeries.setOperatorTime(ZWDateUtil.getNowDateTime());
                productSeries.setPrincipal_id(dataInfoExcelModel.getPrinCode());
                productSeries.setCompanyCode(dataInfoExcelModel.getCompanyCode());
                productSeries = productSeriesRepository.save(productSeries);
            }
            Product product = productRepository.findOne(QProduct.product.productName.eq(productName).and(QProduct.product.productSeries.seriesName.eq(productSeriesName)));
            if (Objects.isNull(product)) {
                product = new Product();
                product.setProductName(productName);
                product.setOperator(user.getId());
                product.setOperatorTime(ZWDateUtil.getNowDateTime());
                product.setCompanyCode(dataInfoExcelModel.getCompanyCode());
                product.setProductSeries(productSeries);
                product = productRepository.save(product);
            }
        }
    }

    public void sendMessage(User user, StringBuilder stringBuilder) {
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setTitle("案件确认后数据查看");
        sendReminderMessage.setUserId(user.getId());
        sendReminderMessage.setRemindTime(ZWDateUtil.getNowDateTime());
        sendReminderMessage.setContent(stringBuilder.toString());
        sendReminderMessage.setType(ReminderType.FLLOWUP);
        sendReminderMessage.setMode(ReminderMode.POPUP);
        restTemplate.postForLocation("http://reminder-service/api/reminderCalendars", sendReminderMessage);
    }
}
