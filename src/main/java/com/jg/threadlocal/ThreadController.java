package com.jg.threadlocal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ThreadController {

    private final AsyncService asyncService;

    /**
     * This endpoint demonstrates that:
     * Given:
     *  - Context is cleared from the main thread, after child threads are created (not necessarily started).
     * Then:
     *  - The asynchronous child threads will still have their inherited Context values even after the parent/main thread
     *  has cleared its own Context.
     */
    @SneakyThrows
    @GetMapping("/test")
    public String test() {
        exampleByCreatedThread();
        exampleByCallingAsync();
        return "(" + UUID.randomUUID().toString() + ") Done with main thread.";
    }

    /**
     * Given a thread is created before the THREADLOCAL is cleared, that created thread would still have the context in
     * its inherited THREADLOCAL (ie: the Context in that thread was never cleared).
     */
    private void exampleByCreatedThread() {
        ContextHolder.set(Context.builder()
                .id(UUID.randomUUID())
                .name("Test")
                .build());

        final Thread thread = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                log.info("{} - {} - {}", "Thread", i, ContextHolder.get());
                try {
                    Thread.sleep(1_000L);
                } catch (final Exception ex) {
                    log.error("Couldn't wait!", ex);
                }
            }
        });

        ContextHolder.clear();
        log.info("After Clear - {}", ContextHolder.get());
        thread.start();
    }

    /**
     * Given the Context/THREADLOCAL in the main thread is cleared before an @Async method completes, the @Async method's
     * thread still has its own inherited Context/THREADLOCAL which is never cleared in that particular thread.
     */
    private void exampleByCallingAsync() {
        ContextHolder.set(Context.builder()
                .id(UUID.randomUUID())
                .name("Test")
                .build());

        asyncService.doAsync();

        ContextHolder.clear();
        log.info("After Clear - {}", ContextHolder.get());
    }

}
