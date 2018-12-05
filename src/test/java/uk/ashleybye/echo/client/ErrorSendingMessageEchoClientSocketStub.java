package uk.ashleybye.echo.client;

public class ErrorSendingMessageEchoClientSocketStub extends EchoClientSocket {

  @Override
  public void connect(String hostname, int port) {
    // Do nothing.
  }

  @Override
  public void sendMessage(String message) {
    throw new ServerConnectionException();
  }
}
