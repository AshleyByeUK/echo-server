package uk.ashleybye.echo.client;

public class ErrorClosingEchoServerConnectionStub extends EchoServerConnection {

  private String message;

  @Override
  public void open(String hostname, int port) {
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
  public void close() {
    throw new ClientConnectionException();
  }
}
