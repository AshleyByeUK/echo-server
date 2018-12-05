package uk.ashleybye.echo.client;

public class ErrorReceivingResponseEchoServerConnectionStub extends EchoServerConnection {

  @Override
  public void open(String hostname, int port) {
    // Do nothing.
  }

  @Override
  public void sendMessage(String message) {
    // Do nothing.
  }

  @Override
  public String getResponse() {
    throw new ServerConnectionException();
  }
}
