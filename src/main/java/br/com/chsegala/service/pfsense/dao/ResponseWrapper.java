package br.com.chsegala.service.pfsense.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private String status;
    private Integer code;
    @JsonProperty("return")
    private String ret;
    private String message;
    private List<T> data;
}
