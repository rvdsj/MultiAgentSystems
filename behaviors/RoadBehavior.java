package behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RoadBehavior extends SimpleBehaviour {
	
	/**
	 * The behavior class that reports the appropriate message about its action.
	 *  - busy
	 *  - estinguished 
	 *  
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	
	public RoadBehavior(Agent agent) {
		super(agent);
	}
	
	public void action() {
		ACLMessage aclMessage = myAgent.receive(mt);

		if (aclMessage!=null) {
			if(aclMessage.getContent().compareTo("report") == 0)
				Report();
		} else {
			this.block();
		}
	}
	
	private boolean randomGen() {
		if(Math.random() < 0.2) {
			return true;
		} else {
			return false;
		}
	}
	
	private void Report() {
		AID r = new AID ("car@"+myAgent.getHap(), AID.ISGUID);
		
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		aclMessage.addReceiver(r);
		if(randomGen()) { 
			aclMessage.setContent("highway");
		} else {
			aclMessage.setContent("combined");
		}
		
		myAgent.send(aclMessage);
	}

	
	public boolean done() {
		return false;
	}
}