# Structure
Messages structure (exception: IPINNIT):
[activityName]:[MessageType] [payload]
(Notice that there is a space character between message type and payload)

The host of a game owns the server which starts a network service finder in the local network.
A client starts the network service discovery client along with the network service discovery listener.
If a service gets found, the client may connect to the associated IP address. 

Note that the first message the server then sends to the Client is of type IPINNIT which has no message type and a payload that is -1 if the server is full or the assigned clientId.

This interaction is hardcoded in the ServerNetwork and NetworkConnection class.

The host can send messages via the server to other clients.
The clients can receive messages and pass them to the matching ClientResponse class.
Clients can send messages to the server.
The server receives messages and passes them to the matching ServerResponse class which may influence the host.

When an AppcompatActivity gets started, it has to register itself to the ClientResponseHandler (or HostResponseHandler), so eventual calls to the response handlers are able to influence the UI. 

# Example: Ping

The server action handler searches for an implementation of ServerRequestInterface that got registered with the message type Constants.PING.

In this case, it finds the RequestPing implementation and passes on the payload (in this case: 2) to its execute method.

This method then sends the message to the client with id 2.

Which then calls the send to client method in the Server network class.

Which adds the message to the ArrayDeque of the network connection to the client.

This output buffer gets checked periodically by the network connection thread, which then sends the message to the client.

The Network connection of the client receives the message and passes it to the ResponseLogic.

The response logic then tries to send the message to the client response handler if a handler with this message type got registered. 

Otherwise it retries it several times with 100ms delay (For transitionary UI delays). 

The client response handler calls the Client Response object of matching message type.

The reponse object then sends a message back to the server to complete the Ping. It also uses the AppCompatActivity to influence the UI to display the toast "Received ping from server".

The server receives this message in the network connection class which passes the message to the reponse logic which passes the message to the host response object with matching message type (again, with retry capability for transitionary UI delays of the host).

The host reponse object then displays a toast "Received ping from client".

# How to add functionality
* Add server request by creating a class that implements the ServerRequestInterface. This enables the class to send messages to clients.
* Register the server request in the action map of the Server request handler (in the static brackets)
* You can now call this server request with the static method: ServerRequestHandler.triggerAction([TYPE], [PARAMETER]);

The above steps are similar for client responses and host responses. These responses can access the assigned AppCompatActivity to influence the UI.


