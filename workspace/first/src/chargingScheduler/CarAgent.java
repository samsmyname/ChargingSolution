package chargingScheduler;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


/**
 * @CarAgent 
 * 	- Creates CarAgent from GUI
 *  - Send the requirements and preferences to the master scheduling agent
 *  - Receives the individual schedule
 */

public class CarAgent extends Agent{
	// FIXME Is CarAgent gets the requirements from the user?
	private int carId = 0;
	private String carRegNum;
	private String startTime;
	private String endTime;
	public String chargeCurrent;
	public String chargeMax;
	
	private int carLoad = 20;
	private TickerBehaviour counter;
	public static ArrayList carRegList=new ArrayList<String>() ;
	public static ArrayList errorStack = new ArrayList<String>();
	//List<Car> cars = new ArrayList<Car>();


	
	public CarAgent() {
	}

	/**
	 * @CarAgent 
	 * 	- Construct CarAgent with user preference
	 */
	public CarAgent(String carRegNum, String startTime, String endTime) {
		this.carRegNum = carRegNum;
		this.setStartTime(startTime);
		this.endTime = endTime;
		carRegList.add(carRegNum);
	}

	/**
	 * @StartCarAgent 
	 * 	- Constrct a CarAgent and start the agent 
	 * 	- CarAgent will then pass to setup()
	 *  - passParam will be passing user preference to the Car Agent
	 * 	
	 * @param carReg : Car Registration #
	 * @param startTime : Car drop in Time
	 * @param endTime : Car pick up time
	 */
	public void StartCarAgent() {
		AgentController agentCtrlc = null;

		Object[] passParam = new Object[3];
		passParam[0] = carRegNum;
		passParam[1] = startTime;
		passParam[2] = endTime;

		try {
			// Create and start an agent of class CarAgent
			System.out.println(">>>>>>>>>>>>>>> Starting up a CarAgen : "+ carRegNum +" Start: "+startTime + " End:"+endTime+CarAgent.class.getName());
			agentCtrlc = SystemStart.mainCtrl.createNewAgent("CarAgent @Rego "+ carRegNum, CarAgent.class.getName(), passParam);
			agentCtrlc.start();
		} catch (StaleProxyException e) {
			String err = "Error: "+ e.getMessage();
			errorStack.add(err);
			UI.displayErr();
			System.out.println("******* Error occured while starting up the agent ******* "+ e);
		}
	}
	
	/* 
	 *	@setup()
	 * 	- Construct a ACLMessage
	 * 	- Send a msg to MasterSchedularAgent 
	 * 
	 *  @addBehaviour 
	 *  - Receives messages from MSA
	 *  - CyclicBehaviour stays active as long as its agent is alive and will be called repeatedly after every event. 
	 *   	Quite useful to handle message reception. 
	 *   	@see for other behavioures : https://www.iro.umontreal.ca/~vaucher/Agents/Jade/primer6.html
	 *  - passParam Prefernece that has been passed from startCarAgent will be passed to the setup and to send to the master Scheduler
	 *  - Comma delimetered string will be passed to the MasterScheduler including CarReg#, Start Time, End Time
	 *  
	 * @see jade.core.Agent#setup()
	 */
	protected void setup() {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		Object[] args = getArguments();

		msg.setContent(args[0] + "," + args[1] + "," + args[2]);
		msg.addReceiver(new AID("MasterScheduler", AID.ISLOCALNAME));
		send(msg);

		addBehaviour(new CyclicBehaviour() {
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					System.out.println("Received msg from carAgent: "+ msg.getContent());
				} else {
					block();
				}
			}
		});

	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}