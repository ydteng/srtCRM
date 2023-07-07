package com.srtcrm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StatementInfo {
    private Integer id;
    private Integer user_id;
    private String file_name;
    private Integer total_transaction;
    private Integer total_usage;
}
