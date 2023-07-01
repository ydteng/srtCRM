package com.srtcrm.controller.utils;

import lombok.Data;

@Data
public class R {
    private Boolean status;
    private Object data;
    private String msg;

    public R(Boolean status, Object data, String msg){
        this.status = status;
        this.data = data;
        this.msg = msg;

    }
}
