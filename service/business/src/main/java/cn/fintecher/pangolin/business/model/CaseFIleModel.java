package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.CaseFile;
import cn.fintecher.pangolin.entity.PersonalImgFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseFIleModel {

    @ApiModelProperty(notes = "案件资料")
    private List<CaseFile> inputDataList;

    @ApiModelProperty(notes = "影像资料")
    private List<PersonalImgFile> personalImgFiles;
}
