/*
 * Jastrology - Copyright 2006 Fengbo Xie, All Rights Reserved.
 *
 * http://www.ivstars.com/jastrology
 * http://sourceforge.net/projects/jastrology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ivstars.astrology.gui;

import com.ivstars.astrology.Config;
import com.ivstars.astrology.Constants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.sf.anole.MessagerFactory;
import net.sf.anole.Messager;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.swing.*;
import java.awt.Color;
/**
 *
 * @author Administrator
 */
public class GuiHelper {
    private static Log log = LogFactory.getLog(GuiHelper.class);
    private static GuiHelper ourInstance;
    private static Config config = new Config("/gui.properties");
    
    static Color getColor(String key){
        return config.getColor(key);
    }
    static java.awt.Font getFont(String key){
        return config.getFont(key);
    }
    static Image getImage(String key){
        return config.getImage(key);
    }
	/**
	 * get an Instance of this class.<br>
	 * The instance is the latest allocated one.
	 * @return a GuiHelper instance. 
	 */
	public synchronized static GuiHelper getInstance() {
		if (ourInstance == null) {
			ourInstance = new GuiHelper();
		}
		return ourInstance;
	}
	
	//java.awt.Font buttonFont;
	//java.awt.Font labelFont;
	Hashtable fontlist = new Hashtable();
	/**
	*The GuiHelper can create internationalized GUI component,
	*such as button(Jbutton),menuitem(jmenuitem),ImageIcon and so on
	*/
	static Messager defaultI18ner = MessagerFactory.getMessager(Constants.BASE_PACKAGE);
	private Messager m_i18ner = null;
	public GuiHelper() {
		this(defaultI18ner);
	}
	public GuiHelper(Messager i18ner) {		
		this.m_i18ner = i18ner;       
	}
	
	/**
	 * load configuration from the I18ner.
	 * initialization should be done here!
	 */
//	public void loadConfiguration() {
//		
//        try {
//            if(!getString("swing.laf").startsWith("!"))
//			UIManager.setLookAndFeel(getString("swing.laf"));
//		} catch (ClassNotFoundException e) {
//		} catch (InstantiationException e) {
//		} catch (IllegalAccessException e) {
//		} catch (UnsupportedLookAndFeelException e) {
//		}
//	}
	/**
	 * get the value of a specified key
	 *
	 * @param key the key with which to lookup for the properties
	 * @return
	 */
	public String getString(String key) {
		String revalue =  m_i18ner.getMessage(key);
		return revalue;
	}
	/**
	 * Get an integer value with the specified key.
	 * @param key  the key with which to lookup
	 * @param defaultValue  default integer value
	 * @return   the value configurated in the properties, otherwise defaultValue
	 */
	public int getInt(String key, int defaultValue) {
		String intStr = getString(key);
		int intvalue = defaultValue;
		try {
			intvalue = Integer.parseInt(intStr.trim());
		} catch (Exception exp) {
			log.debug(exp);
			intvalue = defaultValue;
		} finally {
			return intvalue;
		}
	}
	public int getInt(String key) {
		return getInt(key, 0);
	}
	String addMo(char moni) {
		char[] ch = new char[3];
		ch[0] = '(';
		ch[1] = moni;
		ch[2] = ')';
		return new String(ch);
	}
	/**
	*create a JMenuItem
	*@param command the abstractbutton's command
	*@param monic the mnemonic character
	*@param stroker the Accelerator key stroke
	*@return a specificed JMenuItem
	*/
	public JMenuItem createJMenuItem(
		String command,
		char monic,
		KeyStroke stroker) {
		JMenuItem mi = new JMenuItem();
		String text = getString("MenuItem." + command);
		if (text.startsWith("!"))
			text = command;
		Icon icon = null;
		if (monic != '0') {
			if (text.toUpperCase().indexOf(monic) < 0)
				text = text + addMo(monic);
			mi.setMnemonic(monic);
		}
		mi.setActionCommand(command);
		mi.setText(text);
		mi.setFont(getFont("MenuItem"));
		String tooltip = getString("MenuItem." + command + ".ToolTip");
		if (!tooltip.startsWith("!"))
			mi.setToolTipText(tooltip);
		try {
			icon = createImageIcon(command, command);
		} catch (Exception e) {
			icon = null;
		}
		if (icon != null) {
			mi.setIcon(icon);
		}
		if (stroker != null)
			mi.setAccelerator(stroker);
		return mi;
	}
	/**
	*create a JMenu
	*@param command the action command
	*@param  monic the mnemonic of the JMenu
	*@return a specificed JMenu
	*/
	public JMenu createJMenu(String command, char monic) {
		JMenu mi = new JMenu();
		String text = getString("Menu." + command);
		Icon icon = null;
		if (text.startsWith("!"))
			text = command;
		if (monic != '0') {
			if (text.toUpperCase().indexOf(monic) < 0)
				text = text + addMo(monic);
			mi.setMnemonic(monic);
		}
		mi.setActionCommand(command);
		mi.setText(text);
		mi.setFont(getFont("Menu"));
		String tooltip = getString("Menu." + command + ".ToolTip");
		if (!tooltip.startsWith("!"))
			mi.setToolTipText(tooltip);
		try {
			icon = createImageIcon(command, command);
		} catch (Exception e) {
			icon = null;
		}
		if (icon != null) {
			mi.setIcon(icon);
		}
		return mi;
	}
	/**
	*create a JRadioButtonMenuItem
	*@param command the abstractbutton's command
	*@param monic the mnemonic character
	*@param stroker the Accelerator key stroke
	*@return a specificed JRadioButtonMenuItem
	*/
	public JRadioButtonMenuItem createJRadioMenuItem(
		String command,
		char monic,
		KeyStroke stroker) {
		JRadioButtonMenuItem mi = new JRadioButtonMenuItem();
		String text = getString("MenuItem." + command);
		if (text.startsWith("!"))
			text = command;
		Icon icon = null;
		if (monic != '0') {
			if (text.toUpperCase().indexOf(monic) < 0)
				text = text + addMo(monic);
			mi.setMnemonic(monic);
		}
		mi.setActionCommand(command);
		mi.setText(text);
		mi.setFont(getFont("MenuItem"));
		String tooltip = getString("MenuItem." + command + ".ToolTip");
		if (!tooltip.startsWith("!"))
			mi.setToolTipText(tooltip);
		try {
			icon = createImageIcon(command, command);
		} catch (Exception e) {
			icon = null;
		}
		if (icon != null) {
			mi.setIcon(icon);
		}
		if (stroker != null)
			mi.setAccelerator(stroker);
		return mi;
	}
	/**
	*create a JButton  without Text
	*@param command the abstractbutton's command
	*@param monic the mnemonic character
	*@return a specificed JButton without Text
	*/
	public JButton createJButton(String command, char monic) {
		return createJButton(command, monic, false);
	}
	/**
	*create a JButton
	*@param command the abstractbutton's command
	*@param monic the mnemonic character
	*@param toText true to setText to the button otherwise no text
	*@return a specificed JButton
	*@see #createJButton
	*/
	public JButton createJButton(String command, char monic, boolean toText) {
		JButton button = new JButton();
		String text = getString("Button." + command);
		if (text.startsWith("!"))
			text = command;
		Icon icon = null;
		if (monic != '0') {
			if (text.toUpperCase().indexOf(monic) < 0)
				text = text + addMo(monic);
			button.setMnemonic(monic);
		}
		button.setActionCommand(command);
		String tooltip = getString("Button." + command + ".ToolTip");
		if (!tooltip.startsWith("!"))
			button.setToolTipText(tooltip);
		try {
			icon = createImageIcon(command, command);
		} catch (Exception e) {
			icon = null;
		}
		if (icon != null) {
			button.setIcon(icon);
			if (toText) {
				button.setText(text);
				button.setFont(getFont("Button"));
				button.setHorizontalTextPosition(
					getInt(
						"Button." + command + ".HorizontalTextPosition",
						AbstractButton.TRAILING));
				button.setVerticalTextPosition(
					getInt(
						"Button." + command + ".VerticalTextPosition",
						AbstractButton.CENTER));
			}
		} else {
			button.setText(text);
			button.setFont(getFont("Button"));
		}
		return button;
	}
	public JLabel createJLabel(String command, boolean toText) {
		JLabel label = new JLabel();
		String text = getString("Label." + command);
		if (text.startsWith("!"))
			text = command;
		Icon icon = null;
		String tooltip = getString("Label." + command + ".ToolTip");
		if (!tooltip.startsWith("!"))
			label.setToolTipText(tooltip);
		try {
			icon = createImageIcon(command, command);
		} catch (Exception e) {
			icon = null;
		}
		if (icon != null) {
			label.setIcon(icon);
			if (toText) {
				label.setText(text);
				label.setFont(getFont("Label"));
				label.setHorizontalTextPosition(
					getInt(
						"Label." + command + ".HorizontalTextPosition",
						JLabel.TRAILING));
				label.setVerticalTextPosition(
					getInt(
						"Label." + command + ".VerticalTextPosition",
						JLabel.CENTER));
			}
		} else {
			label.setText(text);
			label.setFont(getFont("Label"));
		}
		return label;
	}
	
	
    
    private Hashtable colorlist=new Hashtable();
    /**
     * get a color from the configuration file.
     * @param key the key of the color object, GuiHelper user the
     * name $key$. Color to find color configed in the properties.It should be a
     * 24-bit integer
     * @return Color the color defined in the file with the specified key or the
     * current LookAndFeel control's color if there's no color configed with the
     * very key name.
     */
    
	/**
	*create a Splash Window
	*@return a splash window.
	*/
	public JWindow createJSplashWindow(Icon icon,Component info) {
		JWindow window = new JWindow();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		JLabel label = new JLabel();
		if (icon != null)
			label.setIcon(icon);
                window.getContentPane().add(label, BorderLayout.CENTER);		
		window.getContentPane().add(info, BorderLayout.SOUTH);
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		Dimension screenSize = toolkit.getScreenSize();
		window.pack();
                Dimension labelSize = window.getSize();
		window.setLocation(
			(screenSize.width / 2 - labelSize.width / 2),
			(screenSize.height / 2 - labelSize.height / 2));
		
		return window;
	}
	/**
	*create an ImageIcon
	*@param filename the images filename.The Image file should be in the path like "resource/images"
	*@param description the description of this ImageIcon
	*@return an ImageIcon
	*/
	public ImageIcon createImageIcon(String command, String description) {
		Image img =getImage("Icon."+command);
                if(img==null)
                    return null;
		return new ImageIcon(img);
	}
    /**
     * get system clipboard contents with specified <code>DataFlavor</code>
     * @param flavor the target DataFlavor
     * @return Object the system clipboard contents
     */
	public static Object getClipboard(DataFlavor flavor) {
		Transferable t =
			Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        Object obj=null;
		try {
			if (t != null
				&& t.isDataFlavorSupported(flavor)) {
				obj = t.getTransferData(flavor);				
			}
		} catch (UnsupportedFlavorException e) {
                    log.debug(e);
                    obj=null;            
		} catch (IOException e) {
                    log.debug(e);
                    obj=null;            
		}
                return obj;
	}
    
}
