/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package chargingScheduler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;

/**
 * @SystemStart
 *  - Starting point of the Agents
 *  - Construct Profile and bind to a port loop through port 8889-8898
 *  - Creates MasterScheduler
 *  - Starts CarAgentGUI
 */

public class SystemStart {
	
	private final static int carAgents = 3;
	private final static int timeForCharge = 2;
	private final static int maxLoadCapacity = 60;
	private final static int[] carBatteryMax = {100, 50, 200};
	private final static int[] carRequiredCharge = {40, 0, 200};
	private final static Times[] carTimes = new Times[3];
	private static ServerSocket serverSocket = null;
	public Profile pMain = null;
	public static ContainerController mainCtrl = null;

	/**
	 * @SystemStart
	 *  - Construct SystemStart in order to removing static behaviour  
	 */
	public SystemStart() {
		Runtime rt = Runtime.instance();
		setupProfile();
		pMain.setParameter(Profile.GUI, "true");
		mainCtrl = rt.createMainContainer(pMain);

		System.out.println(">>>>>>>>>>>>>>> Starting up a Main Agent...");
		AgentController agentCtrl = null;
		try {
			agentCtrl = mainCtrl.createNewAgent("MasterScheduler", MasterScheduler.class.getName(), new Object[0]);
			agentCtrl.start();
		} catch (StaleProxyException e) {
			System.out.println("******* Error occured while starting up the agent ******* "+ e);
		}
		
		CarAgentGui  carAgentGui = new CarAgentGui(this);
		carAgentGui.showTextFieldDemo();
	}
	

	/**
	 * @main 
	 * 	Construct SystemStart to start up the MasterSchedulerAgent and CarAgentGui
	 */
	public static void main(String[] args) {
		// Launch the Main Container (with the administration GUI on top)
		SystemStart ss = new SystemStart();
	}
	


	/**
	 *  @setupProfile
	 *   -Loop through port 8889-8898 to find free port and construct a new profile
	 */
	public void setupProfile() {
		int port = 8888;
		try {
			pMain = new ProfileImpl(null, port, null);
		} catch (Exception e) {
			System.out.println("***** Error Occured while constructing UDP Server Socket : "+ e);
		} finally {
			for (port = 8889; port < 8898; port++)
				if(pMain == null) {
					try {
						pMain = new ProfileImpl(null, port, null);
					} catch (Exception e) {
						System.out.println("******* Error occured while iterating through ports ******* "+ e);
					}
				}
			System.out.println("Port: " + port);
		}
	}
}