package imm_interf;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	final static int SPORT = 4000;
	final static int DSPORT = 4001;
	final static int DPORT = 4002;
	final static String DADDRESS = "228.5.6.7";
	public static ServerSocket server;
	public static Socket client;
	
	public static DatagramSocket dSocket;
	public static InetAddress group;
	
	public static boolean accept() throws IOException, ClassNotFoundException{

			return false;
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		server = new ServerSocket(SPORT);
		dSocket = new DatagramSocket(DSPORT);
		group = InetAddress.getByName(DADDRESS);
		
		while(true){
					
			System.out.println("------------------------------------------------");
			System.out.println("		sono sospeso							");
			System.out.println("------------------------------------------------");
			client = server.accept();
			ServerThread s = new ServerThread(client, group, dSocket);
			s.start();
			
		}

	}
}
