package in.dudeapp.userservice.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nandhan, Created on 25/07/22
 */
@Configuration
public class ExecutorServiceConfig {

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public ExecutorService saveReferencesExecutorService() {

        return new TraceableExecutorService(beanFactory, Executors.newFixedThreadPool(5));
    }
}
