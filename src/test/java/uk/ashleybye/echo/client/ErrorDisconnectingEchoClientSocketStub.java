package uk.ashleybye.echo.client;

public class ErrorDisconnectingEchoClientSocketStub extends EchoClientSocket {

  private String message;

  @Override
  public void connect(String hostname, int port) {
    // Do nothing.
  }

  @Override
  public void sendMessage(String message) {
    this.message = message;
  }

  @Override
  public String getResponse() {
    return message;
  }

  @Override
  public void disconnect() {
    throw new ServerConnectionException();
  }
}
