package com.jg.threadlocal;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@NoArgsConstructor
public class ContextHolder {

    private static final ThreadLocal<Context> THREADLOCAL = new InheritableThreadLocal<>();

    public static Context get() {
        if(isNull(THREADLOCAL.get())) {
            set(Context.builder().build());
        }
        log.info("Getting Context: {}", THREADLOCAL.get());
        return THREADLOCAL.get();
    }

    public static void set(final Context context) {
        log.info("Setting Context: {}", context);
        THREADLOCAL.set(context);
    }

    public static void clear() {
        log.info("Clearing Context.");
        THREADLOCAL.remove();
    }

}
