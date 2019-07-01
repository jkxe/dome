package cn.fintecher.pangolin.business;

import cn.fintecher.pangolin.business.model.UserSimpleModel;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableAsync
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"cn.fintecher.pangolin.business.repository", "cn.fintecher.pangolin.entity"})
@EntityScan("cn.fintecher.pangolin.entity")
public class PangolinBusinessApplication {
    private static final Logger log = LoggerFactory.getLogger(PangolinBusinessApplication.class);



    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Queue unReduceSuccessQueue() {
        return new Queue(Constants.DATAINFO_CONFIRM_QE);
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(PangolinBusinessApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "SwaggerUI: \thttp://localhost:{}/swagger-ui.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.port"));
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.addMappings(new PropertyMap<User, UserSimpleModel>() {
            @Override
            protected void configure() {
                map().setDeptId(source.getDepartment().getId());
            }
        });
        return modelMapper;
    }
}
