package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.BatchSendRequest;
import cn.fintecher.pangolin.business.model.EmailBatchSendRequest;
import cn.fintecher.pangolin.business.model.EmailSendRequest;
import cn.fintecher.pangolin.business.model.MessageBatchSendRequest;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.PersonalContactRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by  yuanyanting.
 * Description:
 * Date: 2017-08-18
 */
@RestController
@RequestMapping("/api/caseIntelligentCollectionController")
@Api(value = "智能催收", description = "智能催收")
public class CaseIntelligentCollectionController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(CaseIntelligentCollectionController.class);
    private static final String ENTITY_NAME = "CaseIntelligentCollection";

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private PersonalContactRepository personalContactRepository;

    /**
     * @Description : 分页,多条件查询智能催收案件信息
     */
    @GetMapping("/queryCaseInfo")
    @ApiOperation(value = "查询智能催收案件信息", notes = "查询智能催收案件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> queryCaseInfo(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestHeader(value = "X-UserToken") String token,
                                                        @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue());
        list.add(CaseInfo.CollectionStatus.COLLECTIONING.getValue());
        list.add(CaseInfo.CollectionStatus.OVER_PAYING.getValue());
        list.add(CaseInfo.CollectionStatus.EARLY_PAYING.getValue());
        builder.and(QCaseInfo.caseInfo.collectionStatus.in(list));
        if (Objects.isNull(user.getCompanyCode())) {
            //超级管理员
            if (Objects.nonNull(companyCode)) {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            }
        } else {
            //不是超级管理员
            builder.and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode())); //限制公司code码
        }
        Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "CaseIntelligentCollectionController")).body(page);
    }

    /**
     * @Description 短信群发及语音群呼操作
     */
    @PostMapping("/handleBatchSend")
    @ApiOperation(value = "短信群发及语音群呼操作", notes = "短信群发及语音群呼操作")
    public ResponseEntity<List<MessageBatchSendRequest>> handleBatchSend(@RequestBody BatchSendRequest request) {
        try {
            List<String> cupoIdlist = request.getCupoIdList(); //获得案件ID集合
            Integer selected = request.getSelected(); //是否选择本人
            List<Integer> selRelations = request.getSelRelationsList(); //客户关系集合
            List<CaseInfo> caseInfolList = new ArrayList<>();
            for (String cupoId : cupoIdlist) {
                CaseInfo caseInfo = caseInfoRepository.findOne(cupoId);
                caseInfolList.add(caseInfo);
            }
            List<MessageBatchSendRequest> messageBatchSendRequestList = new ArrayList<>();
            for (CaseInfo caseInfo : caseInfolList) {
                MessageBatchSendRequest messageBatchSendRequest = batchSend(caseInfo, selected, selRelations);
                messageBatchSendRequest.setCustId(caseInfo.getPersonalInfo().getId()); // 客户id
                messageBatchSendRequest.setCaseNumber(caseInfo.getCaseNumber()); //案件编号
                messageBatchSendRequest.setCustName(caseInfo.getPersonalInfo().getName()); // 客户姓名
                messageBatchSendRequestList.add(messageBatchSendRequest);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "operation successfully")).body(messageBatchSendRequestList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "operation failure", "操作失败")).body(null);
        }

    }

    /**
     * @Description 电子邮件群发操作
     */
    @PostMapping("/handleEmailSend")
    @ApiOperation(value = "电子邮件群发操作", notes = "电子邮件群发操作")
    public ResponseEntity<List<EmailSendRequest>> handleEmailSend(@RequestBody EmailBatchSendRequest emailBatchSendRequest) {

        try {
            List<String> cupoIdlist = emailBatchSendRequest.getEmailBatchSendList(); //获得案件ID集合
            List<CaseInfo> caseInfos = new ArrayList<>();
            for (String cupoId : cupoIdlist) {
                CaseInfo caseInfo = caseInfoRepository.findOne(cupoId);
                caseInfos.add(caseInfo);
            }
            List<EmailSendRequest> emailSendRequests = new ArrayList<>(); //客户与邮箱集合
            for (CaseInfo caseInfo : caseInfos) {
                QPersonalContact qPersonalContact = QPersonalContact.personalContact;
                //本人的数字码是69
                Iterable<PersonalContact> personalContacts = personalContactRepository.findAll(qPersonalContact.personalId.eq(caseInfo.getPersonalInfo().getId()));
                Iterator<PersonalContact> iterator = personalContacts.iterator();
                while (iterator.hasNext()) {
                    PersonalContact next = iterator.next();
                    EmailSendRequest emailSendRequest = new EmailSendRequest();
                    //不展示没有邮箱的客户
                    if (Objects.nonNull(next.getMail()) && !("").equals(next.getMail())) {
                        emailSendRequest.setCustId(Objects.isNull(next.getPersonalId()) ? null : next.getPersonalId()); // 客户ID
                        emailSendRequest.setCustName(Objects.isNull(next.getName()) ? null : next.getName()); //客户姓名
                        emailSendRequest.setEmail(Objects.isNull(next.getMail()) ? null : next.getMail()); // 客户邮箱
                        emailSendRequest.setRelation(Objects.isNull(next.getRelation()) ? null : next.getRelation());// 案件id
                        emailSendRequest.setCupoId(caseInfo.getId());
                        emailSendRequests.add(emailSendRequest);
                    }
                }
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "operation successfully")).body(emailSendRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "operation failure", "操作失败")).body(null);
        }

    }

    /**
     * @Description 获得联系人及电话号码，只在短信群发及语音群呼中调用
     */
    private MessageBatchSendRequest batchSend(CaseInfo caseInfo, Integer selected, List<Integer> selRelations) {
        MessageBatchSendRequest messageBatchSendRequest = new MessageBatchSendRequest();
        List<Integer> relationList = new ArrayList<>(); //客户关系列表
        List<String> phoneList = new ArrayList<>(); //客户关系的电话列表
        List<Integer> statusList = new ArrayList<>(); //状态列表
        List<String> nameList = new ArrayList<>(); //关系人姓名
        List<String> concatIds = new ArrayList<>(); //客户关系人ID
        QPersonalContact qPersonalContact = QPersonalContact.personalContact;
        if (1 == selected) { //判断是否选择本人 1：是
            //本人的数字码(relation)是69
            Iterable<PersonalContact> personalContacts = personalContactRepository.findAll(qPersonalContact.personalId.eq(caseInfo.getPersonalInfo().getId()).and(qPersonalContact.relation.eq(69)));
            Iterator<PersonalContact> iterator = personalContacts.iterator();
            while (iterator.hasNext()) {
                PersonalContact next = iterator.next();
                if (Objects.nonNull(next.getPhone()) && !("").equals(next.getPhone())) {
                    relationList.add(Objects.isNull(next.getRelation()) ? null : next.getRelation());
                    phoneList.add(next.getPhone());
                    nameList.add(Objects.isNull(next.getName()) ? null : next.getName());
                    statusList.add(Objects.isNull(next.getPhoneStatus()) ? null : next.getPhoneStatus());
                    concatIds.add(next.getId());
                }
            }
        }
        List<PersonalContact> personalContactList = new ArrayList<>();
        Iterable<PersonalContact> personalContacts = personalContactRepository.findAll(qPersonalContact.personalId.eq(caseInfo.getPersonalInfo().getId()).and(qPersonalContact.relation.in(selRelations)).and(qPersonalContact.relation.ne(69)));
        Iterator<PersonalContact> iterator = personalContacts.iterator();
        while (iterator.hasNext()) {
            personalContactList.add(iterator.next());
        }
        for (PersonalContact personalContact : personalContactList) {
            if (Objects.nonNull(personalContact.getPhone())) {
                relationList.add(personalContact.getRelation());
                phoneList.add(personalContact.getPhone());
                nameList.add(personalContact.getName());
                statusList.add(personalContact.getPhoneStatus());
                concatIds.add(personalContact.getId());
            }
        }
        messageBatchSendRequest.setConcatIds(concatIds);
        messageBatchSendRequest.setRelation(relationList);
        messageBatchSendRequest.setPhone(phoneList);
        messageBatchSendRequest.setStatus(statusList);
        messageBatchSendRequest.setNameList(nameList);
        return messageBatchSendRequest;
    }
}
