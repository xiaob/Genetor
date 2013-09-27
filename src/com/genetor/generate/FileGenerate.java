package com.genetor.generate;

import com.genetor.TableData;
import com.genetor.base.Field;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dio
 * Date: 13-8-26
 * Time: 上午12:17
 * To change this template use File | Settings | File Templates.
 */
public class FileGenerate {

    static String appPath = System.getProperty("user.dir") + File.separator;
    static String templatePath = appPath + "template" + File.separator;


    public static void engineEntry(TableData tableData, Connection conn) {

        List<File> allTemplates = new ArrayList<File>();
        getAllTemplate(templatePath, allTemplates);

        try {
            for (File tplFile : allTemplates) {
                String tplPath = getVelocityTplPath(tplFile);
                String outPath = getOutPath(tableData, tplFile);
                Template tpl = Velocity.getTemplate(tplPath, "UTF-8");
                File outFile = new File(outPath);
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
                PrintWriter pw = new PrintWriter(outPath,"UTF-8");

                tpl.merge(getContext(tableData, conn), pw);
                pw.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static VelocityContext getContext(TableData tableData, Connection conn) throws SQLException {
        VelocityContext context = new VelocityContext();
        context.put("objectName", tableData.getObjectName());
        context.put("tableName", tableData.getSelecedTable());
        context.put("dbName", tableData.getModel().getDBname());
        context.put("pk", tableData.getPk());

        List<Field> fieldList = getFieldList(tableData, conn);
        context.put("fieldList", fieldList);
        return context;
    }

    private static List<Field> getFieldList(TableData tableData, Connection conn) throws SQLException {
        List<Field> rowSelectList = tableData.getRowSelectList();

        return rowSelectList;
    }

    public static String getVelocityTplPath(File tplFile) {
        String path = tplFile.getPath();
        path = path.replace(appPath, "");
        return path;
    }

    public static String getOutPath(TableData data, File tplFile) throws IOException {
        StringBuffer outPath = new StringBuffer();
        outPath.append(data.getStorePath()).append(File.separator);
        LineNumberReader lnr = new LineNumberReader(new FileReader(tplFile));
        lnr.setLineNumber(0);

        String vmInfValue = lnr.readLine();

        vmInfValue = vmInfValue.replaceFirst("^#\\*", "").replaceFirst("\\*#$", "");
        vmInfValue = vmInfValue.replaceAll("\\{objectName\\}", data.getObjectName());
        vmInfValue = vmInfValue.replaceAll("\\{tableName\\}", data.getSelecedTable());
        vmInfValue = vmInfValue.replaceAll("\\{dbName\\}", data.getModel().getDBname());

        outPath.append(vmInfValue);
        return outPath.toString();
    }


    public static void getAllTemplate(String path, List<File> allTemplates) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (prefix.equals("vm")) {
                    allTemplates.add(file);
                }
            } else {
                getAllTemplate(file.getPath(), allTemplates);
            }
        }
    }


}
