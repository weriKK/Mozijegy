/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class NewShowDialog extends OKCancelDialog {
    
    SQLServer db;
    
    JComboBox cbMovies;
    JComboBox cbRooms;
    
    JComboBox cbYear;
    JComboBox cbMonth;
    JComboBox cbDay;
    
    JComboBox cbHour;
    JComboBox cbMinute;
    
    JLabel lblMovies;
    JLabel lblRooms;
    JLabel lblDate;
    
    
    ItemListener itemListener;
    
    Calendar calendar;
    
    HashMap<String, Integer> movieData;
    HashMap<String, Integer> movieLengthData;
    HashMap<String, Integer> roomData;
    
    public NewShowDialog(Window owner, SQLServer db) {
        super(owner,"Új előadás meghirdetése",Dialog.ModalityType.APPLICATION_MODAL);
        
        this.db = db;
        
        calendar = new GregorianCalendar();
        
        movieData = new HashMap<String,Integer>();
        movieLengthData = new HashMap<String,Integer>();
        roomData = new HashMap<String,Integer>();
        
        InitializeItemListener();
        
        InitializeDialog();
        
        
    }
    
    private void InitializeDialog() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //this.setPreferredSize(new Dimension(320,480));
        this.setResizable(false);
        
        int year = calendar.get(Calendar.YEAR);                
        int month = calendar.get(Calendar.MONTH);
        
        cbMovies = new JComboBox(GetMovieNames());
        cbRooms = new JComboBox(GetRoomNames());
        cbYear = new JComboBox(GetYearList(year));
        cbMonth = new JComboBox(GetMonthList());
        cbDay = new JComboBox(GetDayList(year,month));
        cbHour = new JComboBox(GetHourList());
        cbMinute = new JComboBox(GetMinuteList());
        
        // Aktualis ido beallitasa a comboboxokra
        ResetFields();
        
        lblMovies = new JLabel("Film:");
        lblRooms = new JLabel("Terem:");
        lblDate = new JLabel("Film kezdésének ideje:");

        
//        txtTitle.addKeyListener(keyListener);
//        txtOrigin.addKeyListener(keyListener);
//        txtDirector.addKeyListener(keyListener);
//        txtLength.addKeyListener(keyListener);
//        txtSynopsis.addKeyListener(keyListener);
        
        cbYear.addItemListener(itemListener);
        cbMonth.addItemListener(itemListener);         
        
        GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);
        
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblMovies)
                    .addComponent(cbMovies)
                    .addComponent(lblRooms)
                    .addComponent(cbRooms)
                    .addComponent(lblDate)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbYear)
                        .addComponent(cbMonth)
                        .addComponent(cbDay)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbHour)
                        .addComponent(cbMinute)
                    )
                    .addComponent(buttonPanel)
        );
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(lblMovies)
                    .addComponent(cbMovies)
                    .addComponent(lblRooms)
                    .addComponent(cbRooms)
                    .addComponent(lblDate)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cbYear)
                        .addComponent(cbMonth)
                        .addComponent(cbDay)

                        .addComponent(cbHour)
                        .addComponent(cbMinute)
                    )
                    .addComponent(buttonPanel)                
        );
                
      
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                ResetFields();
            }
        });
        
        this.pack();    
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);
    }    

    @Override
    protected boolean processOK() {
        
//        System.out.println(cbMovies.getSelectedItem()+" id: "+ movieData.get(cbMovies.getSelectedItem()));
//        System.out.println(cbRooms.getSelectedItem()+" id: "+ roomData.get(cbRooms.getSelectedItem()));        

        java.util.Date date = new java.util.Date(
                (Integer)cbYear.getSelectedItem()-1900,
                (Integer)cbMonth.getSelectedItem()-1,
                (Integer)cbDay.getSelectedItem(),
                (Integer)cbHour.getSelectedItem(),
                (Integer)cbMinute.getSelectedItem()
                );
        
        java.text.SimpleDateFormat sqlDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String sqlDate = sqlDateFormat.format(date);
        
        
        Object[] data = new Object[3];
        
        data[0] = movieData.get(cbMovies.getSelectedItem().toString());
        data[1] = sqlDate;
        data[2] = roomData.get(cbRooms.getSelectedItem().toString());

        System.out.println(db.GetShowsRunningAtDate(sqlDate, movieLengthData.get(cbMovies.getSelectedItem().toString()), 5));
        
//        if ( this.db.AddNewShow(data) ) {
//            return true;
//        }
        
        return false;        
    }

    @Override
    protected void processCancel() {
        ResetFields();
    }

    private void ResetFields() {
        calendar = new GregorianCalendar();
        
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        cbMovies.setSelectedIndex(0);
        cbRooms.setSelectedIndex(0);
        cbYear.setSelectedIndex(100);
        cbMonth.setSelectedIndex(month);        
        cbDay.setSelectedIndex(day-1);
        cbHour.setSelectedIndex(hour);
    }
    
    private void UpdateFields() {
        int year = (Integer)cbYear.getSelectedItem();
        int month = (Integer)cbMonth.getSelectedItem()-1;
        
        cbDay.removeAllItems();
        
        for ( Object o : GetDayList(year,month) )
            cbDay.addItem(o);
    }
    
    public Object[] GetMovieNames() {
        
        ArrayList<Object[]> dbData = db.GetMovieInformation();
        Object[] data = new Object[dbData.size()];

        int i = 0;
        
        for ( Object[] o : dbData ) {
            movieData.put(o[0].toString(),(Integer)o[5]);
            movieLengthData.put(o[0].toString(),(Integer)o[4]);
            data[i] = o[0].toString();
            i++;
        }
        
        Arrays.sort(data);
        
        return data;
    }
    
    public Object[] GetRoomNames() {
        
        ArrayList<Object[]> dbData = db.GetRoomNames();
        Object[] data = new Object[dbData.size()];

        int i = 0;
        
        for ( Object[] o : dbData ) {
            roomData.put(o[0].toString(),(Integer)o[1]);
            data[i] = o[0].toString();
            i++;
        }
        
        Arrays.sort(data);
        
        return data;
    }
    
    public Object[] GetYearList(int year) {
        
        Object[] data = new Object[201];
        
        for ( int i = 0; i < 100; i++)
            data[i] = year-(100-i);
        
        data[100] = year;
        
        for ( int i = 101; i <= 200; i++)
            data[i] = year+(i-100);        
        
        return data;
    }
    
    public Object[] GetMonthList() {
        
        Object[] data = new Object[12];
        
        for ( int i = 1; i < 13; i++)
            data[i-1] = i;
       
        return data;
    }    
    
    public Object[] GetDayList(int year, int month) {
        
        
//        System.out.println("YEAR: "+year);
//        System.out.println("MONTH: "+month);
                
        Calendar cal = new GregorianCalendar(year,month,1);
        
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);        
        
//        System.out.println("DAYS: "+days);
//        System.out.println(cal.getTime().toString());
        
        Object[] data = new Object[days];
        
        for ( int i = 1; i <= days; i++ )
            data[i-1] = i;
       
        return data;
    }    
    
    public Object[] GetHourList() {
        
        Object[] data = new Object[24];
        
        for (int i = 0; i < 24; i++)
            data[i] = i;
       
        return data;
    } 
    
    public Object[] GetMinuteList() {
        
        Object[] data = new Object[4];
        
        data[0] = 0;
        data[1] = 15;
        data[2] = 30;
        data[3] = 45;
       
        return data;
    }

    private void InitializeItemListener() {
        
        itemListener = new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if ( e.getSource().equals(cbYear) || e.getSource().equals(cbMonth) )
                    UpdateFields();
            }
        };       
    }

}
