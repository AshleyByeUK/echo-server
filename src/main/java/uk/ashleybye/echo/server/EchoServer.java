package uk.ashleybye.echo.server;

import java.io.PrintWriter;

public class EchoServer {

  private final EchoClientConnection clientConnection;
  private final PrintWriter output;

  public static void main(String[] args) {
    EchoServer server = new EchoServer();
    server.start(9999);
  }

  public EchoServer() {
    this(new EchoClientConnection());
  }

  EchoServer(EchoClientConnection clientConnection) {
    this(clientConnection, new PrintWriter(System.out));
  }

  EchoServer(EchoClientConnection clientConnection, PrintWriter output) {
    this.clientConnection = clientConnection;
    this.output = output;
  }

  public void start(int port) {
    try {
      clientConnection.listen(port);
      receiveMessagesAndSendResponsesIndefinitely();
      clientConnection.disconnect();
    } catch (ServerConnectionException ex) {
      exitWithMessage("Server encountered a connection error");
    }
  }

  private void receiveMessagesAndSendResponsesIndefinitely() {
    String message;
    while ((message = clientConnection.receiveMessage()) != null) {
      clientConnection.sendResponse(message);
    }
  }

  private void exitWithMessage(String message) {
    output.println(message);
  }
}
