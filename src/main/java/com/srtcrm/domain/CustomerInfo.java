package com.srtcrm.domain;

import lombok.Data;

@Data
public class CustomerInfo {
    private Integer id;
    private Integer statement_id;
    private String customer_name;
    private String product_id;
    private String consumption;
    private String transaction_status;
    private String plan_status;
    private String remarks;
}
