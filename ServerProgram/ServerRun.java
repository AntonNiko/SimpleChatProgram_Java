import java.io.*;

public class ServerRun {
	public static void main(String[] args){

		// Declaration & Initialization of Server class
		Server server = new Server();
		server.setVisible(true);
		server.start();
		while(true){
			try{
				server.listenForConnection();
				server.setupStreams();
				server.whileConnected();
				server.stop();
				break;
			}catch(IOException e1){
				server.stop();
				break;
			}
		}
	}
}
