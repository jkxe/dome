package cn.fintecher.pangolin.report.scheduled;

import cn.fintecher.pangolin.report.service.DistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class PredistributionScheduled {

    private static final Logger log = LoggerFactory.getLogger(PredistributionScheduled.class);

    @Autowired
    private DistributionService distributionService;

    @Scheduled(cron = "0 30 0 * * ?")
    private void predistribution() {
        distributionService.distributionCases();
    }
}
