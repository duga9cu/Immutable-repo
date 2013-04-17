package imm_obj;

import imm_interf.Immutable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;


public class Client {

	final static int SPORT = 4000;
	final static String SHOST = "localhost" ;
	final static int DPORT = 4002;
	final static String DADDRESS = "228.5.6.7";
	
	protected static Socket client;
	protected MulticastSocket dSocket;
	protected static ObjectOutputStream out;
	protected ObjectInputStream in;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//create an Immutable object to validate
			Immutable clientObj = new ImmutableImpl.Builder(240, 8).
					thirdMember(100).fourthMember(35).sixthMember(34).build();
			
			//create the list wanted by the server for the validation
			List<Immutable> objectToValidate = clientObj.validateMyself();
			
			//connect to server socket
			client = new Socket(SHOST, SPORT);
			out = new ObjectOutputStream(client.getOutputStream());
			
			//send the object
//			out.writeObject(objectToValidate);
			out.writeObject(objectToValidate.get(0));
			out.writeObject(objectToValidate.get(1));
			out.flush();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
