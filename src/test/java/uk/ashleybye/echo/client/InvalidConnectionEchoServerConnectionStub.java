package uk.ashleybye.echo.client;

public class InvalidConnectionEchoServerConnectionStub extends EchoServerConnection {

  @Override
  public void open(String hostname, int port) {
    throw new InvalidConnectionParametersException();
  }
}
