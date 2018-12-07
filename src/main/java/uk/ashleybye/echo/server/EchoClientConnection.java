package uk.ashleybye.echo.server;

import java.io.IOException;

public class EchoClientConnection {

  private final EchoServerSocketProvider socketProvider;

  public EchoClientConnection() {
    this(new EchoServerSocketProvider());
  }

  EchoClientConnection(EchoServerSocketProvider socketProvider) {
    this.socketProvider = socketProvider;
  }

  public void listen(int port) {
    try {
      socketProvider.openAndWait(port);
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public String receiveMessage() {
    try {
      return socketProvider.read();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public void sendResponse(String message) {
    socketProvider.write(message);
  }

  public void disconnect() {
    try {
      socketProvider.closeSocket();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }
}
