/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class MovieBookingDialog extends javax.swing.JDialog {
    
    private JLabel lblRoomName;
    
    private JPanel informationPanel;
    private JLabel lblRoomInformation;
    private JLabel lblFree;
    private JLabel lblBooked;
    private JLabel lblSold;
    private JLabel lblSelectedSeat;
    private JLabel lblRow;
    private JLabel lblCol;
    private JButton btnBook;
    private JButton btnSell;
    
    private JPanel roomLayoutPanel;
    private JLabel lblMovieInformation;
    
    private ArrayList<SeatRowCounter> lblSeatRowCounterIcons;
    
    private ArrayList<Seat> lblSeatIcons;
    
    private int rows;
    private int columns;
    
    private SQLServer db;
    private int showID;
    
    private int freeSeats;
    private int bookedSeats;
    private int soldSeats;
    private String roomName;
    private String movieName;
    
    private ArrayList<int[]> seatData;
    
    private static final int STATUS_FREE = 0;
    private static final int STATUS_BOOKED = 1;
    private static final int STATUS_SOLD = 2;
    
    private MouseListener SeatSelectionHandler;
    
    private int selectedSeatIndex;
    
    

    /** Creates new form NewJFrame */
    public MovieBookingDialog(SQLServer db, ArrayList<Object> showData) {
        
        this.db = db;
        
        // nothing is selected
        selectedSeatIndex = -1;
        
        this.showID = (Integer)showData.get(0);
        this.roomName = (String)showData.get(2)+" Terem - "+(String)showData.get(1);
        this.movieName = (String)showData.get(3)+" - "+(Integer)showData.get(4)+" perc";
        this.bookedSeats = (Integer)showData.get(5);
        this.soldSeats = (Integer)showData.get(6);
        this.freeSeats = (Integer)showData.get(7);
        
        this.rows = (Integer)showData.get(8);
        this.columns = (Integer)showData.get(9);
        
        lblSeatRowCounterIcons = new ArrayList<SeatRowCounter>();
        lblSeatIcons = new ArrayList<Seat>();
        
        LoadData();
//        
//        for ( int i = 0; i < seatData.size(); i++ ) {
//            for ( int j = 0; j < seatData.get(i).length;j++)
//                System.out.print(seatData.get(i)[j] + " ");            
//            
//            System.out.println(" ");
//        }
        
        initMouseAdapter();
        initComponents();
        initDialog();        
    }
    
    private void initDialog() {
        this.setTitle("Jegyfoglalás - "+this.roomName);        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        
        // Dialog layout
        GroupLayout layout = new GroupLayout( getContentPane() );
        
        layout.setHorizontalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING )
            .addGroup( GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING )
                    .addGroup( GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent( informationPanel,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent( roomLayoutPanel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent( lblRoomName,  GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent( lblRoomName )
                .addGap( 18, 18, 18 )
                .addGroup( layout.createParallelGroup( GroupLayout.Alignment.LEADING )
                    .addComponent( informationPanel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                    .addComponent( roomLayoutPanel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ))
                .addContainerGap())
        );
        
        this.getContentPane().setLayout( layout );
        this.pack();
        
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        initRoomInformationLayout();       
        
        initRoomLayoutLayout();
        

    }

    private void initRoomInformationLayout() {
        
        // Header
        this.lblRoomName = new JLabel(this.roomName);
        this.lblRoomName.setFont(new Font("Tahoma", 1, 24));
        this.lblRoomName.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblRoomName.setHorizontalTextPosition(SwingConstants.CENTER);        
        
        // Bal oldali panel - Terem Informaciok
        this.informationPanel = new JPanel();
        this.informationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        this.lblRoomInformation = new JLabel("Terem információk:");
        this.lblRoomInformation.setFont(new Font("Tahoma", 1, 11));
        
        this.lblFree = new JLabel("Szabad: "+this.freeSeats);
        this.lblFree.setIcon(new SeatIcon(Seat.COLOR_FREE));
        
        this.lblBooked = new JLabel("Foglalt: "+this.bookedSeats);
        this.lblBooked.setIcon(new SeatIcon(Seat.COLOR_BOOKED));        
        
        this.lblSold = new JLabel("Eladva: "+this.soldSeats);
        this.lblSold.setIcon(new SeatIcon(Seat.COLOR_SOLD));
        
        this.lblSelectedSeat = new JLabel("Kiválasztott szék:");
        this.lblSelectedSeat.setFont(new Font("Tahoma", 1, 11)); 
        
        this.lblRow = new JLabel("Sor:");
        this.lblCol = new JLabel("Oszlop:");
        
        this.btnBook = new JButton("Foglal");
        this.btnSell = new JButton("Kiad");
        
        
        GroupLayout informationPanelLayout = new javax.swing.GroupLayout( informationPanel );
       
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
                
            .addGroup( informationPanelLayout.createSequentialGroup()
                .addGroup( informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup( informationPanelLayout.createSequentialGroup()
                        .addGap( 10, 10, 10 )
                        .addComponent( lblRoomInformation ))
                
                    .addGroup( informationPanelLayout.createSequentialGroup()
                        .addGap( 10, 10, 10 )
                        .addComponent( lblFree ))
                
                    .addGroup( informationPanelLayout.createSequentialGroup()
                        .addGap( 10, 10, 10 )
                        .addComponent( lblBooked ))
                
                    .addGroup( informationPanelLayout.createSequentialGroup()
                        .addGap( 10, 10, 10 )
                        .addComponent( lblSold ))
                )                
                .addContainerGap(58, Short.MAX_VALUE)
            )
                
            .addGroup( informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent( btnBook, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( btnSell, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                //.addComponent( btnSell )
                .addContainerGap())
                
            .addGroup( informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent( lblSelectedSeat )
                .addContainerGap( 63, Short.MAX_VALUE ))
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent( lblRow )
                .addContainerGap( 134, Short.MAX_VALUE ))
            .addGroup( informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent( lblCol )
                .addContainerGap( 112, Short.MAX_VALUE ))
        );
        
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
            .addGroup( informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent( lblRoomInformation )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( lblFree )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( lblBooked )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( lblSold )
                .addGap(44, 44, 44)
                .addComponent( lblSelectedSeat )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( lblRow )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
                .addComponent( lblCol )
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE )
                .addGroup( informationPanelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                    .addComponent( btnBook )
                    .addComponent( btnSell ))
                .addContainerGap())
        );
        
        informationPanel.setLayout( informationPanelLayout );        
    }
    
    

    private void initRoomLayoutLayout() {
        // Jobb oldali panel - Terem alaprajz
        this.roomLayoutPanel = new JPanel();
        this.roomLayoutPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        SeatIcon  icoSeat = null;
        
        for ( int row = 0;  row < this.rows; row++ ) {
            // Counter
            lblSeatRowCounterIcons.add(new SeatRowCounter(String.valueOf(row+1)));
            
            // Seats
            for ( int col = 0; col < this.columns; col++ ) {
                
                // CHECK SEAT STATUS getSeatStatus(row,col)
                // set icon based on that
                //icoSeat = new SeatIcon(COLOR_FREE);
                lblSeatIcons.add( new Seat(row, col, GetSeatStatus(row, col),SeatSelectionHandler) );
            }
        }
        
//        (lblSeatIcons.get(5)).setSelected(true);
//        (lblSeatIcons.get(5)).setSelected(false);
        
        lblMovieInformation = new JLabel(this.movieName);
        lblMovieInformation.setFont(new Font("Tahoma",1,12));
        lblMovieInformation.setHorizontalAlignment(SwingConstants.CENTER);

        
        GroupLayout roomLayoutPanelLayout = new GroupLayout(roomLayoutPanel);
        roomLayoutPanel.setLayout(roomLayoutPanelLayout);
        
        // HORIZONTAL
        // Sorszamlalo felepitese
        GroupLayout.ParallelGroup pgHRowCounterGroup = roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        for ( SeatLabel seat : lblSeatRowCounterIcons )
            pgHRowCounterGroup.addComponent(seat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        
        // Sorok felepitese
        GroupLayout.ParallelGroup pgHSeatGroup = roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        for ( int row = 0; row < lblSeatRowCounterIcons.size(); row++ )
        {
            GroupLayout.SequentialGroup sgHRowGroup = roomLayoutPanelLayout.createSequentialGroup();
            
            for ( int col = 0; col < this.columns; col++) {
                
                sgHRowGroup.addComponent(lblSeatIcons.get(row*(this.columns)+col), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                sgHRowGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            
            pgHSeatGroup.addGroup(sgHRowGroup);
        }
        
        roomLayoutPanelLayout.setHorizontalGroup(
            roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                
            .addGroup(roomLayoutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    // Terem informacio header
                    .addComponent(lblMovieInformation, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                    .addGroup(roomLayoutPanelLayout.createSequentialGroup()
                        .addGroup(pgHRowCounterGroup)
                        // Sorszam utani szunet
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, 18 )
                        // Sorok
                        .addGroup(pgHSeatGroup)))
                .addContainerGap())
        );
        
        
        // VERTICAL

        // Sorok Felepitese
        GroupLayout.SequentialGroup sgVSeatGroup = roomLayoutPanelLayout.createSequentialGroup();
        for ( int row = 0; row < lblSeatRowCounterIcons.size(); row++ )
        {
            GroupLayout.ParallelGroup pgVRowGroup = roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            
            for ( int col = 0; col < this.columns; col++) {
                pgVRowGroup.addComponent(lblSeatIcons.get(row*(this.columns)+col), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            }
            
            sgVSeatGroup.addGroup(pgVRowGroup);
            sgVSeatGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }  
        
        // Sorszamlalo felepitese
        GroupLayout.SequentialGroup sgVRowCounterGroup = roomLayoutPanelLayout.createSequentialGroup();
        for ( SeatLabel seat : lblSeatRowCounterIcons ) {
            sgVRowCounterGroup.addComponent(seat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);        
            sgVRowCounterGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }
        
        roomLayoutPanelLayout.setVerticalGroup(
            roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(roomLayoutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMovieInformation)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(sgVSeatGroup)
                    .addGroup(sgVRowCounterGroup))
//                .addContainerGap(258, Short.MAX_VALUE)
            )
        );        
    }
    
    private int GetSeatStatus(int row, int col) {
        
        // Ha mar szerepel az adatbazisban,akkor megkeressuk a 
        // statuszat
        for ( int[] seat_status : seatData ) {
            
            if ( seat_status[3] == row && seat_status[4] == col )
                return seat_status[5];
        }
        
        // Ha nem szerepel, akkor szabad
        return STATUS_FREE;        
    }
    
    private void LoadData() {
        
        if ( seatData != null)
            seatData.clear();
        
        seatData = db.GetShowSeatStates(this.showID);
    }

    private void initMouseAdapter() {
        SeatSelectionHandler = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                Seat seat = (Seat)e.getSource();
                
                if ( seat.isSelected() )
                    return;
                
//                System.out.println("R: "+seat.getRowPosition()+" C: "+seat.getColPosition());

//                for ( Seat s : lblSeatIcons )
//                    s.setSelected(false);

                // Az elozo kivalasztast megszuntetjuk
                if ( selectedSeatIndex >= 0)
                    lblSeatIcons.get(selectedSeatIndex).setSelected(false);
                
                // Kivalasztjuk az uj szeket
                seat.setSelected(true);
                selectedSeatIndex = (seat.getRowPosition() * columns)+seat.getColPosition();
                
                lblRow.setText("Sor: "+(seat.getRowPosition()+1));
                lblCol.setText("Oszlop: "+(seat.getColPosition()+1));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
