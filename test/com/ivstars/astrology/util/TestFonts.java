package com.ivstars.astrology.util;



import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-5-27
 * Time: 21:26:39
 * To change this template use File | Settings | File Templates.
 */
public class TestFonts {
    public static void main(String[] args){
        new AstrologyFonts();
        new TestFonts().testZodiacFont();
    }

    public void testZodiacFont(){
         JFrame frame = new JFrame("Font test");
        JTextArea ta = new JTextArea(5,8);
       // ta.setPreferredSize(new Dimension(400,300));
        ta.setFont(AstrologyFonts.getFont("gezodiac.ttf").deriveFont(36.0f));
        ta.setText("abcdefg\nhijklnm\nopqrst\nuvwxyz");
        frame.getContentPane().add(ta, BorderLayout.NORTH);
        JTextArea tb = new JTextArea(5,8);
       // ta.setPreferredSize(new Dimension(400,300));
        tb.setFont(AstrologyFonts.getFont("syastro.ttf").deriveFont(36.0f));
        tb.setText("abcdefg\nhijklnm\nopqrst\nuvwxyz".toUpperCase());
        frame.getContentPane().add(tb, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
