package chargingScheduler;

 import jade.core.Agent; 
import jade.core.behaviours.*; 

public class startaco extends SimpleBehaviour 
 { 	 

	private boolean finished = false;  
	 
	public startaco( Agent a) { 
 	super(a); 
		 
	} 
	 
	public void action()  
 	{ 
		System.out.println(myAgent.getLocalName() + "Acting"); 
		block( 1000 ); 
 		finished = true; 
 	} 
	 
	public  boolean done() {  return finished;  } 
 
 
} 
 