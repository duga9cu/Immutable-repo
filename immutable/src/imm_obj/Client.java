package imm_obj;

import imm_interf.Immutable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;

import definitions.Definitions;

/**
 * This class is a client which do the following:
 * 		- instantiate two objects with dummy values: one is immutable, the other is not.
 * 		- connect to the server.
 * 		- depending on the option chosed in Definitions.java sends the first or the second object to the server
 * 			for the validation.
 * 
 * @author Lorenzo Rotteglia
 */
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
			Immutable clientObjImmutable = new ImmutableImpl.Builder(333, 333).
					thirdMember(333).fourthMember(333).sixthMember(333).build().validateMyself();
			Immutable clientObjNOTImmutable = new NotImmutableImpl(222).validateMyself();						

			//connect to server socket
			client = new Socket(SHOST, SPORT);
			out = new ObjectOutputStream(client.getOutputStream());

			//send the object
			System.out.println("client: sending object to validate..");
			if	(Definitions.SEND_IMMUTABLE) out.writeObject(clientObjImmutable); 
			else out.writeObject(clientObjNOTImmutable);
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
