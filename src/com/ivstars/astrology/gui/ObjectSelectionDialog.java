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

import com.ivstars.astrology.ChartOptions;
import com.ivstars.astrology.Constants;
import com.ivstars.astrology.PlanetInfo;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import net.sf.anole.Messager;
import net.sf.anole.MessagerFactory;

/**
 * a dialog for object selection
 * @author  Administrator
 */
public class ObjectSelectionDialog extends javax.swing.JDialog {
    private static Messager messager = MessagerFactory.getMessager(Constants.BASE_PACKAGE);
    /*
    Chiron	15
    Phol	16
    Cere	17
    Pall	18
    Juno	19
    Vest	20
    Lili	12
     
    Fort	-86
    Vert	-85
    East	-84
    SoNo	-83
    NoNo	-82
     */
    
    private int flag;
    private List<Integer> selectedPlanets;
    private List<Integer> selectedCusps;
    private java.awt.Font bFont = GuiHelper.getFont("Button");
    /** Creates new form ObjectSelectionDialog */
    public ObjectSelectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        flag = JOptionPane.CANCEL_OPTION;
        planets_bg= new ArrayList<AbstractButton>(22);
        cusps_bg = new ArrayList<AbstractButton>(12);
        initComponents();
    }
    public void setup(ChartOptions options){
        //Enumeration<AbstractButton> buttons = ;
        for(AbstractButton button : planets_bg){
            //AbstractButton button = buttons.nextElement();
            boolean selected=(options.planets.contains(new Integer(button.getActionCommand())));
            button.setSelected(selected);
        }
        
        
        
        for(AbstractButton button :cusps_bg){            
            boolean selected=options.cusps.contains(new Integer(button.getActionCommand()));
            button.setSelected(selected);
        }
    }
    
    /**
     * show this dialog
     * @return JOptionPane.OK_OPTION if OK button is clicked, otherwise CANCEL_OPTION
     */
    public int showDialog(){
        this.setVisible(true);
        return flag;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" 生成的代码 ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), messager.getMessage("ObjectSelectionDialog.Planets"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, GuiHelper.getFont("border")));
        for(int i : Constants.PLANETS){
            JCheckBox cb = new JCheckBox();
            cb.setActionCommand(Integer.toString(i));
            cb.setFont(bFont);
            cb.setText(messager.getMessage(PlanetInfo.getPlanetName(i)));
            jPanel1.add(cb);
            planets_bg.add(cb);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), messager.getMessage("ObjectSelectionDialog.Minor"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, GuiHelper.getFont("border")));
        for(int i : Constants.MINOR_OBJECTS){
            JCheckBox cb = new JCheckBox();
            cb.setActionCommand(Integer.toString(i));
            cb.setFont(bFont);
            cb.setText(messager.getMessage(PlanetInfo.getPlanetName(i)));
            jPanel2.add(cb);
            planets_bg.add(cb);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), messager.getMessage("ObjectSelectionDialog.Cusps"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, GuiHelper.getFont("border")));
        for(int i=-99;i<-87;i++){
            JCheckBox cb = new JCheckBox();
            cb.setActionCommand(Integer.toString(i));
            cb.setFont(bFont);
            cb.setText(messager.getMessage(PlanetInfo.getPlanetName(i)));
            jPanel3.add(cb);
            cusps_bg.add(cb);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel3, gridBagConstraints);

        ok.setFont(bFont);
        ok.setText(messager.getMessage("OK"));
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        getContentPane().add(ok, gridBagConstraints);

        cancel.setFont(bFont);
        cancel.setText(messager.getMessage("Cancel"));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        getContentPane().add(cancel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        flag=JOptionPane.CANCEL_OPTION;
        setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed
    
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        
        selectedPlanets = new ArrayList<Integer>();
        for(AbstractButton button : planets_bg){
            if(button.isSelected())
                selectedPlanets.add(new Integer(button.getActionCommand()));
        }       
        
        selectedCusps = new ArrayList<Integer>();
        for(AbstractButton button : cusps_bg){            
            if(button.isSelected())
                selectedCusps.add(new Integer(button.getActionCommand()));
        }
        
        flag=JOptionPane.OK_OPTION;
        setVisible(false);
    }//GEN-LAST:event_okActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ObjectSelectionDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    public List<Integer> getSelectedPlanets() {
        return selectedPlanets;
    }
    
    public List<Integer> getSelectedCusps() {
        return selectedCusps;
    }
    
    // 变量声明 - 不进行修改//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton ok;
    // 变量声明结束//GEN-END:variables
    private java.util.List<AbstractButton> planets_bg ;
    
    private java.util.List<AbstractButton> cusps_bg;
}
