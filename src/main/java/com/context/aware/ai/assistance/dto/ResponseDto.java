package com.context.aware.ai.assistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private String responseCode;
    private String message;
    private Object data;


}
