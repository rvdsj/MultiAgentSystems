package agents;

import behaviors.WeatherBehavior;
import jade.core.Agent;

public class WeatherAgent extends Agent {


	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println(this.getLocalName() + " is ready.");
		addBehaviour(new WeatherBehavior(this));
	}
}
