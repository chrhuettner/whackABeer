# Structure
Messages structure (exception: IPINNIT):
[activityName]:[MessageType] [payload]
(Notice that there is a space character between message type and payload)

The host of a game owns the server which starts a network service finder in the local network.
A client starts the network service discovery client along with the network service discovery listener.
If a service gets found, the client may connect to the associated IP address. 

Note that the first message the server then sends to the Client is of type IPINNIT which has no message type and a payload that is -1 if the server is full or the assigned clientId
This interaction is hardcoded in the ServerNetwork and NetworkConnection class.

The host can send messages via the server to other clients.
The clients can receive messages and pass them to the matching ClientResponse class.
Clients can send messages to the server.
The server receives messages and passes them to the matching ServerResponse class which may influence the host.

Example:

[Host]  
  ServerActionHandler.triggerAction(Constants.PING, 2);
The server action handler searches for an implementation of ServerRequestInterface that got registered with the message type Constants.PING.
In this case, it finds the RequestPing implementation and passes on the payload (in this case: 2) to its execute method.
This method then sends the message to the client with id 2:
<Server]
   server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.PING, new String[]{""+id});
This calls the method in the Server network class, which executes the following method:
   clientConnections.get(clientId).send(message);
Which adds the message to the ArrayDeque of the network connection to the client 
<NetworkConnection]
   outputBuffer.add(message);
This output buffer gets checked periodically by the network connection thread, which then sends the message to the client.
[NetworkConnection]
[Server]
[Host] 

<NetworkConnection]
The Network connection of the client receives the message and passes it to the ResponseLogic:
  logic.sendHandle(messageSplit[1], messageSplit[0], isServerConnection);
<ResponseLogic]
The logic then tries to send the message to the client response handler if a handler with this message type got registered. 
Otherwise it retries it several times with 100ms delay (For transitionary UI delays). 
<ClientResponseHandler]
The client response handler calls the Client Response object of matching message type.
  clientAction.execute(uiActivity, message);
<RespondToPing]
The reponse object then sends a message back to the server to complete the Ping. It may also use the AppCompatActivity to influence the UI.
  ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.PING, new Object[]{Config.clientID});
[RespondToPing]
[ClientResponseHandler]
  
[ResponseLogic]
