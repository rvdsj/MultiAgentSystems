package main;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import behaviors.FuelAgentBehavior;


public class FuelExpertAgent extends Agent {
	
	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println(this.getLocalName() + " is ready.");
		addBehaviour(new FuelAgentBehavior(this));
	}
	
	
}


