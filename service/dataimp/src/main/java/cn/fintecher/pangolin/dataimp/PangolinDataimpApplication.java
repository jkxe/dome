package cn.fintecher.pangolin.dataimp;


import cn.fintecher.pangolin.util.Snowflake;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableAsync
@ComponentScan
@EnableEurekaClient
@EnableDiscoveryClient
@MapperScan("cn.fintecher.pangolin.dataimp.mapper")
@SpringBootApplication
public class PangolinDataimpApplication {
    private static final Logger log = LoggerFactory.getLogger(PangolinDataimpApplication.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate ()
    {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory  = new SimpleClientHttpRequestFactory ();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(20*60*1000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean
    public Queue unReduceSuccessQueue() {
        return new Queue("mr.cui.file.unReduce.success");
    }


    @Bean
    public Snowflake snowflake() {
        return new Snowflake((int) (System.currentTimeMillis() % 1024));
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(PangolinDataimpApplication.class);
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
}
