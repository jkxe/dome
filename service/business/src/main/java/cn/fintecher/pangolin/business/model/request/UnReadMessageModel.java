package cn.fintecher.pangolin.business.model.request;

import java.util.List;

public class UnReadMessageModel {

    //借据号(查询未读消息时使用)
    private List<String> loanInvoiceNumber;

    public void setLoanInvoiceNumber(List<String> loanInvoiceNumber) {
        this.loanInvoiceNumber = loanInvoiceNumber;
    }
    public List<String> getLoanInvoiceNumber() {
        return loanInvoiceNumber;
    }
}
