package com.jg.threadlocal;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableAsync
public class AsyncService {

    @Async
    @SneakyThrows
    public void doAsync() {
        for(int i = 0; i < 10; i++) {
            log.info("{} - {} - {}", "@Async", i, ContextHolder.get());
            Thread.sleep(1_000L);
        }
    }

}
