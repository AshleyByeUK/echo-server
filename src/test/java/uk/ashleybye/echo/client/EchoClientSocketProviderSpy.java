package uk.ashleybye.echo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
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
  public PrintWriter getSocketWriter() {
    return new PrintWriter(socketWriter);
  }

  @Override
  public BufferedReader getSocketReader() {
    return new BufferedReader(new StringReader("received message"));
  }

  @Override
  public void closeSocket() throws IOException {
    socketWriter.close();
    socketClosed = true;
  }

  public String getSentMessage() {
    return socketWriter.toString();
  }

  public boolean socketWasOpenedFor(String hostname, int port) {
    return this.hostname == hostname && this.port == port;
  }

  public boolean socketWasClosed() {
    return socketClosed;
  }
}
