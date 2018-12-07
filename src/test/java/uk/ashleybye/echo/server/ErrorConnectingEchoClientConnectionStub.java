package uk.ashleybye.echo.server;

public class ErrorConnectingEchoClientConnectionStub extends EchoClientConnection {

  @Override
  public void listen(int port) {
    throw new ServerConnectionException();
  }
}
