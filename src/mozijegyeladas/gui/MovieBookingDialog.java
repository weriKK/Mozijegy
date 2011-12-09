/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

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
    
    private ArrayList<SeatLabel> lblSeatRowCounterIcons;
    
    private ArrayList<SeatLabel> lblSeatIcons;
    
    private int rows;
    private int columns;
    
    private static final Color COLOR_FREE = new Color(200,255,200);
    private static final Color COLOR_BOOKED = new Color(255,200,200);
    private static final Color COLOR_SOLD = Color.RED;
    

    /** Creates new form NewJFrame */
    public MovieBookingDialog(int rows, int columns) {
        
        this.rows = rows;
        this.columns = columns;
        
        lblSeatRowCounterIcons = new ArrayList<SeatLabel>();
        lblSeatIcons = new ArrayList<SeatLabel>();
        
        initComponents();
        initDialog();        
    }
    
    private void initDialog() {
        this.setTitle("NOT_IMPLEMENTED");        
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
        this.lblRoomName = new JLabel("NOT_IMPLEMENTED");
        this.lblRoomName.setFont(new Font("Tahoma", 1, 24));
        this.lblRoomName.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblRoomName.setHorizontalTextPosition(SwingConstants.CENTER);        
        
        // Bal oldali panel - Terem Informaciok
        this.informationPanel = new JPanel();
        this.informationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        this.lblRoomInformation = new JLabel("Terem információk:");
        this.lblRoomInformation.setFont(new Font("Tahoma", 1, 11));
        
        this.lblFree = new JLabel("Szabad: NA");
        this.lblFree.setIcon(new SeatIcon(COLOR_FREE));
        
        this.lblBooked = new JLabel("Foglalt: NA");
        this.lblBooked.setIcon(new SeatIcon(COLOR_BOOKED));        
        
        this.lblSold = new JLabel("Eladva: NA");
        this.lblSold.setIcon(new SeatIcon(COLOR_SOLD));
        
        this.lblSelectedSeat = new JLabel("Kiválasztott szék:");
        this.lblSelectedSeat.setFont(new Font("Tahoma", 1, 11)); 
        
        this.lblRow = new JLabel("Sor: NA");
        this.lblCol = new JLabel("Oszlop: NA");
        
        this.btnBook = new JButton("NA-B");
        this.btnSell = new JButton("NA-S");
        
        
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
            lblSeatRowCounterIcons.add(new SeatLabel(String.valueOf(row+1), false,null));
            
            // Seats
            for ( int col = 0; col < this.columns; col++ ) {
                
                // CHECK SEAT STATUS getSeatStatus(row,col)
                // set icon based on that
                icoSeat = new SeatIcon(COLOR_FREE);
                lblSeatIcons.add( new SeatLabel(null, true, icoSeat) );
            }
        }
        
        lblMovieInformation = new JLabel("NA-3");
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
        GroupLayout.ParallelGroup pgHSeatGroup = roomLayoutPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
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
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
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
}
