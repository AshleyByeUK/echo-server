package uk.ashleybye.echo.client;

public class InvalidConnectionEchoClientSocketStub extends EchoClientSocket {

  @Override
  public void connect(String hostname, int port) {
    throw new InvalidConnectionParametersException();
  }
}
