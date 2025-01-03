package myteam.distributednet2.privateclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class PrivateClientEntryPoint {
	public static void main(String[] args) {
		try {
			Class.forName("myteam.distributednet2.privateclient.PrivatePropertiesReader");
			Socket socket = connectCentralRouter();
			if(socket == null) {
				System.err.println("connect central router error");
				System.exit(-1);
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	private static Socket connectCentralRouter() {
		String addr = PrivatePropertiesReader.getValueSttingDataType("centralRouterAddr");
		String portStr = PrivatePropertiesReader.getValueSttingDataType("centralRouterPort");
		Socket socket = new Socket();
		try {
			socket.setReuseAddress(true);
			socket.connect(new InetSocketAddress(addr, Integer.parseInt(portStr)));
		} catch(SocketException e) {
			e.printStackTrace();
			socket = null;
		} catch(IOException e) {
			e.printStackTrace();
			socket = null;
		}
		return socket;
	}
}
