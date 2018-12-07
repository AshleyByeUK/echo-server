package uk.ashleybye.echo.server;

import java.io.IOException;

public class IOExceptionEchoServerSocketProviderSpy extends EchoServerSocketProvider {

  @Override
  void openAndWait(int port) throws IOException {
    throw new IOException();
  }

  @Override
  String read() throws IOException {
    throw new IOException();
  }

  @Override
  void closeSocket() throws IOException {
    throw new IOException();
  }
}
