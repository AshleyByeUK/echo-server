package uk.ashleybye.echo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

class EchoClientTest {

  private static final String INVALID_HOSTNAME = "invalid_hostname";
  private static final int INVALID_PORT = 1111;
  private static final String VALID_HOSTNAME = "hostname";
  private static final int VALID_PORT = 1234;

  @Test
  void testConnectsSendsAndReceivesMultipleMessagesThenDisconnects() {
    StringWriter output = new StringWriter();
    RespondsWithSentMessageEchoClientSocketStub clientSocket = new RespondsWithSentMessageEchoClientSocketStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\nagain")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertTrue(clientSocket.connectCalledWith("hostname", 1234));
    assertEquals("input\nagain\n", output.toString());
    assertTrue(clientSocket.disconnectWasCalled());
  }

  @Test
  void testConnectingToInvalidServer() {
    StringWriter output = new StringWriter();
    InvalidConnectionEchoClientSocketStub clientSocket = new InvalidConnectionEchoClientSocketStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(INVALID_HOSTNAME, INVALID_PORT);

    assertEquals("Invalid connection parameters specified\n", output.toString());
  }

  @Test
  void testProblemSendingMessages() {
    StringWriter output = new StringWriter();
    ErrorSendingMessageEchoClientSocketStub clientSocket = new ErrorSendingMessageEchoClientSocketStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("An error occurred communicating with the server\n", output.toString());
  }

  @Test
  void testProblemReceivingResponses() {
    StringWriter output = new StringWriter();
    ErrorReceivingResponseEchoClientSocketStub clientSocket = new ErrorReceivingResponseEchoClientSocketStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("An error occurred communicating with the server\n", output.toString());
  }

  @Test
  void testProblemDisconnectingFromServer() {
    StringWriter output = new StringWriter();
    EchoClientSocket clientSocket = new ErrorDisconnectingEchoClientSocketStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("input\nAn error occurred communicating with the server\n", output.toString());
  }
}
