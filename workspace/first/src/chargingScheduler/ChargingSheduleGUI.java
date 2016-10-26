package chargingScheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChargingSheduleGUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table = null;
    private DefaultTableModel tableModel = null;
    private JTextField txtField1 = null;
    private JTextField txtField2 = null;
    private DefaultTableModel model;
  //  private String solutionString;
    private String carNum = null;
    public ArrayList errorStack = CarAgent.errorStack; 

    ChargingSheduleGUI(String solutionString) {
        createGUI(solutionString);
        //this.solutionString = solutionString;
    }

    public void createGUI(String solutionString) {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        
        //car mapping 
        table = new JTable(new DefaultTableModel(new Object[]{"CAR NUMBER"},0));
        model = (DefaultTableModel) table.getModel();
        
        System.out.println("----- ++++++++++++++++++++++ rows exist----" + model.getRowCount());
        
        if(model.getRowCount() > 0){
        	for(int i=0; i < model.getRowCount();i++){
        		model.removeRow(i);
        		System.out.println("----- removing rows exist----"+i);
        	}
        }
       
        //table.setBackground(bg);
        table.setRowHeight(30);
        model.addRow(new Object[]{errorStack});
        
        String[] spot =  solutionString.split(" ");
    	List<String> carList = CarAgent.carRegList;
    	//carNum = null;
    	
    	for(int i=0;i<spot.length;i++){
    		carNum = (i+1) + "  >>>>>  ";
			for (int j = 0; j < carList.size(); j++) {
			
				if ( j+1 == Integer.parseInt(spot[i])) {
					carNum += carList.get(j).toUpperCase();
					model.addRow(new Object[]{carNum});
					System.out.println("------------> "+carNum);
					
				}else{
					//carNum += "Empty";
					model.addRow(new Object[]{carNum});
					System.out.println("------------> "+carNum);
				}
			}
    	}
        
        pane.setViewportView(table);
        JPanel eastPanel = new JPanel();
        
        JPanel northPanel = new JPanel();
        txtField1 = new JTextField();
        txtField2 = new JTextField();
       
        JLabel lblField1 = new JLabel("Car Charging Schedule");
        
        add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(pane,BorderLayout.CENTER);
        
        this.setLocationByPlatform(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
   
 /*   public void showSchedule() {
        SwingUtilities.invokeLater(new Runnable() {
       
            @Override
            public void run() {
                ChargingSheduleGUI frm = new ChargingSheduleGUI(solutionString);
                frm.setLocationByPlatform(true);
                frm.pack();
                frm.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frm.setVisible(true);

            }

        });
    }*/
} 