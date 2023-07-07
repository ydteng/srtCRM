package com.srtcrm.domain;

import lombok.Data;

@Data
public class StatementInfo {
    private Integer id;
    private Integer user_id;
    private String statement_name;
    private Integer total_transaction;
    private Integer total_usage;
}
