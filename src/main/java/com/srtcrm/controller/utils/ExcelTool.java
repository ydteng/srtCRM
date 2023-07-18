package com.srtcrm.controller.utils;

import com.srtcrm.domain.CustomerInfo;
import com.srtcrm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ExcelTool {
    @Autowired
    private CustomerService customerService;
    public List<CustomerInfo> getData(Integer statement_id) {
        List<CustomerInfo> customerInfoListist = customerService.getAllByStatementId(statement_id);
        for (int i = 0; i < customerInfoListist.size(); i++) {
            customerInfoListist.get(i).setId(i+1);
        }
        return customerInfoListist;
    }

    public List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("表头一");
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("表头一");
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }
}
