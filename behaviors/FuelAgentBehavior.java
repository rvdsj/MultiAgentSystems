
package behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FuelAgentBehavior extends SimpleBehaviour {

	String weatherCon = "";
	String roadCon = "";
	String trafficCon = "";
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

	public FuelAgentBehavior(Agent agent) {
		super(agent);
	}

	
	public void action() {
		ACLMessage aclMessage = myAgent.receive(mt);

		if (aclMessage != null) {

			String data = aclMessage.getContent();
			if (!data.equals("pending")) {
				String dataArray[] = data.split(":");
				roadCon = dataArray[0];
				weatherCon = dataArray[1];
				trafficCon = dataArray[2];
			}

			Report();
		} else {
			this.block();
		}
	}

	private void Report() {
		AID r = new AID("car@" + myAgent.getHap(), AID.ISGUID);

		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		aclMessage.addReceiver(r);

		if (weatherCon.equals("sunny") && trafficCon.equals("normal") && roadCon.equals("highway")) {
			System.out.println("Advice : Good condition for driving save more fuel \n");
			String content = "eff,".concat("Good condition for driving save more fuel");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("sunny") && trafficCon.equals("high") && roadCon.equals("combined")) {
			System.out.println("Advice : Try to avoid traffic \n");
			String content = "notEff,".concat("Try to avoid traffic");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("rain") && trafficCon.equals("normal") && roadCon.equals("highway")) {
			System.out.println("Advice : drive carefully due to rain and highway may be slippery \n");
			String content = "notEff,".concat("drive carefully due to rain and highway may be slippery");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("rain") && trafficCon.equals("normal") && roadCon.equals("combined")) {
			System.out.println("Advice : drive carefully due to rain and road may be slippery \n");
			String content = "notEff,".concat("drive carefully due to rain and highway may be slippery");
			aclMessage.setContent(content);

		}

		else if (weatherCon.equals("") && trafficCon.equals("normal") && roadCon.equals("combined")) {
			System.out.println(" Need weather data for further evaluate \n");
			String content = "nodata,".concat("Need weather data for further evaluate");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("sunny") && trafficCon.equals("normal") && roadCon.equals("combined")) {
			System.out.println(" Good for drving. \n");
			String content = "eff,".concat("Good for drving");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("sunny") && trafficCon.equals("") && roadCon.equals("combined")) {
			System.out.println(" Need traffic data for further evaluate \n");
			String content = "nodata,".concat("Need traffic data for further evaluate");
			aclMessage.setContent(content);
		}

		else if (weatherCon.equals("sunny") && trafficCon.equals("normal") && roadCon.equals("")) {
			System.out.println(" Need road data for further evaluate \n");
			String content = "nodata,".concat("Need road data for further evaluate");
			aclMessage.setContent(content);
		}

		else {
			String content = "nodata,".concat("data not available");
			aclMessage.setContent(content);
		}

		myAgent.send(aclMessage);
	}

	public boolean done() {
		return false;
	}
}
