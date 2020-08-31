package com.xcmis.framework.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 功能
 *
 * @author
 * @see
 */
@Configuration
public class TaskPoolConfig {
    @Bean("taskExecutor-untax")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);//核心线程数目
        executor.setMaxPoolSize(20);//指定最大线程数
        executor.setQueueCapacity(200);//队列中最大的数目
        executor.setKeepAliveSeconds(60);//线程空闲后的最大存活时间
        executor.setThreadNamePrefix("untax-taskExecutor-");//线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
