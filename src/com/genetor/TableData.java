package com.genetor;

import com.genetor.base.Field;
import com.genetor.common.DBModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dio
 * Date: 13-8-26
 * Time: 上午12:12
 * To change this template use File | Settings | File Templates.
 */
public class TableData {
    private static final long serialVersionUID = 1L;
    List<Field> rowSelectList;
    private String selecedTable;
    private String objectName;
    private String storePath;
    private DBModel model;
    private String pk;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Field> getRowSelectList() {
        return rowSelectList;
    }

    public void setRowSelectList(List<Field> rowSelectList) {
        this.rowSelectList = rowSelectList;
    }

    public String getSelecedTable() {
        return selecedTable;
    }

    public void setSelecedTable(String selecedTable) {
        this.selecedTable = selecedTable;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public DBModel getModel() {
        return model;
    }

    public void setModel(DBModel model) {
        this.model = model;
    }
}
