package chargingScheduler;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import javax.swing.*;

public class SwingInterface {
	  private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;

	   public SwingInterface(){
	      prepareGUI();
	   }

	   public static void main(String[] args){
		   SwingInterface  swingControlDemo = new SwingInterface();      
	      swingControlDemo.showTextFieldDemo();
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("Car Agent Interface");
	      mainFrame.setSize(400,400);
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

	   private void showTextFieldDemo(){
	      headerLabel.setText("Car Charging Schedule"); 
	      
	      JSpinner startTimeSpinner = new JSpinner( new SpinnerDateModel() );
	      JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
	      startTimeSpinner.setEditor(timeEditor);
	      startTimeSpinner.setValue(new Date()); 
	      
	      
	      JSpinner endTimeSpinner = new JSpinner( new SpinnerDateModel() );
	      JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
	      endTimeSpinner.setEditor(endTimeEditor);
	      endTimeSpinner.setValue(new Date()); 
	      

	      JLabel  userName= new JLabel("User ID: ", JLabel.RIGHT);
	      JLabel  startTime = new JLabel("Start Time: ", JLabel.CENTER);
	      JLabel  endTime = new JLabel("End Time: ", JLabel.CENTER);
	      final JTextField userNameText = new JTextField(6);
	    

	      JButton loginButton = new JButton("Send Schedule");
	      
	      loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	            String data = "Username " + userNameText.getText();
	            data += ", " +" Start Time:"+ startTimeSpinner.getValue();  
	            data +=" , " + " Finish Time:" +endTimeSpinner.getValue();
	            
	            statusLabel.setText(data);        
	         }
	      }); 

	      controlPanel.add(userName);
	      controlPanel.add(userNameText);
	      
	      controlPanel.add(startTime);
	      controlPanel.add(startTimeSpinner);
	      
	      controlPanel.add(endTime);   
	      controlPanel.add(endTimeSpinner);
	      
	      controlPanel.add(loginButton);
	      mainFrame.setVisible(true);  
	   }
}

