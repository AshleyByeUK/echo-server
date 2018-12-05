package uk.ashleybye.echo.client;

import java.io.IOException;

public class IOExceptionEchoClientSocketProviderStub extends EchoClientSocketProvider {

  @Override
  public void openSocket(String hostname, int port) throws IOException {
    throw new IOException();
  }

  @Override
  public void closeSocket() throws IOException {
    throw new IOException();
  }
}
