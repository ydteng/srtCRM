package com.srtcrm.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ExcelIgnoreUnannotated
@HeadRowHeight(25)
// 头背景设置成红色
@HeadStyle(fillPatternType = FillPatternTypeEnum.NO_FILL)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 11,bold = BooleanEnum.FALSE)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternTypeEnum.NO_FILL)
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 11,bold = BooleanEnum.FALSE)
public class CustomerInfo {
    @ExcelProperty("序号")
    private Integer id;
    private Integer statement_id;
    @ExcelProperty("客户名称")
    @ColumnWidth(30)
    private String customer_name;
    @ExcelProperty("适用产品")
    @ColumnWidth(15)
    private String product_id;
    @ExcelProperty("用量（吨）")
    @ColumnWidth(12)
    private Integer consumption;
    @ExcelProperty("成交与否及未成交原因")
    @ColumnWidth(25)
    private String transaction_description;
    @ExcelProperty("计划措施及跟进情况")
    @ColumnWidth(25)
    private String plan_description;
    private Integer transaction_status;
    @ExcelProperty("备注")
    @ColumnWidth(10)
    private String remarks;
}
