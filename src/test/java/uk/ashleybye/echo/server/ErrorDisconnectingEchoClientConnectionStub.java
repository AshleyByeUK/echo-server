package uk.ashleybye.echo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ErrorDisconnectingEchoClientConnectionStub extends EchoClientConnection {

  @Override
  public void listen(int port) {
    // Do nothing.
  }

  @Override
  public String receiveMessage() {
    try {
      return new BufferedReader(Reader.nullReader()).readLine();
    } catch (IOException e) {
      throw new RuntimeException("Error reading null reader.");
    }
  }

  @Override
  public void sendResponse(String message) {
    // Do nothing.
  }

  @Override
  public void disconnect() {
    throw new ServerConnectionException();
  }
}
