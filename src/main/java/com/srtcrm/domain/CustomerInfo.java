package com.srtcrm.domain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInfo {
    private Integer id;
    private Integer statement_id;
    private String customer_name;
    private String product_id;
    private Integer consumption;
    private String transaction_description;
    private String plan_description;
    private Integer transaction_status;
    private String remarks;
    private String update_time;
    @TableField(exist = false)
    long update_interval = -1;
}
