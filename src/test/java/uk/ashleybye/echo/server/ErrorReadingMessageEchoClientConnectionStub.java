package uk.ashleybye.echo.server;

public class ErrorReadingMessageEchoClientConnectionStub extends EchoClientConnection {

  @Override
  public void listen(int port) {
    // Do nothing.
  }

  @Override
  public String receiveMessage() {
    throw new ServerConnectionException();
  }
}
