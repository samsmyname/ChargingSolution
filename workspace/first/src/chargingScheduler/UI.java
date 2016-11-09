package chargingScheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

public class UI extends JFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Car Charging Scheduler");
	private final JLabel lblCarRegNum = new JLabel("Car Reg");
	private final Action action = new SwingAction();
	private static JDesktopPane desktopPane;
	private final JButton addCarBtn = new JButton("Add Request");
	private final JButton useACOBtn = new JButton("Use ACO");
	private final JButton useGABtn = new JButton("Use GA");
	private final JButton testBtn = new JButton("Test");
	private JTextField lblGA = new JTextField();
	private JTextField lblACOph = new JTextField();
	private JTextField lblACOpr = new JTextField();
	private JTextField lblACOev = new JTextField();
	private JTextField carRegNumLbl;
	private JLabel lblChargeCurrent;
	private JLabel label;
	private JTextField chargeCurrent;
	private JTextField chargeMax;
	private JSpinner startTimeSpinner;
	private JSpinner endTimeSpinner;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private int i=0;
	
	private JTable requestTable = new JTable();
	private DefaultTableModel tableModel;
    private DefaultTableModel reModel;
	private JLabel requests;
	
	private static JTable sheduleTable = new JTable();
	private static DefaultTableModel scheduleTableModel;
    private static String carNum = null;
    public static List<String> errorStack = new ArrayList<String>(); 

	private JLabel carSchedules;
	SystemStart ss;
	private final static JLabel lblErr = new JLabel("");
	//private final JToggleButton tglBtn = new JToggleButton("Genetic Alg.");
	//private final JLabel lblClickToChange = new JLabel("Click to change the Algorithm");
	private JLabel statusTxt;
	
	
	/**
	 * Launch the application.
	 */
	
	public UI(SystemStart ss){
		   this.ss = ss;
		   EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UI frame = new UI();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		   //prepareGUI();
	   }

	

	/**
	 * Create the frame.
	 */
	public UI() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 773);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Lantinghei TC", Font.PLAIN, 16));
		
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.BLUE);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		addCarBtn.setBounds(16, 265, 108, 29);
		useACOBtn.setBounds(16, 325, 108, 29);
		useGABtn.setBounds(16, 425, 108, 29);
		testBtn.setBounds(16, 565, 108, 29);
		//lblGA.setBounds(156,365, 50, 29);
		lblACOph.setBounds(16, 360, 50, 29);
		lblACOpr.setBounds(66, 360, 50, 29);
		lblACOev.setBounds(116, 360, 50, 29);
		
		desktopPane.add(addCarBtn);
		desktopPane.add(useACOBtn);
		desktopPane.add(useGABtn);
		desktopPane.add(testBtn);
		//desktopPane.add(lblGA);
		desktopPane.add(lblACOph);
		desktopPane.add(lblACOpr);
		desktopPane.add(lblACOev);
		
		lblACOph.setText("100");
		lblACOpr.setText("0.75");
		lblACOev.setText("0.5");
		
		
		
		carRegNumLbl = new JTextField();
		carRegNumLbl.setBounds(121, 66, 101, 26);
		desktopPane.add(carRegNumLbl);
		carRegNumLbl.setColumns(10);
		lblCarRegNum.setBounds(16, 67, 97, 23);
		desktopPane.add(lblCarRegNum);
		
		lblCarRegNum.setForeground(Color.WHITE);
		lblCarRegNum.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		
		lblChargeCurrent = new JLabel("Current Charge");
		lblChargeCurrent.setForeground(Color.WHITE);
		lblChargeCurrent.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		lblChargeCurrent.setBounds(16, 104, 97, 23);
		desktopPane.add(lblChargeCurrent);
		
		label = new JLabel("Max Charge");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		label.setBounds(16, 136, 97, 23);
		desktopPane.add(label);
		
		chargeCurrent = new JTextField();
		chargeCurrent.setColumns(10);
		chargeCurrent.setBounds(121, 101, 101, 26);
		desktopPane.add(chargeCurrent);
		
		chargeMax = new JTextField();
		chargeMax.setColumns(10);
		chargeMax.setBounds(121, 133, 101, 26);
		desktopPane.add(chargeMax);
		
		//startTimeSpinner = new JSpinner();
		
		
		JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(timeEditor);
		startTimeSpinner.setValue(new Date());
		startTimeSpinner.setBounds(121, 171, 70, 26);
		desktopPane.add(startTimeSpinner);
		
		
		JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor);
		endTimeSpinner.setValue(new Date());
		endTimeSpinner.setBounds(121, 206, 70, 26);
		desktopPane.add(endTimeSpinner);

		lblStartTime = new JLabel("Drop-off Hr");
		lblStartTime.setForeground(Color.WHITE);
		lblStartTime.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		lblStartTime.setBounds(16, 174, 70, 23);
		desktopPane.add(lblStartTime);
		
		lblEndTime = new JLabel("Pick-up Hr");
		lblEndTime.setForeground(Color.WHITE);
		lblEndTime.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		lblEndTime.setBounds(16, 209, 70, 23);
		desktopPane.add(lblEndTime);
		
		//Request Table
		requestTable = new JTable(new DefaultTableModel(new Object[]{"Car Reg"},0));
		reModel = (DefaultTableModel) requestTable.getModel();
		requestTable.setBounds(280, 37, 308, 160);
		reModel.addColumn("Start Time");
        reModel.addColumn("End Time");
        reModel.addColumn("Hours Needed");
		
		JScrollPane scrReq = new JScrollPane(requestTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scrReq.setBounds(280, 85, 407, 160);
		desktopPane.add(scrReq);
		
		
		//schedule table 
		sheduleTable = new JTable(new DefaultTableModel(new Object[]{"CAR NUMBER"},0));
		scheduleTableModel = (DefaultTableModel) sheduleTable.getModel();
		scheduleTableModel.addRow(new Object[]{"Schedules"});
		//scroll
		JScrollPane scr = new JScrollPane(sheduleTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		sheduleTable.setBounds(278, 242, 310, 460);
		
		//desktopPane.add(sheduleTable);
		scr.setBounds(278, 290, 409, 422);
		desktopPane.add(scr);
		
		requests = new JLabel("Requests");
		requests.setForeground(Color.WHITE);
		requests.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		requests.setBounds(278, 54, 97, 23);
		desktopPane.add(requests);
		
		
		carSchedules = new JLabel("Car Schedules");
		carSchedules.setForeground(Color.WHITE);
		carSchedules.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		carSchedules.setBounds(280, 257, 97, 23);
		desktopPane.add(carSchedules);
		lblErr.setForeground(Color.WHITE);
		lblErr.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblErr.setBounds(6, 6, 582, 36);
		desktopPane.add(lblErr);
		//tglBtn.setBounds(16, 396, 108, 29);
		
		//desktopPane.add(tglBtn);
		//lblClickToChange.setForeground(Color.WHITE);
		//lblClickToChange.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		//lblClickToChange.setBounds(16, 361, 206, 23);
		
		//desktopPane.add(lblClickToChange);
		
		statusTxt = new JLabel("");
		statusTxt.setForeground(Color.WHITE);
		statusTxt.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		statusTxt.setBounds(16, 469, 206, 23);
		desktopPane.add(statusTxt);
		
		/*
		tglBtn.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
				  if(tglBtn.getText()=="Genetic Alg."){
					  MasterScheduler.isGeneticAlg = false;
			          tglBtn.setText("ACO Alg.");
			          System.out.println("Enable isGeneticAlg  -----> "+ MasterScheduler.isGeneticAlg);
				  }else{
					  MasterScheduler.isGeneticAlg = true;
			          tglBtn.setText("Genetic Alg.");
			          System.out.println("Enable ACO  -----> "+ MasterScheduler.isGeneticAlg);
				  }
		         }
		      }); 
		*/
		useACOBtn.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
				  SystemStart.MS.antColonyAlgorithm(lblACOph.getText(), lblACOpr.getText(), lblACOev.getText());
		         }
		      }); 
		
		useGABtn.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
				  SystemStart.MS.geneticAlgorithm();
		         }
		      }); 
		
		testBtn.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
				  Fitness.test();
		         }
		      }); 
		
		addCarBtn.addActionListener(new ActionListener() {
			
	         public void actionPerformed(ActionEvent e) {   
	        	String startTime = new SimpleDateFormat("HH:mm").format(startTimeSpinner.getValue());
	        	String endTime = new SimpleDateFormat("HH:mm").format(endTimeSpinner.getValue());
	            String data = "Car Id:" + carRegNumLbl.getText();
	            data += "\r\n " +" Cur Charge:"+ chargeCurrent.getText();
	            data += "\r\n " +" Max Charge:"+ chargeMax.getText();
	            data += "\r\n" +" Start Time:"+ startTime;//startTimeSpinner.getValue();  
	            data +="\r\n" + " End Time:" +endTime;//endTimeSpinner.getValue();
	            statusTxt.setText(data);    
	            
	            int cC = Integer.valueOf(chargeCurrent.getText());
	            int cM = Integer.valueOf(chargeMax.getText());
	            double hoursNeeded =( cM - cC );
	            hoursNeeded = Math.ceil(hoursNeeded / 100);
	            
	            //Construct a CarAgent after pressing Send Button
	            CarAgent carAgent = new CarAgent(carRegNumLbl.getText(), startTime, endTime, chargeCurrent.getText(), chargeMax.getText());
	            carAgent.StartCarAgent();
	            
	            System.out.println(carRegNumLbl.getText() +"----"+startTime + " : "+endTime);
	           
	            reModel.addRow(new Object[]{carRegNumLbl.getText()});
	            
	            reModel.setValueAt(startTime, i, 1);
	            reModel.setValueAt(endTime, i, 2);
	            reModel.setValueAt((int)hoursNeeded, i, 3);
	            
	            
	            
	            i++;
	         }
	      }); 
	}
	
	public static void removeRows(){
        while(scheduleTableModel.getRowCount()>0){
        	for(int j=0;j<scheduleTableModel.getRowCount();j++){
        		scheduleTableModel.removeRow(j);
        	}
        }
     
	}
	
	public static void displayErr(){
		errorStack = CarAgent.errorStack;
        
        String strErr = "";
        for(int j=0;j<errorStack.size();j++){
        	strErr +=" "+ errorStack.get(j);
        	System.out.println("strErr: "+ strErr);
    	}
        
        lblErr.setText(strErr);
       
	}
	
	public static void displaySchedules(String solutionString){
		removeRows();
		//System.out.println(scheduleTableModel.getRowCount()+ "ADDING ********** rowCount: "+scheduleTableModel.getRowCount());
		sheduleTable.setRowHeight(30);
	        
	        String[] spot = null;
	        List<String> carList = null;
	        spot =  solutionString.split(" ");
	    	carList = CarAgent.carRegList;
	    	
	    	//car mapping 
	    	for(int i=0;i<spot.length;i++){
	    		
	    		if((i)<=9 ){
	    			carNum = "0"+(i)+":00" + "  >>>>>  ";
	    		}else if((i)>9){
	    			carNum = (i)+":00"+ "  >>>>>  ";
	    		}else{
	    			carNum = (i) + "  >>>>>  ";
	    		}
	    		
	    		for (int j = 0; j < carList.size(); j++) {
					//System.out.println(i);
					if ( (j+1) == Integer.parseInt(spot[i])) {
						carNum += carList.get(j).toUpperCase();
						//scheduleTableModel.addRow(new Object[]{carNum});
						//System.out.println("------------> "+carNum);
						
					}else{
						//carNum += "----";
						//scheduleTableModel.addRow(new Object[]{carNum});
						//System.out.println("------------> "+carNum);
					}
				}
	    		
	    		//System.out.println("isGeneticAlg: "+MasterScheduler.isGeneticAlg+"------------> "+i);
	    		scheduleTableModel.addRow(new Object[]{carNum});
	    		
	    	}
	}

	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
