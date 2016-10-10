import java.io.*;

public class ClientRun {

	public static void main(String[] args){
		
		// Declaration & Initialization of Client class
		Client client = new Client();
		client.setVisible(true);
		while(true){
			client.connectToServer();
			try{
				client.setupStreams();
				client.whileConnected();
				client.closeConnection();
				break;
			}catch(IOException e1){
				client.closeConnection();
				break;
			}
		}
	}
}
