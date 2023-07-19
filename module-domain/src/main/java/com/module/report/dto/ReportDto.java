package com.module.report.dto;

import com.module.report.type.Reason;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class ReportDto {

    @Getter
    @Builder
    public static class CreateRequest {
        @NotNull(message = "사유는 필수 선택입니다.")
        private Reason reason;
    }
}
