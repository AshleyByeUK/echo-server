package uk.ashleybye.echo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class UnknownHostEchoClientSocketProviderStub extends EchoClientSocketProvider {

  @Override
  public void openSocket(String hostname, int port) throws IOException {
    throw new UnknownHostException();
  }
}
