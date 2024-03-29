package org.example.web;

import lombok.Data;


@Data
public class SimpleResponse {
    private Integer code = 200;
    private String message = "成功";
    private Object data;
}
