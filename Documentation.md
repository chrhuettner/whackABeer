# Network Structure
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

## Example: Ping

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

## How to add functionality
* Add server request by creating a class that implements the ServerRequestInterface. This enables the class to send messages to clients.
* Register the server request in the action map of the Server request handler (in the static brackets)
* You can now call this server request with the static method: ServerRequestHandler.triggerAction([TYPE], [PARAMETER]);

The above steps are similar for client responses and host responses. These responses can access the assigned AppCompatActivity to influence the UI.

## Example for added functionality: Send the "click on a beer" through network (Client to Server and Server to Client Communication)

1. A Client sends the beer which was clicked on. The Client can do this in the GameActivity: ClientResponseHandler.sendMessageToServer(Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, Config.clientID + ";" + beerName);. 
  The Client needs to specify its activity(GameActivity) and the Constant for clicking beer, which signifies the type of message (must be created beforehand). The clientID and the name of the clicked beer is added in the payload (delimited by ';', do not use ' '(space) as a delimiter, because network traffic separates the whole message by spaces)
2. Then a RespondToClick is registered in the Host Responses (like described in the "How to add functionality" section). From there the client message is finally sent to the server by calling ServerRequestHandler.triggerAction(Constants.CLICKED_BEER, clientMessage);
3. A ServerRequest must be added to handle it (RequestBeer): Again registering the request in the static brackets
4. Then the server uses the game logic to handle the request and then calls server.sendToClient(id, Constants.MAIN_ACTIVITY_TYPE, Constants.CLICKED_BEER, new String[]{clickedBeer+" myMessageToClient"});
5. Again the ClientResponseHandler must register the request in the static brackets (RespondToClick)
6. In RespondToClick the Client can set every item in its activity using the R class

# App Structure

Every page that can be seen in the app has its own activity and xml-file. 

Every new activity is loaded via intents. Communication between activities either works with the Constants and Config Files or with intents.

In the activities viewBinding is used instead of findViewById. For the ClientResponses this is not possible and therefore the R class is used with findViewById.

The SinglePlayerActivity and the lobby for Multiplayer loads the same GameActivity (SinglePlayer-Mode equals Multiplayer-Mode with one Client).

The GameActivity can register single and double taps on beers, which is used for points calculation.

The navigation and status bar are hidden always. The screen is locked in portrait mode always. This must be added in every onCreate method.
  

