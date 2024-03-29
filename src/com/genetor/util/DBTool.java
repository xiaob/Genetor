package com.genetor.util;

import com.genetor.base.Field;
import com.genetor.common.DBConnection;
import com.genetor.common.DBFactory;
import com.genetor.common.DBModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * 数据库操作工具类
 *
 * @author <a href= "mailto:chengzhiping@sinosoft.com.cn"> chengzhiping@sinosoft.com.cn</a>
 *         3:07:34 PM May 11, 2012
 */
public class DBTool {
    private static final Log log = LogFactory.getLog(DBTool.class);
    private static Properties dataTypeProperties;

    public static Connection getDBCon(DBModel model) throws SQLException, ClassNotFoundException {
        DBFactory db = new DBFactory();
        Connection conn = db.getDBConnectionInstance(model).getConnection();
        return conn;
    }

    /**
     * 获取所有表表名
     *
     * @param cnn
     * @return
     * @throws SQLException
     */
    public static List getAllTableName(Connection cnn) throws SQLException {
        List tables = new ArrayList();
        DatabaseMetaData dbMetaData = cnn.getMetaData();
        //可为:"TABLE",   "VIEW",   "SYSTEM   TABLE",
        //"GLOBAL   TEMPORARY",   "LOCAL   TEMPORARY",   "ALIAS",   "SYNONYM"
        String[] types = {"TABLE"};
        ResultSet tabs = dbMetaData.getTables(null, null, null, types);
        while (tabs.next()) {
            //只要表名这一列
            tables.add(tabs.getObject("TABLE_NAME"));
        }
        log.info(tables);
        return tables;

    }

    /**
     * 获取某表下所有字段
     *
     * @param conn
     * @param tableName:表名
     * @return
     * @throws SQLException
     */
    public static List getAllColumnInfoV2(Connection conn, String tableName) throws SQLException {
        List columns = new ArrayList();
        //预编译的 SQL 语句
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName);
        //获取结果集
        ResultSet rs = pstmt.executeQuery();
        //获取所有字段名
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rsmd != null) {
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String columnLabel = rsmd.getColumnLabel(i);
                log.info(tableName + "->ColumnNameType======" + rsmd.getColumnTypeName(i));
                log.info(tableName + "->ColumnDisplaySize======" + rsmd.getColumnDisplaySize(i));
                log.info(tableName + "->ColumnName======" + rsmd.getColumnName(i));
                columns.add(rsmd.getColumnTypeName(i) + "-" + rsmd.getColumnDisplaySize(i) + "-" + rsmd.getColumnName(i) + "-" + columnLabel);
//				//判断当前字段是否为主键，如果不是主键则加入到Vector集合中
//				if(!DBFactory.isPrimaryKey(conn,tableName, rsmd.getColumnName(i)))
            }
        }
        return columns;
    }

    public static Field getColumnField(Connection conn, String dbtype, String tableName, String columnName) throws SQLException {
        Field field = new Field();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, columnName);
        if (columnSet != null) {
            while (columnSet.next()) {

                String columnTypeName = columnSet.getString("TYPE_NAME");
                String columnComment = columnSet.getString("REMARKS");
                int datasize = columnSet.getInt("COLUMN_SIZE");
                int nullable = columnSet.getInt("NULLABLE");


                String fieldType = translateToJavaType(dbtype, columnTypeName);
                String fieldName_fl = initialStrToLower(columnName);
                String fieldName_fu = initialStrUpper(columnName);

                field.setName(columnName);
                field.setDataType(fieldType);
                field.setName_fl(fieldName_fl);
                field.setName_fu(fieldName_fu);
                field.setRemark(columnComment);
                field.setDatasize(datasize);
                field.setNullable(nullable == 1 ? false : true);

            }
        }
        return field;
    }

    public static List<Object[]> getAllColumnData(Connection conn, String tableName) throws SQLException {
        List<Object[]> objects = new ArrayList<Object[]>();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
        if (columnSet != null) {
            while (columnSet.next()) {
                Object[] object = {true, columnSet.getString("COLUMN_NAME"), columnSet.getString("TYPE_NAME"), columnSet.getString("COLUMN_SIZE"), columnSet.getString("REMARKS"), ""};
                objects.add(object);
            }
        }
        return objects;
    }


    /**
     * 获取主键
     *
     * @param tableName 表面
     * @return 返回 true 为是主键 ，返回false则不是
     * @throws SQLException
     */
    public static String getPrimaryKey(Connection conn, String tableName) {
        try {
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet primaryKey = dbMeta.getPrimaryKeys(null, null, tableName);
            while (primaryKey.next()) {
                return primaryKey.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "id";
    }


    private static String translateToJavaType(String dbType, String columnType) {
        if (dataTypeProperties == null) {
            dataTypeProperties = Config.read(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "datatype.ini");
        }

        columnType = columnType.replaceAll(String.valueOf((char) 32), "");

        if (DBConnection.ORACLE_FLAG.equals(dbType)) {
            columnType = dataTypeProperties.getProperty(DBConnection.ORACLE_FLAG + "." + columnType.toUpperCase().trim());
        }
        if (DBConnection.MYSQL_FLAG.equals(dbType)) {
            columnType = dataTypeProperties.getProperty(DBConnection.MYSQL_FLAG + "." + columnType.toUpperCase().trim());
        }
        return columnType;
    }

    public static String initialStrToLower(String str) {
        String result = clearString(str);
        return result.substring(0, 1).toLowerCase() + result.substring(1);
    }

    public static String initialStrUpper(String str) {
        String result = clearString(str);
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    public static String clearString(String str) {
        StringBuffer sb = new StringBuffer();
        String[] elements = str.split("-");
        for (String objectName : elements) {
            sb.append(objectName.substring(0, 1).toUpperCase()).append(objectName.substring(1));
        }

        elements = sb.toString().split("_");
        sb = new StringBuffer();
        for (String objectNameElement : elements) {
            sb.append(objectNameElement.substring(0, 1).toUpperCase()).append(objectNameElement.substring(1, objectNameElement.length()));
        }
        return sb.toString();
    }
}
