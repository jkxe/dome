package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.BackPlanImportParams;
import cn.fintecher.pangolin.business.model.UploadUserBackcashPlanExcelModel;
import cn.fintecher.pangolin.business.repository.UserBackcashPlanRepository;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.entity.QUser;
import cn.fintecher.pangolin.entity.QUserBackcashPlan;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.UserBackcashPlan;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.util.CellError;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ExcelSheetObj;
import cn.fintecher.pangolin.entity.util.ExcelUtil;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-08-03-9:27
 */
@Service("userBackcashPlanExcelImportService")
public class UserBackcashPlanExcelImportService {
    private final static List<String>  isNeedTitle_work=new ArrayList<>();

    static {
        isNeedTitle_work.add("序号");
        isNeedTitle_work.add("用户名");
        isNeedTitle_work.add("姓名");
        isNeedTitle_work.add("年份");
        isNeedTitle_work.add("月份");
        isNeedTitle_work.add("回款金额(元)");

    }

    final Logger log = LoggerFactory.getLogger(UserBackcashPlanExcelImportService.class);
    @Autowired
    ParseExcelService parseExcelService;
    @Autowired
    private UserBackcashPlanRepository userBackcashPlanRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CellError> importExcelDataInfo(BackPlanImportParams params,UploadFile uploadFile) throws Exception {
        List<CellError> cellErrorList = null;
        if (Objects.isNull(uploadFile)) {
            throw new Exception("获取Excel数据失败");
        }
        //判断文件类型是否为Excel
        if (!Constants.EXCEL_TYPE_XLS.equals(uploadFile.getType()) && !Constants.EXCEL_TYPE_XLSX.equals(uploadFile.getType())) {
            throw new Exception("数据文件为非Excel数据");
        }
        Sheet excelSheet = null;
        try {
            excelSheet = parseExcelService.getExcelSheets(uploadFile);
            if (Objects.isNull(excelSheet)) {
                throw new RuntimeException("获取Excel对象错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取Excel对象错误");
        }
        try {
            parseExcelService.checkExcelHeader(excelSheet, 0, 0, isNeedTitle_work);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            //从文件服务器上获取Excel文件并解析：
            ExcelSheetObj excelSheetObj = ExcelUtil.parseExcelSingle(params.getLocalUrl(), params.getType(), params.getDataClass(), params.getStartRow(), params.getStartCol());
            List dataList = excelSheetObj.getDatasList();
            //导入错误信息
            cellErrorList = excelSheetObj.getCellErrorList();
            if (cellErrorList.isEmpty()) {
                //临时回款计划信息
                goalExcelImportStrategic(dataList, params.getUsernameList(), params.getUserPlan(), params.getOperator(), params.getCompanyCode(), cellErrorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return cellErrorList;
    }

    /**
     * Excel导入策略
     */
    public void goalExcelImportStrategic(List dataList, List<String> userList, List<UserBackcashPlan> userPlan, String operator, String companyCode, List<CellError> cellErrorList) throws Exception {

        // 将数据验证通过的存在List中（不存在计划的）
        List<UserBackcashPlan> passList = new ArrayList<>();
        // 将数据验证通过的存在List中（已经存在计划的）
        List<UserBackcashPlan> passPlanList = new ArrayList<>();

        List<String> usernamePlan = new ArrayList<>();
        try {
            if (!userPlan.isEmpty()) {
                for (UserBackcashPlan plan : userPlan) {
                    usernamePlan.add(plan.getUserName());
                }
            }
            for (Object goalObj : dataList) {
                UploadUserBackcashPlanExcelModel data = (UploadUserBackcashPlanExcelModel) goalObj;
                //验证年、月、回款金额数据的合法性
                if (!validityDataInfoExcel(cellErrorList, data, userList)) {
                    return;
                }
                // 验证用户名合法性
                if (!usernamePlan.isEmpty()) {
                    QUserBackcashPlan qUserBackcashPlan = QUserBackcashPlan.userBackcashPlan;
                    UserBackcashPlan userBackcashPlan = userBackcashPlanRepository.findOne(qUserBackcashPlan.userName.eq(data.getUserName()).and(qUserBackcashPlan.year.eq(data.getYear())).and(qUserBackcashPlan.month.eq(data.getMonth())));
                    // 要导入的某个用户已经存在
                    if (Objects.isNull(userBackcashPlan)) {
                        add(passList, data, operator, companyCode);
                    } else {
                        // 要导入的用户名、年份、月份已经存在就是修改
                        UserBackcashPlan userBackcashPlan1 = new UserBackcashPlan();
                        userBackcashPlan1.setId(userBackcashPlan.getId());
                        userBackcashPlan1.setUserName(data.getUserName());
                        userBackcashPlan1.setRealName(data.getRealName());
                        userBackcashPlan1.setYear(data.getYear());
                        userBackcashPlan1.setMonth(data.getMonth());
                        userBackcashPlan1.setBackCash(negativeCheck(data.getBackCash()) ? new BigDecimal(0) : data.getBackCash());
                        userBackcashPlan1.setOperateTime(new Date());
                        userBackcashPlan1.setOperator(operator);
                        userBackcashPlan1.setCompanyCode(companyCode);
                        passPlanList.add(userBackcashPlan1);
                    }
                } else {
                    add(passList, data, operator, companyCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        // 将数据保存在数据库中
        try {
            if (!passList.isEmpty()) {
                for (UserBackcashPlan userBackcashPlan : passList) {
                    userBackcashPlanRepository.save(userBackcashPlan);
                }
            }
            if (!passPlanList.isEmpty()) {
                for (UserBackcashPlan userBackcashPlan : passPlanList) {
                    userBackcashPlanRepository.save(userBackcashPlan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 验证年份、月份、金额都是数字
     */
    private Boolean validityDataInfoExcel(List<CellError> cellErrorList, UploadUserBackcashPlanExcelModel data, List<String> usernameList) {
        // 用户名不能为空
        String username = data.getUserName();
        if (StringUtils.isBlank(username)) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("导入的用户名不能为空！");
            cellErrorList.add(cellError);
            return false;
        } else {
            // 导入的用户都应该是存在该系统中的
            if (!usernameList.contains(username)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 不存在！");
                cellErrorList.add(cellError);
                return false;
            }
            // 姓名也不能为空
            if (StringUtils.isBlank(username)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 的姓名为空！");
                cellErrorList.add(cellError);
                return false;
            }
            // 年份不能为空
            Integer goalYear = data.getYear();
            if (Objects.isNull(goalYear)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 的年份为空！");
                cellErrorList.add(cellError);
                return false;
            } else {
                // 年份必须是4位的数字并且小于当前年份
                String yearReq = "[0-9]{4}";
                if (!(String.valueOf(goalYear)).matches(yearReq)) {
                    CellError cellError = new CellError();
                    cellError.setErrorMsg("导入的用户 [" + username + "] 的年份 [" + goalYear + "] 不符合规则！");
                    cellErrorList.add(cellError);
                    return false;
                }else {
                    String year=ZWDateUtil.getDate().substring(0,4);
                    int com=String.valueOf(goalYear).compareTo(year);
                    if (com>0) {
                        CellError cellError = new CellError();
                        cellError.setErrorMsg("导入的用户 [" + username + "] 的年份 [" + goalYear + "] 超出当前年份！");
                        cellErrorList.add(cellError);
                        return false;
                    }
                }
            }
            // 月份不能为空
            Integer goalMonth = data.getMonth();
            if (Objects.isNull(goalMonth)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 的月份为空！");
                cellErrorList.add(cellError);
                return false;
            } else {
                // 月份必须是1-12的数字
                if (!(goalMonth instanceof Number) || goalMonth < 1 || goalMonth > 12) {
                    CellError cellError = new CellError();
                    cellError.setErrorMsg("导入的用户 [" + username + "] 的月份 [" + goalMonth + "] 不符合规则！");
                    cellErrorList.add(cellError);
                    return false;
                }
            }
            if (!(data.getBackCash() instanceof Number)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 的目标金额不是数字！");
                cellErrorList.add(cellError);
                return false;
            }
            // 判断用户名和姓名是否对应正确
            String realName = data.getRealName();
            User user = userRepository.findOne(QUser.user.userName.eq(username));
            if (!Objects.equals(user.getRealName(), realName)) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户名 [" + username + "] 和姓名 [" + realName + "] 不对应");
                cellErrorList.add(cellError);
                return false;
            }
            //判断金额是否为负数
            if (negativeCheck(data.getBackCash())) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("导入的用户 [" + username + "] 的目标金额不能为负数！");
                cellErrorList.add(cellError);
                return false;
            }
        }
        return true;
    }

    /**
     * 向List中添加数据
     */
    private void add(List passList, UploadUserBackcashPlanExcelModel data, String operator, String companyCode) {
        UserBackcashPlan userBackcashPlan = new UserBackcashPlan();
        userBackcashPlan.setUserName(data.getUserName());
        userBackcashPlan.setRealName(data.getRealName());
        userBackcashPlan.setYear(data.getYear());
        userBackcashPlan.setMonth(data.getMonth());
        userBackcashPlan.setBackCash(data.getBackCash());
        userBackcashPlan.setOperateTime(new Date());
        userBackcashPlan.setOperator(operator);
        userBackcashPlan.setCompanyCode(companyCode);
        passList.add(userBackcashPlan);
    }

    /**
     * @Description 判断负值
     */
    private Boolean negativeCheck(BigDecimal var) {
        int flag = var.compareTo(new BigDecimal(0));
        if (Objects.equals(flag, -1)) {
            return true;
        } else {
            return false;
        }
    }
}
