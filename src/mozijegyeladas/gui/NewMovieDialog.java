/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class NewMovieDialog extends OKCancelDialog {
    
    SQLServer db;
    
    JTextField txtTitle;
    JTextField txtOrigin;
    JComboBox  cbDubbing;
    JTextField txtDirector;
    JTextField txtLength;
    JTextArea txtSynopsis; 
    
    ArrayList<JComponent> componentContainer;
    
    
    JLabel lblTitle;
    JLabel lblOrigin;
    JLabel lblDubbing;
    JLabel lblDirector;
    JLabel lblLength;
    JLabel lblSynopsis;
    
    KeyListener keyListener;
    DocumentFilter lengthFilter;
    
    
    public NewMovieDialog(Window owner, SQLServer db) {
        super(owner,"Új film felvétele",Dialog.ModalityType.APPLICATION_MODAL);
        
        this.db = db;
        
        InitializeKeyListener();                
        
        InitializeDialog();
        
    }
    
    private void InitializeDialog() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(320,480));
        this.setResizable(false);
        
        txtTitle = new JTextField();
        txtOrigin = new JTextField();
        txtDirector = new JTextField();
        
        txtLength = new JTextField();
        txtLength.setMaximumSize(new Dimension(30, txtLength.getSize().height)); 
        txtLength.setColumns(3);
        
        // Filter beallitasa a film hossz mezohoz
        ((AbstractDocument)txtLength.getDocument()).setDocumentFilter(lengthFilter);
        
        txtSynopsis = new JTextArea();
        txtSynopsis.setRows(8);
        txtSynopsis.setEditable(true);
        txtSynopsis.setLineWrap(true);
        txtSynopsis.setWrapStyleWord(true);        
                
        cbDubbing = new JComboBox();
        cbDubbing.addItem("szinkronizált");
        cbDubbing.addItem("feliratos");
        
        componentContainer = new ArrayList<JComponent>();
        componentContainer.add(txtTitle);    // TextFields...
        componentContainer.add(txtOrigin);
        componentContainer.add(txtDirector);
        componentContainer.add(txtLength);        
        componentContainer.add(txtSynopsis); // TextArea        
        componentContainer.add(cbDubbing); // Ezt nem hasznaljuk ellenorzesnel, de
                                           // a teljesseg kedveert berakjuk, length-1 et hasznalunk!
        
        txtTitle.addKeyListener(keyListener);
        txtOrigin.addKeyListener(keyListener);
        txtDirector.addKeyListener(keyListener);
        txtLength.addKeyListener(keyListener);
        txtSynopsis.addKeyListener(keyListener);

        
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
                ResetFields();
                disableOK();
            }
        });
        
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                boolean formsFilled = false;
                
                for (int i = 0; i < componentContainer.size()-1; i++) {
                    formsFilled = !((JTextField)componentContainer.get(i)).getText().isEmpty();
                    System.out.println( ((JTextField)componentContainer.get(i)).getText());
                }
                
                if ( formsFilled )
                    enableOK();
                else
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
        
        Object[] data = new Object[6];
        
        data[0] = txtTitle.getText().trim();
        data[1] = txtOrigin.getText().trim();
        data[2] = cbDubbing.getSelectedIndex()+1; // Adatbazisban nem 0-tol indul
        data[3] = txtDirector.getText().trim();
        data[4] = Integer.parseInt(txtLength.getText().trim());
        data[5] = txtSynopsis.getText().trim();
        
        if ( this.db.AddNewMovie(data) ) {
            this.disableOK();
            return true;
        }
        
        return false;        
    }

    @Override
    protected void processCancel() {
        ResetFields();
    }

    private void ResetFields() {
        txtTitle.setText(null);
        txtOrigin.setText(null);
        cbDubbing.setSelectedIndex(0);
        txtDirector.setText(null);
        txtLength.setText(null);
        txtSynopsis.setText(null);
    }

    private void InitializeKeyListener() {
        keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                

            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                // Film hossz filterek
                if ( e.getSource().equals(txtLength) )
                {
                    if ( !isNumber(txtLength.getText()) ) {
                        txtLength.setText(null);
                        Toolkit.getDefaultToolkit().beep();
                    }
                    else if ( txtLength.getText().length() > 3 ) {
                        Toolkit.getDefaultToolkit().beep();
                        txtLength.setText(txtLength.getText().substring(0, 3));
                    }
                }                       
                
                boolean formsFilled = true;
                
                // Textfields
                for (int i = 0; i < componentContainer.size()-2; i++) {
                    
                    if ( ((JTextField)componentContainer.get(i)).getText().trim().isEmpty() )
                        formsFilled = false;
                }

                // TextArea
                if ( ((JTextArea)componentContainer.get(4)).getText().trim().isEmpty() )
                    formsFilled = false;
                
                if ( formsFilled )
                    enableOK();
                else
                    disableOK();
            }
        };
    }
    
    
    private boolean isNumber(String input)
    {
        try
        {
            Integer.parseInt( input );
            return true;
        }
        catch ( Exception ex)
        {
            return false;
        }
    }    


}
