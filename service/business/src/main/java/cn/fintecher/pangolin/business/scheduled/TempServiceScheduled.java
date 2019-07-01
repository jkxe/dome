package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description:
 * @Author: cowboy
 * @Date Created in 2018/8/27 17:30
 */
@Component
//@EnableScheduling
public class TempServiceScheduled {

    @Autowired
    SysParamRepository sysParamRepository;

//    @Scheduled(cron = "0 35 09 * * ?")
    public void tempService(){
        try{
            QSysParam qSysParam = QSysParam.sysParam;
            SysParam sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.TEMP).and(qSysParam.companyCode.eq("0001")));
            if(Objects.isNull(sysParam)){
                throw new Exception("临时基础数据缺失");
            }
            sysParam.setValue(String.valueOf(Integer.valueOf(sysParam.getValue())+1));
            sysParamRepository.save(sysParam);
        }catch (Exception e){
            e.printStackTrace();
        }
           }
}
