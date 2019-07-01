package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("PersonalInformationService")
public class PersonalInformationService {

    private final Logger log = LoggerFactory.getLogger(PersonalInformationService.class);

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private PayPlanRepository payPlanRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderRepaymentPlanRepository orderRepaymentPlanRepository;

    @Autowired
    private CaseFileRepository caseFileRepository;

    @Autowired
    private WriteOffDetailsRepository writeOffDetailsRepository;

    @Autowired
    private PersonalSocialPlatRepository personalSocialPlatRepository;

    @Autowired
    private PersonalImgFileRepository personalImgFileRepository;

    @Autowired
    private PersonalAstOperCrdtRepository personalAstOperCrdtRepository;

    @Autowired
    private CaseInfoDistributedRepository caseInfoDistributedRepository;

    /**
     * 根据客户id获取客户基本信息
     * @param caseId
     * @return
     */
    public PersonalModel getPersonalByCaseId(String caseId){
        CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
        if(Objects.isNull(caseInfo)){
            throw new RuntimeException("该案件不存在");
        }
        String personalId = caseInfo.getPersonalInfo().getId();
        PersonalModel personalModel = new PersonalModel();
        personalModel.setCaseNumber(caseInfo.getCaseNumber());
        Personal personal = personalRepository.findOne(personalId);
        if(Objects.nonNull(personal)) {
            personalModel.setPersonalSource(personal.getPersonalSource());
            personalModel.setCustomerId(caseInfo.getCustomerId());
            personalModel.setOtherSource(personal.getOtherSource());
            personalModel.setCreditLevel(personal.getCreditLevel());
            personalModel.setReferrer(personal.getReferrer());
            personalModel.setIntroductionBank(personal.getIntroductionBank());
            personalModel.setName(personal.getName());
            personalModel.setSex(personal.getSex());
            personalModel.setAge(personal.getAge());
            personalModel.setMobileNo(personal.getMobileNo());
            personalModel.setEmail(personal.getEmail());
            personalModel.setEducation(personal.getEducation());
            personalModel.setMarital(personal.getMarital());
            personalModel.setChildrenNumber(personal.getChildrenNumber());
            personalModel.setNationality(personal.getNationality());
            personalModel.setCertificatesType(personal.getCertificatesType());
            personalModel.setCertificatesNumber(personal.getCertificatesNumber());
            personalModel.setIdCardIssuingAuthority(personal.getIdCardIssuingAuthority());
            personalModel.setIdCardExpirydate(personal.getIdCardExpirydate());
            personalModel.setIdCardAddress(personal.getIdCardAddress());
            personalModel.setLocalHomeAddress(personal.getLocalHomeAddress());
            personalModel.setLocalPhoneNo(personal.getLocalPhoneNo());
            personalModel.setPermanentAddress(personal.getPermanentAddress());
            personalModel.setLiveMoveTime(personal.getLiveMoveTime());
            personalModel.setHomeOwnership(personal.getHomeOwnership());
            List<PersonalBank> personalBankList = new ArrayList<>(personal.getPersonalBankInfos());
            if(personalBankList != null && personalBankList.size()> 0){
                personalModel.setPersonalBank(personalBankList);
            }
            List<PersonalCar> personalCarList = new ArrayList<>(personal.getPersonalCars());
            if(personalCarList != null && personalCarList.size()> 0){
                personalModel.setPersonalCar(personalCarList.get(0));
            }
            List<PersonalJob> personalJobs = new ArrayList<>(personal.getPersonalJobs());
            if(personalJobs != null && personalJobs.size()> 0){
                personalModel.setPersonalJob(personalJobs.get(0));
            }
            List<PersonalIncomeExp> personalIncomeExps = new ArrayList<>(personal.getPersonalIncomeExps());
            if(personalIncomeExps != null && personalIncomeExps.size()> 0){
                personalModel.setPersonalIncomeExp(personalIncomeExps.get(0));
            }
            List<PersonalSocialPlat> list = personalSocialPlatRepository.findByCustomerId(caseInfo.getCustomerId());
            if(list != null && list.size()> 0){
                personalModel.setPersonalSocialPlats(list);
            }
            List<PersonalContact> personalContactList = new ArrayList<>(personal.getPersonalContacts());
            personalModel.setPersonalContactList(personalContactList);
            List<PersonalProperty> personalPropertyList = new ArrayList<>(personal.getPersonalProperties());
            PersonalPropertyModel personalPropertyModel = new PersonalPropertyModel();
            List<String> addressList = new ArrayList<>();
            if(personalPropertyList != null && personalPropertyList.size() > 0){
                for(PersonalProperty personalProperty : personalPropertyList){
                    if(strNotNull(personalProperty)){
                        personalPropertyModel.setHousePurchasePrice(personalProperty.getHousePurchasePrice());
                        personalPropertyModel.setHouseAssAmt(personalProperty.getHouseAssAmt());
                        personalPropertyModel.setFirstPayment(personalProperty.getFirstPayment());
                        personalPropertyModel.setTotalPeriods(personalProperty.getTotalPeriods());
                        personalPropertyModel.setMonthPaymentAmount(personalProperty.getMonthPaymentAmount());
                        personalPropertyModel.setRepaymentPeriods(personalProperty.getRepaymentPeriods());
                        personalPropertyModel.setOver(personalProperty.getOver());
                        personalPropertyModel.setAddress(personalProperty.getAddress());
                    }else{
                        addressList.add(personalProperty.getAddress());
                    }
                }
            }
            personalPropertyModel.setAddressList(addressList);
            personalModel.setPersonalPropertyModel(personalPropertyModel);
        }
        return personalModel;
    }

    /**
     * 判断房产信息那个为主体
     * @param personalProperty
     * @return
     */
    public boolean strNotNull(PersonalProperty personalProperty){
        if(personalProperty.getHousePurchasePrice() == null
                && personalProperty.getHouseAssAmt() == null
                && personalProperty.getFirstPayment() == null
                && personalProperty.getTotalPeriods() == null
                && personalProperty.getMonthPaymentAmount() == null
                && personalProperty.getRepaymentPeriods() == null
                && personalProperty.getOver() == null){
            return false;
        }else{
            return true;
        }
    }


    /**
     * 获取还款信息
     * @param caseId
     * @return
     */
    public CaseInformationModel getCaseInformation(String caseId){
        CaseInformationModel caseInformationModel = new CaseInformationModel();
        CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
        if(Objects.isNull(caseInfo)){
            throw new RuntimeException("该案件不存在");
        }
        caseInformationModel.setOverdueCapital(caseInfo.getOverdueCapital());
        caseInformationModel.setOverduePeriods(caseInfo.getOverduePeriods());
        caseInformationModel.setOverdueDays(caseInfo.getOverdueDays());
        caseInformationModel.setMaxOverdueDays(caseInfo.getMaxOverdueDays());
        caseInformationModel.setLatesDateReturn(caseInfo.getLatesDateReturn());
        caseInformationModel.setOverdueAmount(caseInfo.getOverdueAmount());
        caseInformationModel.setHasPayPeriods(caseInfo.getHasPayPeriods());
        caseInformationModel.setLeftPeriods(caseInfo.getLeftPeriods());
        caseInformationModel.setUnpaidPrincipal(caseInfo.getUnpaidPrincipal());
        caseInformationModel.setUnpaidInterest(caseInfo.getUnpaidInterest());
        caseInformationModel.setUnpaidFine(caseInfo.getUnpaidFine());
        caseInformationModel.setUnpaidOtherInterest(caseInfo.getUnpaidOtherInterest());
        caseInformationModel.setUnpaidMthFee(caseInfo.getUnpaidMthFee());
        caseInformationModel.setUnpaidOtherFee(caseInfo.getUnpaidOtherFee());
        caseInformationModel.setUnpaidLpc(caseInfo.getUnpaidLpc());
        caseInformationModel.setCurrPnltInterest(caseInfo.getCurrPnltInterest());
        caseInformationModel.setLatelyPayDate(caseInfo.getLatelyPayDate());
        caseInformationModel.setLatelyPayAmount(caseInfo.getLatelyPayAmount());
        caseInformationModel.setLeftCapital(caseInfo.getLeftCapital());
        caseInformationModel.setLeftInterest(caseInfo.getLeftInterest());
        caseInformationModel.setRemainFee(caseInfo.getRemainFee());
        caseInformationModel.setOverdueAccountNumber(caseInfo.getOverdueAccountNumber());
        caseInformationModel.setInColcnt(caseInfo.getInColcnt());
        caseInformationModel.setOutColcnt(caseInfo.getOutColcnt());
        caseInformationModel.setExecutedPeriods(caseInfo.getExecutedPeriods());
        caseInformationModel.setSeriesName(caseInfo.getProduct().getProductSeries().getSeriesName());
        caseInformationModel.setProductName(caseInfo.getProduct().getProductName());
        caseInformationModel.setLoanInvoiceNumber(caseInfo.getLoanInvoiceNumber());
        caseInformationModel.setLoanPayTime(caseInfo.getLoanPayTime());
        caseInformationModel.setApplyPeriod(caseInfo.getApplyPeriod());
        caseInformationModel.setApplyAmount(caseInfo.getApplyAmount());
        caseInformationModel.setCreditPeriod(caseInfo.getCreditPeriod());
        caseInformationModel.setCreditAmount(caseInfo.getCreditAmount());
        caseInformationModel.setLoanPeriod(caseInfo.getLoanPeriod());
        caseInformationModel.setLoanAmount(caseInfo.getLoanAmount());
        caseInformationModel.setOverdueCapitalInterest(caseInfo.getOverdueCapitalInterest());
        caseInformationModel.setOverdueInterest(caseInfo.getOverdueInterest());
        caseInformationModel.setOverdueDelayFine(caseInfo.getOverdueDelayFine());
        caseInformationModel.setOverdueManageFee(caseInfo.getOverdueManageFee());
        caseInformationModel.setOverdueFine(caseInfo.getOverdueFine() );
        caseInformationModel.setContractNumber(caseInfo.getContractNumber());
        caseInformationModel.setPreRepayPrincipal(caseInfo.getPreRepayPrincipal());
        caseInformationModel.setAdvancesFlag(caseInfo.getAdvancesFlag());
        caseInformationModel.setRepayDate(caseInfo.getRepayDate());
        caseInformationModel.setMovingBackFlag(caseInfo.getMovingBackFlag());
        caseInformationModel.setVerficationStatus(caseInfo.getVerficationStatus());
        caseInformationModel.setIntoTime(caseInfo.getIntoTime());
        caseInformationModel.setCurrentDebtAmount(caseInfo.getCurrentDebtAmount());
        caseInformationModel.setAccountBalance(caseInfo.getAccountBalance());
        caseInformationModel.setUpdateTime(caseInfo.getUpdateTime());
        caseInformationModel.setCreateTime(caseInfo.getCreateTime());
        caseInformationModel.setSeriesName(caseInfo.getProductType());
        caseInformationModel.setProductName(caseInfo.getProductName());
        caseInformationModel.setRepayAccountNo(caseInfo.getRepayAccountNo());
        caseInformationModel.setRepayBank(caseInfo.getRepayBank());
        caseInformationModel.setLoanPurpose(caseInfo.getLoanPurpose());
        if(Objects.nonNull(caseInfo.getArea())) {
            caseInformationModel.setAreaName(caseInfo.getArea().getAreaName());
        }

        String caseNumber = caseInfo.getCaseNumber();
        List<PayPlan> list = new ArrayList<>();
        Iterator<PayPlan> payPlanIterator = payPlanRepository.findAll(QPayPlan.payPlan.caseNumber.eq(caseNumber)).iterator();
        while (payPlanIterator.hasNext()){
            PayPlan payPlan = payPlanIterator.next();
            list.add(payPlan);
        }
        list.sort(Comparator.comparingInt(PayPlan :: getPayPeriod));
        caseInformationModel.setPayPlans(list);

        OrderInfo orderInfo = orderInfoRepository.findOne(QOrderInfo.orderInfo.caseNumber.eq(caseNumber));
        caseInformationModel.setOrderInfo(orderInfo);
        if(Objects.nonNull(orderInfo)){
            OrderRepaymentPlan orderRepaymentPlan = orderRepaymentPlanRepository.findOne(QOrderRepaymentPlan
                    .orderRepaymentPlan.orderId.eq(orderInfo.getId()));
            caseInformationModel.setOrderRepaymentPlan(orderRepaymentPlan);
        }
        String personalId = caseInfo.getPersonalInfo().getId();
        Material material = materialRepository.findOne(QMaterial.material.personalId.eq(personalId));
        caseInformationModel.setMaterial(material);

        List<WriteOffDetails> writeOffDetailsList = new ArrayList<>();
        Iterator<WriteOffDetails> writeOffDetailsIterator = writeOffDetailsRepository.findAll(QWriteOffDetails
                .writeOffDetails.caseNumber.eq(caseNumber)).iterator();
        while (writeOffDetailsIterator.hasNext()){
            WriteOffDetails writeOffDetails = writeOffDetailsIterator.next();
            writeOffDetailsList.add(writeOffDetails);
        }
        caseInformationModel.setWriteOffDetailsList(writeOffDetailsList);
        return caseInformationModel;
    }

    /**
     * 获取该案件对应的资料信息
     * @param caseId
     * @return
     */
    public CaseFIleModel getCaseFileByCaseId(String caseId){
        CaseFIleModel caseFIleModel = new CaseFIleModel();
        CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
        if(Objects.isNull(caseInfo)){
            throw new RuntimeException("该案件不存在");
        }
        List<CaseFile> inputDataList = new ArrayList<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCaseFile qCaseFile = QCaseFile.caseFile;
        booleanBuilder.and(qCaseFile.id.eq(caseInfo.getContractNumber()));
        Iterator<CaseFile> iterator = caseFileRepository.findAll(booleanBuilder).iterator();
        while (iterator.hasNext()){
            CaseFile caseFile = iterator.next();
            inputDataList.add(caseFile);
//            if(caseFile.getFileSource().equals(CaseFile.FileSource.Input_data.getValues())){
//                inputDataList.add(caseFile);
//            }else if(caseFile.getFileSource().equals(CaseFile.FileSource.Supplementary.getValues())){
//                inputDataList.add(caseFile);
//            }else if(caseFile.getFileSource().equals(CaseFile.FileSource.Online_Contract.getValues())){
//                inputDataList.add(caseFile);
//            }else if(caseFile.getFileSource().equals(CaseFile.FileSource.Under_Line_Contract.getValues())){
//                inputDataList.add(caseFile);
//            }else if(caseFile.getFileSource().equals(CaseFile.FileSource.Other_Information.getValues())){
//                inputDataList.add(caseFile);
//            }
        }
        List<PersonalImgFile> list = personalImgFileRepository.findByCustomerId(caseInfo.getCustomerId());
        if(list != null && list.size()> 0){
            caseFIleModel.setPersonalImgFiles(list);
        }
        caseFIleModel.setInputDataList(inputDataList);
        return caseFIleModel;
    }

    public List<PersonalAstOperCrdtModel> getPersonalAstOperCrdtModelByCaseId(String caseId) {
        CaseInfo one = caseInfoRepository.findOne(caseId);
        if(Objects.isNull(one)){
            throw new RuntimeException("该案件不存在");
        }
        if (Strings.isNullOrEmpty(one.getCustomerId())){
            throw new RuntimeException("该案件关联客户不存在");
        }
        List<PersonalAstOperCrdt> list = personalAstOperCrdtRepository.findListByCustomerId(one.getCustomerId());
        List<PersonalAstOperCrdtModel> list1 = new ArrayList<>();
        for (PersonalAstOperCrdt personalAstOperCrdt : list) {
            PersonalAstOperCrdtModel personalAstOperCrdtModel = new PersonalAstOperCrdtModel();
            personalAstOperCrdtModel.setCreateTime(personalAstOperCrdt.getCreateTime());
            personalAstOperCrdtModel.setCustomerId(personalAstOperCrdt.getCustomerId());
            personalAstOperCrdtModel.setOriginalData(personalAstOperCrdt.getOriginalData());
            personalAstOperCrdtModel.setResourceType(personalAstOperCrdt.getResourceType());
            if (personalAstOperCrdt.getUpdateTime() != null){
                personalAstOperCrdtModel.setUpdateTime(personalAstOperCrdt.getUpdateTime());
            }
            personalAstOperCrdtModel.setResourceId(personalAstOperCrdt.getResourceId());
            list1.add(personalAstOperCrdtModel);
        }
        return list1;
    }

    public PersonalModel getPersonalByCaseIdDistribute(String loanInvoiceNumber) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfoDistributed.caseInfoDistributed.loanInvoiceNumber.eq(loanInvoiceNumber));
        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(builder);
        if(Objects.isNull(caseInfoDistributed)){
            throw new RuntimeException("该案件不存在");
        }
        String personalId = caseInfoDistributed.getPersonalInfo().getId();
        PersonalModel personalModel = new PersonalModel();
        personalModel.setCaseNumber(caseInfoDistributed.getCaseNumber());
        Personal personal = personalRepository.findOne(personalId);
        if(Objects.nonNull(personal)) {
            personalModel.setPersonalSource(personal.getPersonalSource());
            personalModel.setCustomerId(caseInfoDistributed.getCustomerId());
            personalModel.setOtherSource(personal.getOtherSource());
            personalModel.setCreditLevel(personal.getCreditLevel());
            personalModel.setReferrer(personal.getReferrer());
            personalModel.setIntroductionBank(personal.getIntroductionBank());
            personalModel.setName(personal.getName());
            personalModel.setSex(personal.getSex());
            personalModel.setAge(personal.getAge());
            personalModel.setMobileNo(personal.getMobileNo());
            personalModel.setEmail(personal.getEmail());
            personalModel.setEducation(personal.getEducation());
            personalModel.setMarital(personal.getMarital());
            personalModel.setChildrenNumber(personal.getChildrenNumber());
            personalModel.setNationality(personal.getNationality());
            personalModel.setCertificatesType(personal.getCertificatesType());
            personalModel.setCertificatesNumber(personal.getCertificatesNumber());
            personalModel.setIdCardIssuingAuthority(personal.getIdCardIssuingAuthority());
            personalModel.setIdCardExpirydate(personal.getIdCardExpirydate());
            personalModel.setIdCardAddress(personal.getIdCardAddress());
            personalModel.setLocalHomeAddress(personal.getLocalHomeAddress());
            personalModel.setLocalPhoneNo(personal.getLocalPhoneNo());
            personalModel.setPermanentAddress(personal.getPermanentAddress());
            personalModel.setLiveMoveTime(personal.getLiveMoveTime());
            personalModel.setHomeOwnership(personal.getHomeOwnership());
            List<PersonalBank> personalBankList = new ArrayList<>(personal.getPersonalBankInfos());
            if(personalBankList != null && personalBankList.size()> 0){
                personalModel.setPersonalBank(personalBankList);
            }
            List<PersonalCar> personalCarList = new ArrayList<>(personal.getPersonalCars());
            if(personalCarList != null && personalCarList.size()> 0){
                personalModel.setPersonalCar(personalCarList.get(0));
            }
            List<PersonalJob> personalJobs = new ArrayList<>(personal.getPersonalJobs());
            if(personalJobs != null && personalJobs.size()> 0){
                personalModel.setPersonalJob(personalJobs.get(0));
            }
            List<PersonalIncomeExp> personalIncomeExps = new ArrayList<>(personal.getPersonalIncomeExps());
            if(personalIncomeExps != null && personalIncomeExps.size()> 0){
                personalModel.setPersonalIncomeExp(personalIncomeExps.get(0));
            }
            List<PersonalSocialPlat> list = personalSocialPlatRepository.findByCustomerId(caseInfoDistributed.getCustomerId());
            if(list != null && list.size()> 0){
                personalModel.setPersonalSocialPlats(list);
            }
            List<PersonalContact> personalContactList = new ArrayList<>(personal.getPersonalContacts());
            personalModel.setPersonalContactList(personalContactList);
            List<PersonalProperty> personalPropertyList = new ArrayList<>(personal.getPersonalProperties());
            PersonalPropertyModel personalPropertyModel = new PersonalPropertyModel();
            List<String> addressList = new ArrayList<>();
            if(personalPropertyList != null && personalPropertyList.size() > 0){
                for(PersonalProperty personalProperty : personalPropertyList){
                    if(strNotNull(personalProperty)){
                        personalPropertyModel.setHousePurchasePrice(personalProperty.getHousePurchasePrice());
                        personalPropertyModel.setHouseAssAmt(personalProperty.getHouseAssAmt());
                        personalPropertyModel.setFirstPayment(personalProperty.getFirstPayment());
                        personalPropertyModel.setTotalPeriods(personalProperty.getTotalPeriods());
                        personalPropertyModel.setMonthPaymentAmount(personalProperty.getMonthPaymentAmount());
                        personalPropertyModel.setRepaymentPeriods(personalProperty.getRepaymentPeriods());
                        personalPropertyModel.setOver(personalProperty.getOver());
                        personalPropertyModel.setAddress(personalProperty.getAddress());
                    }else{
                        addressList.add(personalProperty.getAddress());
                    }
                }
            }
            personalPropertyModel.setAddressList(addressList);
            personalModel.setPersonalPropertyModel(personalPropertyModel);
        }
        return personalModel;
    }

    public CaseInformationModel getCaseInformationDistribute(String loanInvoiceNumber) {
        CaseInformationModel caseInformationModel = new CaseInformationModel();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfoDistributed.caseInfoDistributed.loanInvoiceNumber.eq(loanInvoiceNumber));
        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(builder);
        if(Objects.isNull(caseInfoDistributed)){
            throw new RuntimeException("该案件不存在");
        }
        caseInformationModel.setOverdueCapital(caseInfoDistributed.getOverdueCapital());
        caseInformationModel.setOverduePeriods(caseInfoDistributed.getOverduePeriods());
        caseInformationModel.setOverdueDays(caseInfoDistributed.getOverdueDays());
        caseInformationModel.setMaxOverdueDays(caseInfoDistributed.getMaxOverdueDays());
        caseInformationModel.setLatesDateReturn(caseInfoDistributed.getLatesDateReturn());
        caseInformationModel.setOverdueAmount(caseInfoDistributed.getOverdueAmount());
        caseInformationModel.setHasPayPeriods(caseInfoDistributed.getHasPayPeriods());
        caseInformationModel.setLeftPeriods(caseInfoDistributed.getLeftPeriods());
        caseInformationModel.setUnpaidPrincipal(caseInfoDistributed.getUnpaidPrincipal());
        caseInformationModel.setUnpaidInterest(caseInfoDistributed.getUnpaidInterest());
        caseInformationModel.setUnpaidFine(caseInfoDistributed.getUnpaidFine());
        caseInformationModel.setUnpaidOtherInterest(caseInfoDistributed.getUnpaidOtherInterest());
        caseInformationModel.setUnpaidMthFee(caseInfoDistributed.getUnpaidMthFee());
        caseInformationModel.setUnpaidOtherFee(caseInfoDistributed.getUnpaidOtherFee());
        caseInformationModel.setUnpaidLpc(caseInfoDistributed.getUnpaidLpc());
        caseInformationModel.setCurrPnltInterest(caseInfoDistributed.getCurrPnltInterest());
        caseInformationModel.setLatelyPayDate(caseInfoDistributed.getLatelyPayDate());
        caseInformationModel.setLatelyPayAmount(caseInfoDistributed.getLatelyPayAmount());
        caseInformationModel.setLeftCapital(caseInfoDistributed.getLeftCapital());
        caseInformationModel.setLeftInterest(caseInfoDistributed.getLeftInterest());
        caseInformationModel.setRemainFee(caseInfoDistributed.getRemainFee());
        caseInformationModel.setOverdueAccountNumber(caseInfoDistributed.getOverdueAccountNumber());
        caseInformationModel.setInColcnt(caseInfoDistributed.getInColcnt());
        caseInformationModel.setOutColcnt(caseInfoDistributed.getOutColcnt());
        caseInformationModel.setExecutedPeriods(caseInfoDistributed.getExecutedPeriods());
        caseInformationModel.setSeriesName(caseInfoDistributed.getProduct().getProductSeries().getSeriesName());
        caseInformationModel.setProductName(caseInfoDistributed.getProduct().getProductName());
        caseInformationModel.setLoanInvoiceNumber(caseInfoDistributed.getLoanInvoiceNumber());
        caseInformationModel.setLoanPayTime(caseInfoDistributed.getLoanPayTime());
        caseInformationModel.setApplyPeriod(caseInfoDistributed.getApplyPeriod());
        caseInformationModel.setApplyAmount(caseInfoDistributed.getApplyAmount());
        caseInformationModel.setCreditPeriod(caseInfoDistributed.getCreditPeriod());
        caseInformationModel.setCreditAmount(caseInfoDistributed.getCreditAmount());
        caseInformationModel.setLoanPeriod(caseInfoDistributed.getLoanPeriod());
        caseInformationModel.setLoanAmount(caseInfoDistributed.getLoanAmount());
        caseInformationModel.setOverdueCapitalInterest(caseInfoDistributed.getOverdueCapitalInterest());
        caseInformationModel.setOverdueInterest(caseInfoDistributed.getOverdueInterest());
        caseInformationModel.setOverdueDelayFine(caseInfoDistributed.getOverdueFine());
        caseInformationModel.setOverdueManageFee(caseInfoDistributed.getOverdueManageFee());
        caseInformationModel.setOverdueFine(caseInfoDistributed.getOverdueFine() );
        caseInformationModel.setContractNumber(caseInfoDistributed.getContractNumber());
        caseInformationModel.setPreRepayPrincipal(caseInfoDistributed.getPreRepayPrincipal());
        caseInformationModel.setAdvancesFlag(caseInfoDistributed.getAdvancesFlag());
        caseInformationModel.setRepayDate(caseInfoDistributed.getRepayDate());
        caseInformationModel.setMovingBackFlag(caseInfoDistributed.getMovingBackFlag());
        caseInformationModel.setVerficationStatus(caseInfoDistributed.getVerficationStatus());
        caseInformationModel.setIntoTime(caseInfoDistributed.getIntoTime());
        caseInformationModel.setCurrentDebtAmount(caseInfoDistributed.getCurrentDebtAmount());
        caseInformationModel.setAccountBalance(caseInfoDistributed.getAccountBalance());
        caseInformationModel.setUpdateTime(caseInfoDistributed.getUpdateTime());
        caseInformationModel.setCreateTime(caseInfoDistributed.getCreateTime());
        caseInformationModel.setSeriesName(caseInfoDistributed.getProductType());
        caseInformationModel.setProductName(caseInfoDistributed.getProductName());
        caseInformationModel.setRepayAccountNo(caseInfoDistributed.getRepayAccountNo());
        caseInformationModel.setRepayBank(caseInfoDistributed.getRepayBank());
        caseInformationModel.setLoanPurpose(caseInfoDistributed.getLoanPurpose());
        if(Objects.nonNull(caseInfoDistributed.getArea())) {
            caseInformationModel.setAreaName(caseInfoDistributed.getArea().getAreaName());
        }

        String caseNumber = caseInfoDistributed.getCaseNumber();
        List<PayPlan> list = new ArrayList<>();
        Iterator<PayPlan> payPlanIterator = payPlanRepository.findAll(QPayPlan.payPlan.caseNumber.eq(caseNumber)).iterator();
        while (payPlanIterator.hasNext()){
            PayPlan payPlan = payPlanIterator.next();
            list.add(payPlan);
        }
        list.sort(Comparator.comparingInt(PayPlan :: getPayPeriod));
        caseInformationModel.setPayPlans(list);

        OrderInfo orderInfo = orderInfoRepository.findOne(QOrderInfo.orderInfo.caseNumber.eq(caseNumber));
        caseInformationModel.setOrderInfo(orderInfo);
        if(Objects.nonNull(orderInfo)){
            OrderRepaymentPlan orderRepaymentPlan = orderRepaymentPlanRepository.findOne(QOrderRepaymentPlan
                    .orderRepaymentPlan.orderId.eq(orderInfo.getId()));
            caseInformationModel.setOrderRepaymentPlan(orderRepaymentPlan);
        }
        String personalId = caseInfoDistributed.getPersonalInfo().getId();
        Material material = materialRepository.findOne(QMaterial.material.personalId.eq(personalId));
        caseInformationModel.setMaterial(material);

        List<WriteOffDetails> writeOffDetailsList = new ArrayList<>();
        Iterator<WriteOffDetails> writeOffDetailsIterator = writeOffDetailsRepository.findAll(QWriteOffDetails
                .writeOffDetails.caseNumber.eq(caseNumber)).iterator();
        while (writeOffDetailsIterator.hasNext()){
            WriteOffDetails writeOffDetails = writeOffDetailsIterator.next();
            writeOffDetailsList.add(writeOffDetails);
        }
        caseInformationModel.setWriteOffDetailsList(writeOffDetailsList);
        return caseInformationModel;
    }

    public List<PersonalAstOperCrdtModel> getPersonalAstOperCrdtModelByCaseIdDistribute(String loanInvoiceNumber) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfoDistributed.caseInfoDistributed.loanInvoiceNumber.eq(loanInvoiceNumber));
        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(builder);
        if(Objects.isNull(caseInfoDistributed)){
            throw new RuntimeException("该案件不存在");
        }
        if (Strings.isNullOrEmpty(caseInfoDistributed.getCustomerId())){
            throw new RuntimeException("该案件关联客户不存在");
        }
        List<PersonalAstOperCrdt> list = personalAstOperCrdtRepository.findListByCustomerId(caseInfoDistributed.getCustomerId());
        List<PersonalAstOperCrdtModel> list1 = new ArrayList<>();
        for (PersonalAstOperCrdt personalAstOperCrdt : list) {
            PersonalAstOperCrdtModel personalAstOperCrdtModel = new PersonalAstOperCrdtModel();
            personalAstOperCrdtModel.setCreateTime(personalAstOperCrdt.getCreateTime());
            personalAstOperCrdtModel.setCustomerId(personalAstOperCrdt.getCustomerId());
            personalAstOperCrdtModel.setOriginalData(personalAstOperCrdt.getOriginalData());
            personalAstOperCrdtModel.setResourceType(personalAstOperCrdt.getResourceType());
            if (personalAstOperCrdt.getUpdateTime() != null){
                personalAstOperCrdtModel.setUpdateTime(personalAstOperCrdt.getUpdateTime());
            }
            personalAstOperCrdtModel.setResourceId(personalAstOperCrdt.getResourceId());
            list1.add(personalAstOperCrdtModel);
        }
        return list1;
    }

    public CaseFIleModel getCaseFileByCaseIdDistribute(String loanInvoiceNumber) {
        CaseFIleModel caseFIleModel = new CaseFIleModel();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfoDistributed.caseInfoDistributed.loanInvoiceNumber.eq(loanInvoiceNumber));
        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(builder);
        if(Objects.isNull(caseInfoDistributed)){
            throw new RuntimeException("该案件不存在");
        }
        List<CaseFile> inputDataList = new ArrayList<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCaseFile qCaseFile = QCaseFile.caseFile;
        booleanBuilder.and(qCaseFile.id.eq(caseInfoDistributed.getContractNumber()));
        Iterator<CaseFile> iterator = caseFileRepository.findAll(booleanBuilder).iterator();
        while (iterator.hasNext()){
            CaseFile caseFile = iterator.next();
            inputDataList.add(caseFile);
        }
        List<PersonalImgFile> list = personalImgFileRepository.findByCustomerId(caseInfoDistributed.getCustomerId());
        if(list != null && list.size()> 0){
            caseFIleModel.setPersonalImgFiles(list);
        }
        caseFIleModel.setInputDataList(inputDataList);
        return caseFIleModel;
    }
}
