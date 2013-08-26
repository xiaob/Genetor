package com.genetor;

import com.genetor.ui.MainFrame;
import com.genetor.util.FrameTool;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Dio
 * Date: 13-8-25
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        try {
            resetStyle();
            MainFrame login = new MainFrame();
            login.setResizable(false);
            login.setSize(340, 350);
            FrameTool.setCenter(login);
            login.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
            login.setTitle("Genetor v0.1");
            login.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void resetStyle() throws Exception {
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
        initGlobalFontSetting(new Font("微软雅黑", Font.PLAIN, 14));
    }


    public static void initGlobalFontSetting(Font fnt) {
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}
