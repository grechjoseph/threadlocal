package com.jg.threadlocal;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Context {

    private UUID id;
    private String name;

}
