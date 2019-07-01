package cn.fintecher.pangolin.entity;

/**
 * Created by ChenChang on 2017/4/6.
 */
public enum ReminderType {

    REPAYMENT("还款提醒"), FLLOWUP("跟进提醒"), REPAIRED("修复提醒"),
    DERATE("减免审批提醒"), APPLY("还款审核提醒"),CASE_IMPORT("案件导入提醒"),
    ASSIST_APPROVE("协催审批提醒"),LEAVE_CASE("留案案件提醒"),
    CIRCULATION("提前流转审批提醒"),FORCE_TURN("强制流转案件提醒"),
    MEMO_MODIFY("修改备注提醒"),APPROVAL_MESSAGE("审批提醒"),
    FOLLOWUP_EXPORT("跟进记录导出提醒"), VERIFICATION("核销审批提醒"),
    JUDICIAL("司法审批提醒"),COMMON_MESSAGE("消息通知"),
    CASE_EXPIRE("案件到期提醒"), STRATEGY("策略分配提醒"),
    COMMITTED_REPAYMENT("承诺还款提醒"),BATCH_TASK_WARNING("跑批任务提醒");

    private String cName;

    ReminderType(String cName) {
        this.cName = cName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
