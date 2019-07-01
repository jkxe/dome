package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class AttachmentInfo {

    private String docType;//证件材料类型
    private String docCd;//证件材料代码
    private String mtnDate;//上传时间
    private String oprId;//上传操作员
    private String fileName;//存储文件名
}
