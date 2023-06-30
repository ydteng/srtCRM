package com.srtcrm.domain;

import lombok.Data;

@Data
public class StatementInfo {
    private Integer id;
    private Integer user_id;
    private String area;
    private String date;
}
