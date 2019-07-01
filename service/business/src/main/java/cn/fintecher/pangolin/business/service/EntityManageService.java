package cn.fintecher.pangolin.business.service;


import cn.fintecher.pangolin.business.model.CaseInfoDistributeParams;
import cn.fintecher.pangolin.util.ZWStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;


/**
 * Created by qijigui on 2018-01-05.
 */

@Service("entityManageService")
public class EntityManageService {

    private final Logger logger = LoggerFactory.getLogger(EntityManageService.class);

    @Inject
    EntityManager entityManager;

    public List<Object[]> getCaseInfoDistribute(CaseInfoDistributeParams CaseInfoDistributeParams, String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT\n" +
                "\tcid.id AS caseId,\n" +
                "\tcid.area_id AS city,\n" +
                "\tcid.overdue_amount AS overdueAmount,\n" +
                "\tcid.overdue_days AS overdueDays,\n" +
                "\tprin.`name` AS principalName,\n" +
                "\tcid.batch_number AS batchNumber,\n" +
                "\tprodser.series_name AS seriesName,\n" +
                "\tarea.parent_id AS province,\n" +
                "\tcid.overdue_periods AS overduePeriods,\n" +
                "\tcid.case_number AS caseNumber,\n" +
                "\tper.id_card AS idCard,\n" +
                "\tcid.pay_status AS payStatus,\n" +
                "\tcid.score AS score,\n" +
                "\tcid.delegation_date AS delegationDate,\n" +
                "\tcid.close_date AS close_date,\n" +
                "\tper.mobile_no,\n" +
                "\tper.`name`,\n" +
                "\tcid.commission_rate,\n" +
                "\tprod.product_name,\n" +
                "\tcid.contract_amount\n" +
                "FROM\n" +
                "\t" + tableName + " cid\n" +
                "LEFT JOIN personal per ON cid.personal_id = per.id\n" +
                "LEFT JOIN area_code area ON cid.area_id = area.id\n" +
                "LEFT JOIN principal prin ON cid.principal_id = prin.id\n" +
                "LEFT JOIN product prod ON cid.product_id = prod.id\n" +
                "LEFT JOIN product_series prodser ON prodser.id = prod.series_id\n" +
                "WHERE\n" +
                "\tcid.recover_remark = 0\n" +
                "AND cid.company_code ='" + CaseInfoDistributeParams.getCompanyCode() + "'");
        appendParamSql(CaseInfoDistributeParams, stringBuilder);
        if (Objects.equals(tableName, "case_info")) {
            stringBuilder.append(" and cid.case_pool_type=225 and cid.collection_status=25");
        }
        logger.info("策略查询语句：{}", stringBuilder.toString());
        return entityManager.createNativeQuery(stringBuilder.toString()).getResultList();
    }

    public List<Object[]> getOutSourcePool(CaseInfoDistributeParams CaseInfoDistributeParams) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT\n" +
                "\tcid.id AS caseId,\n" +
                "\tcid.area_id AS city,\n" +
                "\tcid.overdue_amount AS overdueAmount,\n" +
                "\tcid.overdue_days AS overdueDays,\n" +
                "\tprin.`name` AS principalName,\n" +
                "\tcid.batch_number AS batchNumber,\n" +
                "\tprodser.series_name AS seriesName,\n" +
                "\tarea.parent_id AS province,\n" +
                "\tcid.overdue_periods AS overduePeriods,\n" +
                "\tcid.case_number AS caseNumber,\n" +
                "\tper.id_card AS idCard,\n" +
                "\tcid.pay_status AS payStatus,\n" +
                "\tcid.score AS score,\n" +
                "\tcid.delegation_date AS delegationDate,\n" +
                "\tcid.close_date AS close_date,\n" +
                "\tper.mobile_no,\n" +
                "\tper.`name`,\n" +
                "\tcid.commission_rate,\n" +
                "\tprod.product_name,\n" +
                "\tcid.contract_amount\n" +
                "FROM\n" +
                "\toutsource_pool ots\n" +
                "LEFT JOIN `case_info` cid ON ots.case_id = cid.id\n" +
                "LEFT JOIN personal per ON cid.personal_id = per.id\n" +
                "LEFT JOIN area_code area ON cid.area_id = area.id\n" +
                "LEFT JOIN principal prin ON cid.principal_id = prin.id\n" +
                "LEFT JOIN product prod ON cid.product_id = prod.id\n" +
                "LEFT JOIN product_series prodser ON prodser.id = prod.series_id\n" +
                "WHERE\n" +
                "\tcid.recover_remark = 0\n" +
                "AND cid.company_code ='" + CaseInfoDistributeParams.getCompanyCode() + "'");
        appendParamSql(CaseInfoDistributeParams, stringBuilder);
        stringBuilder.append(" and ots.out_status=167 ");
        logger.info("策略查询委外语句：{}", stringBuilder.toString());
        return entityManager.createNativeQuery(stringBuilder.toString()).getResultList();
    }

    private void appendParamSql(CaseInfoDistributeParams caseInfoDistributeParams, StringBuilder stringBuilder) {
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getPersonalName())) {
            stringBuilder.append(" and per.name like '%").append(caseInfoDistributeParams.getPersonalName().trim()).append("%'");
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getMobileNo())) {
            stringBuilder.append(" and per.mobile_no ='").append(caseInfoDistributeParams.getMobileNo().trim()).append("'");
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getProvinceId())) {
            stringBuilder.append(" and area.parent_id =").append(caseInfoDistributeParams.getProvinceId().trim());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getCityId())) {
            stringBuilder.append(" and area.id =").append(caseInfoDistributeParams.getCityId().trim());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getBatchNumber())) {
            stringBuilder.append(" and cid.batch_number ='").append(caseInfoDistributeParams.getBatchNumber().trim()).append("'");
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getOverdueDaysStart())) {
            stringBuilder.append(" and cid.overdue_days >=").append(caseInfoDistributeParams.getOverdueDaysStart());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getOverdueDaysEnd())) {
            stringBuilder.append(" and cid.overdue_days <=").append(caseInfoDistributeParams.getOverdueDaysEnd());
        }

        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getOverDueAmountStart())) {
            stringBuilder.append(" and cid.overdue_amount >=").append(caseInfoDistributeParams.getOverDueAmountStart());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getOverDueAmountEnd())) {
            stringBuilder.append(" and cid.overdue_amount <=").append(caseInfoDistributeParams.getOverDueAmountEnd());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getHandNumberStart())) {
            stringBuilder.append(" and cid.hand_number >=").append(caseInfoDistributeParams.getHandNumberStart());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getHandNumberEnd())) {
            stringBuilder.append(" and cid.hand_number <=").append(caseInfoDistributeParams.getHandNumberEnd());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getPrincipalId())) {
            stringBuilder.append(" and prin.id ='").append(caseInfoDistributeParams.getPrincipalId()).append("'");
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getCommissionRateStart())) {
            stringBuilder.append(" and cid.commission_rate >=").append(caseInfoDistributeParams.getCommissionRateStart());
        }
        if (ZWStringUtils.isNotEmpty(caseInfoDistributeParams.getCommissionRateEnd())) {
            stringBuilder.append(" and cid.commission_rate <=").append(caseInfoDistributeParams.getCommissionRateEnd());
        }
        if (Objects.nonNull(caseInfoDistributeParams.getCaseIds()) && !caseInfoDistributeParams.getCaseIds().isEmpty()) {
            stringBuilder.append(" and cid.id in ").append(caseInfoDistributeParams.setCaseId(caseInfoDistributeParams.getCaseIds()));
        }
    }
}
