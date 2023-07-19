package com.module.report.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Reason {
    ABUSE("욕설"), OBSCENE("음란"), SLANDER("비방");

    private final String description;
}
