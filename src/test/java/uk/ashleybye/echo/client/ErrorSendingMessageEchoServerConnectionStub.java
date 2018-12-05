package uk.ashleybye.echo.client;

public class ErrorSendingMessageEchoServerConnectionStub extends EchoServerConnection {

  @Override
  public void open(String hostname, int port) {
    // Do nothing.
  }

  @Override
  public void sendMessage(String message) {
    throw new ServerConnectionException();
  }
}
