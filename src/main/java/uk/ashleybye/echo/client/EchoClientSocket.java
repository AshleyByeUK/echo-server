package uk.ashleybye.echo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class EchoClientSocket {

  private final EchoClientSocketProvider socketProvider;

  public EchoClientSocket() {
    this(new EchoClientSocketProvider());
  }

  EchoClientSocket(EchoClientSocketProvider socketProvider) {
    this.socketProvider = socketProvider;
  }

  public void connect(String hostname, int port) {
    try {
      socketProvider.openSocket(hostname, port);
    } catch (UnknownHostException ex) {
      throw new InvalidConnectionParametersException();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public void sendMessage(String message) { // TODO: println
    socketProvider.getSocketWriter().write(message);
  }

  public String getResponse() {
    try {
      return socketProvider.getSocketReader().readLine();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public void disconnect() {
    try {
      socketProvider.closeSocket();
    } catch (IOException e) {
      throw new ServerConnectionException();
    }
  }
}
