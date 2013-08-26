package com.genetor.base;

/**
 * Created with IntelliJ IDEA.
 * User: Dio
 * Date: 13-8-26
 * Time: 上午2:04
 * To change this template use File | Settings | File Templates.
 */
public class Field {
    private String columnType;
    private String name;
    private String dataType;
    private String name_fl;
    private String name_fu;
    private String remark;
    private int datasize;
    private boolean nullable;

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getDatasize() {
        return datasize;
    }

    public void setDatasize(int datasize) {
        this.datasize = datasize;
    }

    public String getName_fu() {
        return name_fu;
    }

    public void setName_fu(String name_fu) {
        this.name_fu = name_fu;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName_fl() {
        return name_fl;
    }

    public void setName_fl(String name_fl) {
        this.name_fl = name_fl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
