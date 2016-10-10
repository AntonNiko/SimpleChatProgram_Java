# SimpleChatProgram_Java

This is a simple Chat Program written in Java, where a Client connects to a Server on port 4000. This program is designed to showcase the use of the java.net, java.io, java.awt & javax.swing packages in use as a simple method to provide a User-friendly way of communicating between 2 hosts using a Server-Client model

The Client and Server programs both use instances of java.net classes such as SocketServer,ObjectOutputStream,ObjectInputStream & Socket.

## Structure

##### Server Program:
|  Class   | Purpose   | Notable Methods |
|:--------:|:---------:|:-------:|
| ServerRun | Initializes GUI Instance, controls Server Actions by calling non-static methods | *main()* |
| Server | Contains Methods that operates Server, handles exceptions and outputs to user| *public Server()*, *start()*, *stop()*, *sendMessage()*, *listenForConnections()* |

##### Client Program: 
|  Class   | Purpose   | Notable Methods |
|:--------:|:---------:|:-------:|
| ClientRun | Initializes GUI Instance, controls Client Actions by calling non-static methods | *main()* |
| Client | Contains Methods that operates Client, handles exceptions and outputs to user| *public Client()*, *connectToServer()*, *sendMessage()*, *closeConnection()* |

