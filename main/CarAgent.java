package main;

import jade.MicroBoot;
import jade.content.onto.basic.Action;

import java.util.Scanner;
import jade.core.AID;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CarAgent extends Agent {

	String weatherCon = "";
	String roadCon = "";
	String trafficCon = "";
	int time = 5000;
	private static final long serialVersionUID = 1L;
	gui gui;

	protected void setup() {

		gui = new gui();
		gui.setVisible(true);
		boolean act = gui.getActivate();
		System.out.println(this.getLocalName() + " is ready.");

		this.addBehaviour(new SendBehaviour(this));
		this.addBehaviour(new ReceiveBehaviour(this));
		this.addBehaviour(new FuelSendBehaviour(this));
		this.addBehaviour(new FuelReceiveBehaviour(this));

	}

	class SendBehaviour extends TickerBehaviour {

		private static final long serialVersionUID = 1L;

		public SendBehaviour(Agent agent) {
			super(agent, time);
		}
		
		@Override
		protected void onTick() {

			AID road;
			AID traffic;
			AID weather;

			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);

			double whatHappened = Math.random();
			boolean task = true;

			road = new AID("road@" + myAgent.getHap(), AID.ISGUID);
			traffic = new AID("traffic@" + myAgent.getHap(), AID.ISGUID);
			weather = new AID("weather@" + myAgent.getHap(), AID.ISGUID);
			aclMessage.addReceiver(road);
			aclMessage.addReceiver(traffic);
			aclMessage.addReceiver(weather);
			aclMessage.setContent("report");

			if (aclMessage.getContent() != null) {
				System.out.println(
						"\n--------------------------------------------------------------------------------------------------------------\n");

			}

			myAgent.send(aclMessage);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	class FuelSendBehaviour extends TickerBehaviour {

		private static final long serialVersionUID = 1L;

		public FuelSendBehaviour(Agent agent) {
			super(agent, time);
		}

		@Override
		protected void onTick() {

			AID fuel;

			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);

			double whatHappened = Math.random();
			boolean task = true;

			fuel = new AID("fuel@" + myAgent.getHap(), AID.ISGUID);
			aclMessage.addReceiver(fuel);

			if (!roadCon.equals("") && !weatherCon.equals("") && !trafficCon.equals("")) {
				String data = roadCon + ":" + weatherCon + ":" + trafficCon;
				aclMessage.setContent(data);

			}

			else {
				aclMessage.setContent("pending");
			}
			myAgent.send(aclMessage);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	class ReceiveBehaviour extends SimpleBehaviour {

		/**
		 * The behavior class that prints a message according to what the other agents
		 * reported.
		 */
		private static final long serialVersionUID = 1L;
		private final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

		public ReceiveBehaviour(Agent agent) {
			super(agent);
		}

		public void action() {
			ACLMessage aclMessage = myAgent.receive(mt);

			if (aclMessage != null) {

				if (aclMessage.getContent().compareTo("highway") == 0) {

					roadCon = "highway";

				} else if (aclMessage.getContent().compareTo("combined") == 0) {

					roadCon = "combined";
				} else if (aclMessage.getContent().compareTo("rain") == 0) {

					weatherCon = "rain";
				} else if (aclMessage.getContent().compareTo("sunny") == 0) {

					weatherCon = "sunny";
				} else if (aclMessage.getContent().compareTo("high") == 0) {

					trafficCon = "high";
				} else if (aclMessage.getContent().compareTo("normal") == 0) {

					trafficCon = "normal";
				}

				gui.setRoad(roadCon);
				gui.setWeather(weatherCon);
				gui.setTraffic(trafficCon);

			} else {
				this.block();
			}
		}

		public boolean done() {
			return false;
		}
	}

	class FuelReceiveBehaviour extends SimpleBehaviour {

		/**
		 * The behavior class that prints a message according to what the other agents
		 * reported.
		 */
		private static final long serialVersionUID = 1L;
		private final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

		public FuelReceiveBehaviour(Agent agent) {
			super(agent);
		}

		public void action() {
			ACLMessage aclMessage = myAgent.receive(mt1);

			if (aclMessage != null) {
				String[] content = aclMessage.getContent().split(",");
				if (content[0].compareTo("eff") == 0) {
					System.out.println("EFFICINECY STATUS : " + aclMessage.getContent() + " \n");
					gui.setEff("Efficient");
					gui.setNeg(content[1]);

				}

				if (content[0].compareTo("notEff") == 0) {
					System.out.println("EFFICINECY STATUS : " + aclMessage.getContent() + " \n");
					gui.setEff("Not Efficient");
					gui.setNeg(content[1]);

				}

				if (content[0].compareTo("nodata") == 0) {
					System.out.println("EFFICINECY STATUS : " + aclMessage.getContent() + " \n");
					gui.setEff("No data");
					gui.setNeg(content[1]);

				}

				// like these can add lot of scenarios

			} else {
				this.block();
			}
		}

		public boolean done() {
			return false;
		}
	}

}
