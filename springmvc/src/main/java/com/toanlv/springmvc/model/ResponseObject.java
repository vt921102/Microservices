package com.toanlv.springmvc.model;

import org.springframework.http.ResponseEntity;

public class ResponseObject  {
    private String message;
    private String staus;
    private Object data;

    public ResponseObject() {
    }

    public ResponseObject(String message, String staus, Object data) {
        this.message = message;
        this.staus = staus;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
