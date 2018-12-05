package uk.ashleybye.echo.client;

public class RespondsWithSentMessageEchoClientSocketStub extends EchoClientSocket {

  private String hostname;
  private int port;
  private String message;
  private boolean disconnectWasCalled = false;

  @Override
  public void connect(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
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
    disconnectWasCalled = true;
  }

  boolean connectCalledWith(String hostname, int port) {
    return this.hostname == hostname && this.port == port;
  }

  public boolean disconnectWasCalled() {
    return disconnectWasCalled;
  }
}
