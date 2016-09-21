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

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;

import jade.wrapper.*;

/**
 * This class shows an example of how to run JADE as a library from an external program 
 * and in particular how to start an agent and interact with it by  means of the 
 * Object-to-Agent (O2A) interface.
 * 
 * @author Giovanni Iavarone - Michele Izzo
 */

public class SystemStart {
	
	private final static int carAgents = 6;
	private final static int timeForCharge = 2;
	private final static int maxLoadCapacity = 60;

	public static void main(String[] args) throws StaleProxyException, InterruptedException {
		// Get a hold to the JADE runtime
		Runtime rt = Runtime.instance();
		
		// Launch the Main Container (with the administration GUI on top) listening on port 8888
		System.out.println(">>>>>>>>>>>>>>> Launching the platform Main Container...");
		Profile pMain = new ProfileImpl(null, 8888, null);
		pMain.setParameter(Profile.GUI, "true");
		ContainerController mainCtrl = rt.createMainContainer(pMain);

		// Create and start an agent of class MasterScheduler
		System.out.println(">>>>>>>>>>>>>>> Starting up a CounterAgent...");
		AgentController agentCtrl = mainCtrl.createNewAgent("MasterScheduler", MasterScheduler.class.getName(), new Object[0]);
		agentCtrl.start();
		
		for (int i=1; i<=carAgents; i++)
		{
			// Create and start an agent of class CarAgent
			System.out.println(">>>>>>>>>>>>>>> Starting up a CarAgent...");
			AgentController agentCtrlc = mainCtrl.createNewAgent("CarAgent" + i, CarAgent.class.getName(), new Object[0]);
			agentCtrlc.start();
		}	
		

	}
}