package com.genetor.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dio
 * Date: 13-8-25
 * Time: 下午10:05
 * To change this template use File | Settings | File Templates.
 */
public class FrameTool {

    /**
     * 屏幕居中方法
     *
     * @param jframe
     */
    public static void setCenter(JFrame jframe) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int frameH = jframe.getHeight();
        int frameW = jframe.getWidth();
        jframe.setLocation((screenWidth - frameW) / 2, (screenHeight - frameH) / 2);
    }
}
