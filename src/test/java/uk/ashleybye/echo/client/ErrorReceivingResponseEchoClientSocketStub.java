package uk.ashleybye.echo.client;

public class ErrorReceivingResponseEchoClientSocketStub extends EchoClientSocket {

  @Override
  public void connect(String hostname, int port) {
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
