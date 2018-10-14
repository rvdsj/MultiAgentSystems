package behaviors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WeatherBehavior extends SimpleBehaviour {
	
	/**
	 * The behavior class that reports the appropriate message about its action.
	 *  - busy
	 *  - estinguished 
	 *  
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	
	public WeatherBehavior(Agent agent) {
		super(agent);
	}
	
	@Override
	public void action() {
		ACLMessage aclMessage = myAgent.receive(mt);

		if (aclMessage!=null) {
			if(aclMessage.getContent().compareTo("report") == 0)
				Report();
		} else {
			this.block();
		}
	}
	
	
	private void Report() {
		AID r = new AID ("car@"+myAgent.getHap(), AID.ISGUID);
		
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		aclMessage.addReceiver(r);	
	    //file treading	
		String weatherCOn="";
		  try {
		         File file = new File("src/weather.txt");
		         Scanner scanner = new Scanner(file);
		         while (scanner.hasNextLine()) {
		           
		           weatherCOn=scanner.nextLine();
		         }
		         scanner.close();
		       } catch (FileNotFoundException e) {
		         e.printStackTrace();
		       }
		 
		 aclMessage.setContent(weatherCOn);
		  
		myAgent.send(aclMessage);
	}

	
	public boolean done() {
		return false;
	}

	
}