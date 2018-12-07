package uk.ashleybye.echo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class MultipleRequestsEchoClientConnectionSpy extends EchoClientConnection {

  private int port;
  private int messagesReceived = 0;
  private List<Reader> messages = Arrays.asList(
      new StringReader("input\n"),
      new StringReader("again\n"),
      Reader.nullReader());
  private StringWriter output = new StringWriter();
  private PrintWriter connectionOut = new PrintWriter(output);
  private boolean disconnected = false;

  @Override
  public void listen(int port) {
    this.port = port;
  }

  @Override
  public String receiveMessage() {
    try {
      return new BufferedReader(messages.get(messagesReceived++)).readLine();
    } catch (IOException e) {
      throw new RuntimeException("Unable to read stubbed messages");
    }
  }

  @Override
  public void sendResponse(String message) {
    connectionOut.println(message);
  }

  @Override
  public void disconnect() {
    disconnected = true;
  }

  boolean listenCalledWith(int port) {
    return this.port == port;
  }

  public String getSentResponses() {
    return output.toString();
  }

  public boolean disconnectWasCalled() {
    return disconnected;
  }
}
