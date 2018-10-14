package agents;

import behaviors.TrafficBehavior;
import jade.core.Agent;

public class TrafficAgent extends Agent {

	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println(this.getLocalName() + " is ready.");
		addBehaviour(new TrafficBehavior(this));
	}
}
