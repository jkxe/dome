package cn.fintecher.pangolin.business.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DROOLS 初始化资源，防止在多线程调用时初始化失败
 * 应该是Drools一个BUG
 */

@Configuration
@EnableCaching
public class DroolsConfig {

    @Bean
    public KieBuilder initKieBuilder() {
        //通过工厂获取KieServices,KieServices是一个线程安全的单身人士，担任由Kie提供的其他服务的枢纽
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        //KieBuilder是KieModule中包含的资源的构建器
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs);
//        System.out.println("测试" + kieBuilder);
        //多线程情况下第一次buildAll时可能会报错
        kieBuilder.buildAll();
        return kieBuilder;
    }
}
