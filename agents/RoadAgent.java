
package agents;

import behaviors.RoadBehavior;
import jade.core.Agent;

public class RoadAgent extends Agent {


	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println(this.getLocalName() + " is ready.");
		addBehaviour(new RoadBehavior(this));
	}
}
