package test;

 import jade.core.Agent;
 import jade.core.behaviours.*;
     
 public class Test extends Agent 
 {       
     protected void setup() 
     {
         addBehaviour( new myBehaviour ( this ));
     }   //  --- setup ---
     
     class myBehaviour extends SimpleBehaviour
     {
    	 public myBehaviour (Agent a)
    	 {
    		 super(a);
    	 }
    	 
    	 public void action()
    	 {
    		 System.out.println( "Hello World! My name is " + 
                     myAgent.getLocalName() );
    	 }
    	 
    	 private boolean finished = false;
    	 public boolean done()
    	 {
    		 return finished;
    	 }
    	 
     }
     
 }   //  --- class Test