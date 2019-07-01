package cn.fintecher.pangolin.business.web;


import cn.fintecher.pangolin.business.model.CaseFIleModel;
import cn.fintecher.pangolin.business.model.CaseInformationModel;
import cn.fintecher.pangolin.business.model.PersonalAstOperCrdtModel;
import cn.fintecher.pangolin.business.model.PersonalModel;
import cn.fintecher.pangolin.business.service.PersonalInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personalInformationController")
@Api(value = "PersonalInformationController", description = "详情页展示的客户详细信息")
public class PersonalInformationController {

    private final Logger log = LoggerFactory.getLogger(PersonalInformationController.class);

    @Autowired
    private PersonalInformationService personalInformationService;

    @GetMapping("/getPersonalByCaseId")
    @ApiOperation(value = "获取客户和对应银行信息",notes = "获取客户和对应银行信息")
    public ResponseEntity<PersonalModel> getPersonalByCaseId(@RequestParam("caseId") String caseId){
        PersonalModel personalModel = personalInformationService.getPersonalByCaseId(caseId);
        return ResponseEntity.ok().body(personalModel);
    }

    @GetMapping("/getPersonalByCaseIdDistribute")
    @ApiOperation(value = "获取客户和对应银行信息",notes = "获取客户和对应银行信息")
    public ResponseEntity<PersonalModel> getPersonalByCaseIdDistribute(@RequestParam("loanInvoiceNumber") String loanInvoiceNumber){
        PersonalModel personalModel = personalInformationService.getPersonalByCaseIdDistribute(loanInvoiceNumber);
        return ResponseEntity.ok().body(personalModel);
    }

    @GetMapping("/getCaseInformationModelByCaseId")
    @ApiOperation(value = "获取该案件的基础信息",notes = "获取该案件的基础信息")
    public ResponseEntity<CaseInformationModel> getCaseInformationModelByCaseId(
            @RequestParam("caseId") String caseId){
        CaseInformationModel caseInformationModel = personalInformationService.getCaseInformation(caseId);
        return ResponseEntity.ok().body(caseInformationModel);
    }

    @GetMapping("/getCaseInformationModelByCaseIdDistribute")
    @ApiOperation(value = "获取该案件的基础信息",notes = "获取该案件的基础信息")
    public ResponseEntity<CaseInformationModel> getCaseInformationModelByCaseIdDistribute(
            @RequestParam("loanInvoiceNumber") String loanInvoiceNumber){
        CaseInformationModel caseInformationModel = personalInformationService.getCaseInformationDistribute(loanInvoiceNumber);
        return ResponseEntity.ok().body(caseInformationModel);
    }

    @GetMapping("/getPersonalAstOperCrdtModelByCaseId")
    @ApiOperation(value = "获取该案件的资产信息和征信信息",notes = "获取该案件的资产信息和征信信息")
    public ResponseEntity<List<PersonalAstOperCrdtModel>> getPersonalAstOperCrdtModelByCaseId(
            @RequestParam("caseId") String caseId){
        List<PersonalAstOperCrdtModel> personalAstOperCrdtModel = personalInformationService.getPersonalAstOperCrdtModelByCaseId(caseId);
        return ResponseEntity.ok().body(personalAstOperCrdtModel);
    }

    @GetMapping("/getPersonalAstOperCrdtModelByCaseIdDistribute")
    @ApiOperation(value = "获取该案件的资产信息和征信信息",notes = "获取该案件的资产信息和征信信息")
    public ResponseEntity<List<PersonalAstOperCrdtModel>> getPersonalAstOperCrdtModelByCaseIdDistribute(
            @RequestParam("loanInvoiceNumber") String loanInvoiceNumber){
        List<PersonalAstOperCrdtModel> personalAstOperCrdtModel = personalInformationService.getPersonalAstOperCrdtModelByCaseIdDistribute(loanInvoiceNumber);
        return ResponseEntity.ok().body(personalAstOperCrdtModel);
    }

    @GetMapping("/getCaseFIleModelByCaseId")
    @ApiOperation(value = "获取该案件的文件信息",notes = "获取该案件的文件信息")
    public ResponseEntity<CaseFIleModel> getCaseFIleModelByCaseId(
            @RequestParam("caseId") String caseId){
        CaseFIleModel caseFIleModel = personalInformationService.getCaseFileByCaseId(caseId);
        return ResponseEntity.ok().body(caseFIleModel);
    }

    @GetMapping("/getCaseFIleModelByCaseIdDistribute")
    @ApiOperation(value = "获取该案件的文件信息",notes = "获取该案件的文件信息")
    public ResponseEntity<CaseFIleModel> getCaseFIleModelByCaseIdDistribute(
            @RequestParam("loanInvoiceNumber") String loanInvoiceNumber){
        CaseFIleModel caseFIleModel = personalInformationService.getCaseFileByCaseIdDistribute(loanInvoiceNumber);
        return ResponseEntity.ok().body(caseFIleModel);
    }
}
