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

import com.ivstars.astrology.*;
import com.ivstars.astrology.util.CommandParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.sf.anole.Messager;
import net.sf.anole.MessagerFactory;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.text.ParseException;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import nl.loremipsum.gif.filters.NeuQuantQuantizerOP;

/**
 * Chart
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Chart extends JComponent implements Runnable, ActionListener {
    private static Log log = LogFactory.getLog(Chart.class);
    private static net.sf.anole.Messager messager = MessagerFactory.getMessager(Constants.BASE_PACKAGE);
    private static Map<String, ChartRender> rendermap;

    private Color background = Color.black;
    private Color foreground = Color.white;
    private JTextArea txt;
    private Calendar calendar;
    private ChartModel model;
    private ChartRender render;
    private ChartOptions options;
    private JFileChooser fc;
    private Frame parent;
    private ChartModelDialog dialog;
    private String defaultRender;
    private Image image;
    StringRender stringrender;
    boolean runflag = false;

    public Chart(Frame parent) {
        this.parent = parent;
        this.setLayout(null);
        txt = new JTextArea();
        txt.setEditable(false);
        //txt.setPreferredSize(new Dimension(200,800));
        txt.setBackground(Color.black);
        txt.setForeground(Color.white);
        txt.setFont(new Font("Dialog", Font.PLAIN, 12));
        txt.setAlignmentY(JLabel.TOP_ALIGNMENT);
        calendar = Calendar.getInstance();

        //add key listener
        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    inputCommand();
                } else if (code == KeyEvent.VK_P) {
                    switchAnimation();
                } else if (code == KeyEvent.VK_S) {
                    screenshot();
                } else if (code == KeyEvent.VK_I) {
                    changeRender(rendermap.get("render.ImageRender"));
                } else if (code == KeyEvent.VK_D) {
                    changeRender(rendermap.get("render.DefaultRender"));
                } else if (code == KeyEvent.VK_G) {
                    System.gc();
                } else if (code == KeyEvent.VK_L) {
                    changeLanguage();
                } else if (code == KeyEvent.VK_H) {
                    help();
                }
            }


        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if ((e.getModifiers() & MouseEvent.META_MASK) != 0)
                    help();
                else requestFocusInWindow();
            }
        });
        Config config = Config.getConfig(Config.DEFAULT_CONFIG_FILE);
        model = new ChartModel(calendar.getTime(), config.getFloat("defaultlocation.latitude", 39.92f),
                config.getFloat("defaultlocation.longitude", 116.46f));
        model.getLocation().setName(config.getString("defaultlocation.name"));
        defaultRender = config.getString("DefaultRender", "DefaultRender");
        int[] ids = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        options = ChartOptions.buildOptions(ids);
        model.setOptions(options);
        try {
            render = rendermap.get("render." + defaultRender);
        } catch (Exception e) {
            log.debug("Can't get render " + defaultRender);
        }
        if (render == null) {
            render = new DefaultRender();
        }
        stringrender = new StringRender();
        //this.setToolTipText(messager.getMessage("chart.help"));  
        /*  DEBUG CODE
        parent.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                log.debug("WINDOW EVENT:\t" + e);
            }
        });
        this.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                log.debug("Property of Chart change:\t" + evt);
            }

        });*/
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        float w = this.getWidth();//-200;
        float h = this.getHeight();
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (image == null) {
            render();
        }
        g.drawImage(image, 0, 0, this);
    }

    protected void render() {
        options.size = this.getSize();
        model.setOptions(options);
        image = (Image) render.render(model);
        //log.debug(stringrender.toString());
        txt.setText((String) stringrender.render(model));
    }

    public Dimension getPreferredSize() {
        return new Dimension(576, 576);
    }

    public static void addRender(ChartRender render) {
        rendermap.put("render." + render.getName(), render);
    }

    public void repaint() {
        this.render();
        super.repaint();
    }

    public static void main(String[] args) {
        try {
            String laf = Config.getConfig(Config.DEFAULT_CONFIG_FILE).getString("swing.laf",
                    UIManager.getSystemLookAndFeelClassName());
            //it seems does not work on my Ubuntu 
		//UIManager.setLookAndFeel(laf);
        } catch (Exception e) {
            log.debug("Set look and feel failed.", e);
        }
        GuiHelper helper = GuiHelper.getInstance();
        JLabel info = helper.createJLabel("Loading", true);
        JWindow window = helper.createJSplashWindow(helper.createImageIcon("splash", "jastrology logo"), info);
        window.setVisible(true);
        try {
            info.setText(messager.getMessage("progress.initvelocity"));
            Config.initVelocity();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        info.setText(messager.getMessage("progress.initframe"));
        VersionInfo v = Config.getVersionInfo();
        JFrame frame = new JFrame(messager.getMessage("AstroFrame.title") + " " + v.getVersion() + " " + v.getRelease());

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Config.class.getResource("/images/icon.png")));

        info.setText(messager.getMessage("progress.load.render"));
        rendermap = new Hashtable<String, ChartRender>();
        addRender(new DefaultRender());
        addRender(new ImageRender());
        info.setText(messager.getMessage("progress.initchart"));
        final Chart chart = new Chart(frame);

        info.setText(messager.getMessage("progress.buildmenu"));
        frame.setJMenuBar(chart.buildMenuBar(helper));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(chart, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(chart.txt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(200, 620));
        frame.getContentPane().add(scroll, BorderLayout.EAST);
        frame.setBounds(50, 50, 780, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        window.dispose();
        new Thread(chart).start();
        chart.requestFocusInWindow();

    }

    public void run() {
        while (true) {
            if (runflag) {
                calendar.setTime(model.getDate());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                model = new ChartModel(calendar.getTime(), model.getTimezone().getID(), model.getLatitude(), model.getLongitude());
                //render();
                this.repaint();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand().toLowerCase();
        if (command.equals("exit")) {
            System.exit(0);
        } else if (command.equals("export")) {
            screenshot();
        } else if (command.equals("editmain")) {
            edit();
        } else if (command.equals("shortcuts")) {
            help();
        } else if (command.equals("about")) {
            about();
        } else if (command.equals("objects")) {
            changeObjects();
        } else if (command.equals("inputcommand")) {
            inputCommand();
        } else if (command.startsWith("hs_")) {
            changeHouseSystem(e.getActionCommand().charAt(3));
        } else if (command.startsWith("render.")) {
            changeRender(rendermap.get(e.getActionCommand()));
        }
    }

    //action methods
    private void inputCommand() {
        runflag = false;
        String value = JOptionPane.showInputDialog(this, messager.getMessage("InputCommand.message"));
        if (value != null)
            try {
                model = CommandParser.parse(value);
                //render();
                repaint();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
    }

    private void switchAnimation() {
        runflag = !runflag;
    }

    private void screenshot() {
        runflag = false;
        if (null == fc) {
            fc = new JFileChooser();
            fc.addChoosableFileFilter(new SimpleFileFilter(new String[]{"png", "gif", "jpg"}, "Image Files"));
            fc.setAccessory(new ImageFilePreviewer(fc));
        }
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            options.size = new Dimension(576, 576);
            model.setOptions(options);
            try {
                String format = SimpleFileFilter.getExtension(file);
                if (format == null) {
                    format = "png";
                    file = new File(file.getAbsolutePath() + ".png");
                }
                BufferedImage image = (BufferedImage) render.render(model);

                //TODO should use another gif writer? Note the same issue of AstrologyServlet renderImage() method
                if ("gif".equals(format)) {
                    new NeuQuantQuantizerOP().filter(image, image);
                }
                
                ImageIO.write(image, format, file);
                JOptionPane.showMessageDialog(this, messager.getMessage("success.writeImage.message", new String[]{file.getAbsolutePath()}),
                        messager.getMessage("success.writeImage.title"), JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                log.debug("Error while write horoscope", e);
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        messager.getMessage("error.writeImage.title"), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void changeRender(ChartRender render) {
        this.render = render;
        //render();
        repaint();
    }

    private void changeLanguage() {
        Locale locale = Locale.getDefault();
        log.debug("previous locale:" + locale);
        if (locale.getLanguage().equals("zh")) {
            Locale.setDefault(Locale.ENGLISH);
        } else {
            Locale.setDefault(Locale.CHINA);
        }
        log.debug("current locale changed to:" + Locale.getDefault());
        //render();
        repaint();
    }

    private void edit() {
        if (dialog == null) {
            dialog = new ChartModelDialog(this.parent, true);
        }
        dialog.setModel(model);
        dialog.setTitle(messager.getMessage("MenuItem.editmain"));
        dialog.setLocationRelativeTo(this.parent);
        dialog.setVisible(true);
        if (dialog.getModel() != null) {
            this.model = dialog.getModel();
            //render();
            repaint();
        }
    }

    private void help() {
        String help = messager.getMessage("chart.help");
        JOptionPane.showMessageDialog(this, help);
    }

    private void about() {
        //TODO show about dialog
        JOptionPane.showMessageDialog(this,
                messager.getMessage("jastrology.about", new Object[]{Config.getVersionInfo()}),
                messager.getMessage("MenuItem.about"), JOptionPane.PLAIN_MESSAGE);
    }

    private void changeHouseSystem(int hs) {
        log.debug("change house system to " + (char) hs);
        options.setHouseSystem(hs);
        model.calc();
        //render();
        repaint();
    }

    private void changeObjects() {
        ObjectSelectionDialog sdialog = new ObjectSelectionDialog(this.parent, true);
        sdialog.setTitle(messager.getMessage("ObjectSelectionDialog.title"));
        sdialog.setup(options);
        sdialog.setLocationRelativeTo(this.parent);
        int result = sdialog.showDialog();
        if (result == JOptionPane.OK_OPTION) {
            options.cusps = sdialog.getSelectedCusps();
            options.planets = sdialog.getSelectedPlanets();
            model.setOptions(options);
            model.calc();
            //render();
            repaint();
        }
        sdialog.dispose();
    }

    //
    private JMenuBar buildMenuBar(GuiHelper helper) {
        JMenuBar menubar = new JMenuBar();
        //file menu
        JMenu menu = menubar.add(helper.createJMenu("file", 'F'));
        menu.add(helper.createJMenuItem("export", 'E', KeyStroke.getKeyStroke("alt E"))).addActionListener(this);
        menu.addSeparator();
        menu.add(helper.createJMenuItem("exit", 'X', KeyStroke.getKeyStroke("typed q"))).addActionListener(this);

        //edit menu
        menu = menubar.add(helper.createJMenu("edit", 'E'));
        menu.add(helper.createJMenuItem("editmain", '0', KeyStroke.getKeyStroke("alt 1"))).addActionListener(this);
        menu.add(helper.createJMenuItem("inputcommand", 'I', null)).addActionListener(this);
        //setting menu
        menu = menubar.add(helper.createJMenu("setting", 'S'));
        JMenu hsmenu = (JMenu) menu.add(helper.createJMenu("house.system", 'H'));
        ButtonGroup group_hs = new ButtonGroup();
        char[] hs = new char[]{'P', 'K', 'O', 'R', 'C', 'A', 'E', 'V', 'X', 'H', 'T', 'B'};
        for (int i = 0; i < hs.length; i++) {
            JMenuItem item = hsmenu.add(helper.createJRadioMenuItem("hs_" + hs[i], hs[i],
                    KeyStroke.getKeyStroke("control shift " + hs[i])));
            item.addActionListener(this);
            if (options.getHouseSystem() == hs[i]) item.setSelected(true);
            group_hs.add(item);
        }
        menu.addSeparator();
        menu.add(helper.createJMenuItem("objects", '0', KeyStroke.getKeyStroke("alt R"))).addActionListener(this);

        JMenu rmenu = (JMenu) menu.add(helper.createJMenu("render", 'R'));
        ButtonGroup group_render = new ButtonGroup();

        for (ChartRender render : rendermap.values()) {
            JMenuItem item = rmenu.add(helper.createJRadioMenuItem("render." + render.getName(), '0', null));
            item.addActionListener(this);
            if (render.getName().equals(defaultRender)) item.setSelected(true);
            group_render.add(item);
        }
        // help menu
        menu = menubar.add(helper.createJMenu("help", 'H'));
        menu.add(helper.createJMenuItem("shortcuts", 'K', null)).addActionListener(this);
        menu.add(helper.createJMenuItem("about", 'A', null)).addActionListener(this);
        //menubar.setHelpMenu(menu);
        return menubar;
    }
}
