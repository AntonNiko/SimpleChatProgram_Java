import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea msgBoxText;
	private JScrollPane msgBoxPane;
	private JLabel msgTitle;
	private JTextField msgInput;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private Socket connection;
	
	public Client(){
		super("TCP Connection Client");
		setLayout(new FlowLayout());
		setSize(320,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(600,150);
		setResizable(false);
		
		// Message Box Text added to JScrollPane. Used for displaying Chat beteen client & server
		msgBoxText = new JTextArea(16,25);
		msgBoxText.setEditable(false);
		msgBoxPane = new JScrollPane(msgBoxText);
		msgBoxPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(msgBoxPane);
		
		// Message Input Field
		msgTitle = new JLabel("Input Message:");
		add(msgTitle);
		msgInput = new JTextField(20);
		msgInput.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sendMessage(e.getActionCommand());
				msgInput.setText("");
			}
		});
		add(msgInput);
	}
	

	// Attempts to connect to the server with IP Address 127.0.0.1 at remote port 4000. Tries again until connection established
	protected void connectToServer() {
		msgBoxText.append("Attempting Connection...\n");
		while(true){
			try{
				connection = new Socket(InetAddress.getByName("127.0.0.1"),4000);
				break;
			} catch(IOException e1){
				continue;
			}
		}
		msgBoxText.append("Connected to "+connection.getInetAddress().getHostName()+"\n");
	}
	
	// Sets up OutputStream & InputStream used to send & receive data respectively
	protected void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		msgBoxText.append("Streams connected!\n");
	}
	
	// Repeatedly waits for incoming data and if receives string, displays it to the user
	protected void whileConnected() throws IOException{
		do {
			try{
				message = (String) input.readObject();
				msgBoxText.append(message+"\n"); 
			}catch(ClassNotFoundException e1){
				msgBoxText.append("ERROR - Invalid Message received\n");
			}
		} while(!message.equals("SERVER - END"));
	}

	// Sends a string to the connected client with writeObject method of output Stream
	protected void sendMessage(String message){
		try{
			output.writeObject("CLIENT - "+message);
			output.flush();
			msgBoxText.append("CLIENT - "+message+"\n");
		}catch(IOException e1){
			msgBoxText.append("ERROR - Output Stream is closed\n");
		}catch(NullPointerException e2){
			msgBoxText.append("ERROR - Connection not yet established\n");
		}
	}

	// Closes Input & Output streams and connection
	protected void closeConnection(){
		msgBoxText.append("Closing...\n");
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException e1){
			// Only case for IOException is an already closed java.net instance variable. Ignore Request
		}catch(NullPointerException e2){
			// Only case for NullPointerException is an unassigned java.net instance variable
		}
		msgBoxText.append("Closed Connection\n");
	}
}	
	
