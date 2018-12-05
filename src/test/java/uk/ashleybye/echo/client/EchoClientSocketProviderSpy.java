package uk.ashleybye.echo.client;

import java.io.IOException;
import java.io.StringWriter;

public class EchoClientSocketProviderSpy extends EchoClientSocketProvider {

  private String hostname;
  private int port;
  private StringWriter socketWriter = new StringWriter();
  private boolean socketClosed = false;

  @Override
  public void openSocket(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  @Override
  public void write(String message) {
    socketWriter.write(message);
  }

  @Override
  public String read() {
    return "received message";
  }

  @Override
  public void closeSocket() throws IOException {
    socketWriter.close();
    socketClosed = true;
  }

  String getSentMessage() {
    return socketWriter.toString();
  }

  boolean socketWasOpenedFor(String hostname, int port) {
    return this.hostname == hostname && this.port == port;
  }

  boolean socketWasClosed() {
    return socketClosed;
  }
}
