package com.example.demo.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse {
    private boolean status;
    private String statusMessage;
    private Object response;
    private Instant systemTime;

}