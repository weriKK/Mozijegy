/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class NewMovieDialog extends OKCancelDialog {
    
    JTextField txtTitle;
    JTextField txtOrigin;
    JComboBox  cbDubbing;
    JTextField txtDirector;
    JTextField txtLength;
    JTextArea txtSynopsis;    
    
    JLabel lblTitle;
    JLabel lblOrigin;
    JLabel lblDubbing;
    JLabel lblDirector;
    JLabel lblLength;
    JLabel lblSynopsis;
    
    
    public NewMovieDialog(Window owner, SQLServer db) {
        super(owner,"Új film felvétele",Dialog.ModalityType.APPLICATION_MODAL);
        
        InitializeDialog();
        
    }
    
    private void InitializeDialog() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(320,480));
        this.setResizable(false);
        
        txtTitle = new JTextField();
        txtOrigin = new JTextField();
        txtDirector = new JTextField();
        
        try {
            txtLength = new JFormattedTextField(new MaskFormatter("###"));
        } catch (ParseException ex) {
            //Logger.getLogger(NewMovieDialog.class.getName()).log(Level.SEVERE, null, ex);
            txtLength = new JTextField();
        }
        txtLength.setMaximumSize(new Dimension(30, txtLength.getSize().height));         
        
        txtSynopsis = new JTextArea();
        txtSynopsis.setRows(8);
        txtSynopsis.setEditable(true);
        txtSynopsis.setLineWrap(true);
        txtSynopsis.setWrapStyleWord(true);        
                
        cbDubbing = new JComboBox();
        cbDubbing.addItem("szinkronizált");
        cbDubbing.addItem("feliratos");
        
        lblTitle = new JLabel("Cím:");
        lblOrigin = new JLabel("Eredet:");
        lblDubbing = new JLabel("Felirat:");
        lblDirector = new JLabel("Rendező:");
        lblLength = new JLabel("Film hossz (perc):");
        lblSynopsis = new JLabel("Szinopszis:");
        
        GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);
        
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(txtTitle)
                    .addComponent(lblOrigin)
                    .addComponent(txtOrigin)
                    .addComponent(lblDubbing)
                    .addComponent(cbDubbing)
                    .addComponent(lblDirector)
                    .addComponent(txtDirector)
                    .addComponent(lblLength)
                    .addComponent(txtLength)                
                    .addComponent(lblSynopsis)
                    .addComponent(txtSynopsis)
                    .addComponent(buttonPanel)
        );
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(lblTitle)
                    .addComponent(txtTitle)
                    .addComponent(lblOrigin)
                    .addComponent(txtOrigin)
                    .addComponent(lblDubbing)
                    .addComponent(cbDubbing)
                    .addComponent(lblDirector)
                    .addComponent(txtDirector)
                    .addComponent(lblLength)
                    .addComponent(txtLength)                
                    .addComponent(lblSynopsis)
                    .addComponent(txtSynopsis)
                    .addComponent(buttonPanel)                
        );
                
        this.disableOK();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                disableOK();
            }
        });

        this.pack();    
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);
    }    

    @Override
    protected boolean processOK() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void processCancel() {
        txtTitle.setText(null);
        txtOrigin.setText(null);
        cbDubbing.setSelectedIndex(0);
        txtDirector.setText(null);
        txtLength.setText(null);
        txtSynopsis.setText(null);
    }
    
}
