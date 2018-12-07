package uk.ashleybye.echo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class EchoServerConnection {

  private final EchoClientSocketProvider socketProvider;

  public EchoServerConnection() {
    this(new EchoClientSocketProvider());
  }

  EchoServerConnection(EchoClientSocketProvider socketProvider) {
    this.socketProvider = socketProvider;
  }

  public void open(String hostname, int port) {
    try {
      socketProvider.openSocket(hostname, port);
    } catch (UnknownHostException ex) {
      throw new InvalidConnectionParametersException();
    } catch (IOException ex) {
      throw new ClientConnectionException();
    }
  }

  public void sendMessage(String message) {
    socketProvider.write(message);
  }

  public String getResponse() {
    try {
      return socketProvider.read();
    } catch (IOException ex) {
      throw new ClientConnectionException();
    }
  }

  public void close() {
    try {
      socketProvider.closeSocket();
    } catch (IOException e) {
      throw new ClientConnectionException();
    }
  }
}
