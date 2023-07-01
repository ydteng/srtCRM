package com.srtcrm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInfo {
    private Integer id;
    private Integer statement_id;
    private String customer_name;
    private String product_id;
    private int consumption;
    private String transaction_status;
    private String plan_status;
    private String remarks;
}
