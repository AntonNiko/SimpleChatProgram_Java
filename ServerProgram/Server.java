import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea msgBoxText;
	private JScrollPane msgBoxPane;
	private JLabel msgTitle;
	private JTextField msgInput;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static ServerSocket server;
	private static Socket connection;
	
	public Server(){
		super("TCP Connection Server");
		setLayout(new FlowLayout());
		setSize(320,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(200,150);
		setResizable(false);
		
		// Message Box Text added to JScrollPane. Used for displaying Chat beteen client & server
		msgBoxText = new JTextArea(16,25);
		msgBoxText.setEditable(false);
		msgBoxPane = new JScrollPane(msgBoxText);
		msgBoxPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(msgBoxPane);
		
		// Message Input Field
		msgTitle = new JLabel("Input Message Below:");
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

	// Creates ServerSocket instance at port 4000 with backlog of 100 connections
	protected void start(){
		try{
			server = new ServerSocket(4000,100);
		}catch(IOException e1){
			JOptionPane.showMessageDialog(null,"The server has already been started.","java.io.IOException",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	// Accept any incoming connection requests, displays Connection information
	protected void listenForConnection() throws IOException {
		connection = server.accept();
		msgBoxText.append("Now connected to "+connection.getInetAddress().getHostName()+"\n");
	}
	
	// Sets up OutputStream & InputStream used to send & receive data respectively
	protected void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		msgBoxText.append("Streams are now Setup!\n");
	}
	
	// Displays Connection success. Repeatedly awaits incoming data and displays any received strings
	protected void whileConnected() throws IOException {
		String message = "You are now connected!";
		do {
			try{
				message = (String) input.readObject();
			    msgBoxText.append(message+"\n");
			} catch(ClassNotFoundException e1){
				msgBoxText.append("Invalid message received\n");
			}
		} while(!message.equals("CLIENT - END"));
	}
	
	// Sends a string to the connected client with writeObject method of output Stream
	protected void sendMessage(String message){
		try{
			output.writeObject("SERVER - "+message);
			output.flush();
			msgBoxText.append("SERVER - "+message+"\n");
		}catch(IOException e1){
			msgBoxText.append("ERROR - Output Stream is closed\n");
		}catch(NullPointerException e2){
			msgBoxText.append("ERROR - Connection not yet established\n");
		}
	}	
	
	// Closes Input & Output streams, connection and finally ServerSocket instance
	protected void stop(){
		msgBoxText.append("Closing...\n");
		try{
			output.close();
			input.close();
			connection.close();
			server.close();
		}catch(IOException e1){
			// Only case for IOException is an already closed java.net instance variable. Ignore Request
		}catch(NullPointerException e2){
			// Only case for NullPointerException is an unassigned java.net instance variable
		}
		msgBoxText.append("Closed Connection\n");
	}
}
