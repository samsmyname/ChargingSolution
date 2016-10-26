package chargingScheduler;


import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

/**
 * @author AysHasi
 * @SwingInterface 
 * 		Creates the Swing interface to obtain the Car Schedule details from user
 * 		Construct a CarAgent object and pass user input : CarRegistrationNum, StartTime(HH:mm), EndTime(HH:mm)
 * 			
 */
public class CarAgentGui {
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   
	   SystemStart ss;

	   public CarAgentGui(SystemStart ss){
		   this.ss = ss;
		   prepareGUI();
	   }

//	   public static void main(String[] args){
//		   SwingInterface  swingControlDemo = new SwingInterface();
//	       swingControlDemo.showTextFieldDemo();
//	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("Car Agent Interface");
	      mainFrame.setSize(600,300);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	      headerLabel = new JLabel("", JLabel.CENTER);        
	      statusLabel = new JLabel("",JLabel.CENTER);    

	      statusLabel.setSize(350,100);

	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	   }

	   public void showTextFieldDemo(){
	      headerLabel.setText("Car Charging Schedule"); 
	      
	      JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
	      JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
	      startTimeSpinner.setEditor(timeEditor);
	      startTimeSpinner.setValue(new Date()); 
	      
	      
	      JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
	      JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
	      endTimeSpinner.setEditor(endTimeEditor);
	      endTimeSpinner.setValue(new Date()); 
	      

	      JLabel  carRegNum= new JLabel("<html>Car Reg. Number:</html>", JLabel.RIGHT);
	      JLabel  startTime = new JLabel("<html>Start Time:</html>", JLabel.CENTER);
	      JLabel  endTime = new JLabel("<html>End Time:</html>", JLabel.CENTER);
	      final JTextField carRegNumLbl = new JTextField(6);
	    

	      JButton loginButton = new JButton("Send Schedule");
	      
	      loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {   
	        	String startTime = new SimpleDateFormat("HH:mm").format(startTimeSpinner.getValue());
	        	String endTime = new SimpleDateFormat("HH:mm").format(endTimeSpinner.getValue());
	            String data = "Car Id:" + carRegNumLbl.getText();
	            data += ", " +" Start Time:"+ startTime;//startTimeSpinner.getValue();  
	            data +=" , " + " End Time:" +endTime;//endTimeSpinner.getValue();
	            statusLabel.setText(data);    
	            
	            //Construct a CarAgent after pressing Send Button
	            CarAgent carAgent = new CarAgent(carRegNumLbl.getText(), startTime, endTime);
	            carAgent.StartCarAgent();
					
	         }
	      }); 

	      controlPanel.add(carRegNum);
	      controlPanel.add(carRegNumLbl);
	      
	      controlPanel.add(startTime);
	      controlPanel.add(startTimeSpinner);
	      
	      controlPanel.add(endTime);   
	      controlPanel.add(endTimeSpinner);
	      
	      controlPanel.add(loginButton);
	      mainFrame.setVisible(true);  
	   }
}

