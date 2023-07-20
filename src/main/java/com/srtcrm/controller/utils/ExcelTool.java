package com.srtcrm.controller.utils;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.srtcrm.domain.CustomerInfo;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.service.CustomerService;
import com.srtcrm.service.StatementService;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ExcelTool {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StatementService statementService;
    public List<CustomerInfo> getData(Integer statement_id) {
        List<CustomerInfo> customerInfoListist = customerService.getAllByStatementId(statement_id);
        for (int i = 0; i < customerInfoListist.size(); i++) {
            customerInfoListist.get(i).setId(i+1);
        }
        return customerInfoListist;
    }

    public List<List<String>> head(Integer statement_id) {
        StatementInfo statementInfo = statementService.getById(statement_id);
        Integer totalUsage = statementInfo.getTotal_usage();
        Integer totalTransaction = statementInfo.getTotal_transaction();
        List<List<String>> headList = new ArrayList<List<String>>();
        String[] headSon = {"序号","客户名称","适用产品","用量（吨）","成交与否及未成交原因","计划措施及跟进情况","备注"};
        for (int i = 0; i <7; i++){
            List<String> head = new ArrayList<String>();
            head.add("               类市场客户信息汇总月报表（   年  月）");
            head.add("  市场总量:"+totalUsage+"（单位：吨）                     已成交量:"+totalTransaction+"（单位：吨）");
            head.add(headSon[i]);
            headList.add(head);
        }
        return headList;
    }



    public HorizontalCellStyleStrategy handlerStyleWrite() {
        // 方法1 使用已有的策略 推荐
        // HorizontalCellStyleStrategy 每一行的样式都一样 或者隔行一样
        // AbstractVerticalCellStyleStrategy 每一列的样式都一样 需要自己回调每一页
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)11);
        headWriteFont.setFontName("宋体");
        headWriteFont.setBold(false);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)11);
        headWriteFont.setFontName("宋体");
        headWriteFont.setBold(false);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
