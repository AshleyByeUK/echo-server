package uk.ashleybye.echo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

class EchoClientTest {

  private static final String INVALID_HOSTNAME = "invalid_hostname";
  private static final int INVALID_PORT = 1111;
  private static final String VALID_HOSTNAME = "hostname";
  private static final int VALID_PORT = 1234;

  @Test
  void testOpenConnectionSendsAndReceivesMultipleMessagesCloseConnection() {
    StringWriter output = new StringWriter();
    RespondsWithSentMessageEchoServerConnectionStub serverConnection = new RespondsWithSentMessageEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        new BufferedReader(new StringReader("input\nagain")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertTrue(serverConnection.connectCalledWith("hostname", 1234));
    assertEquals("input\nagain\n", output.toString());
    assertTrue(serverConnection.disconnectWasCalled());
  }

  @Test
  void testOpenConnectionToInvalidServer() {
    StringWriter output = new StringWriter();
    InvalidConnectionEchoServerConnectionStub serverConnection = new InvalidConnectionEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(INVALID_HOSTNAME, INVALID_PORT);

    assertEquals("Invalid connection parameters specified\n", output.toString());
  }

  @Test
  void testProblemSendingMessages() {
    StringWriter output = new StringWriter();
    ErrorSendingMessageEchoServerConnectionStub serverConnection = new ErrorSendingMessageEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("An error occurred communicating with the server\n", output.toString());
  }

  @Test
  void testProblemReceivingResponses() {
    StringWriter output = new StringWriter();
    ErrorReceivingResponseEchoServerConnectionStub serverConnection = new ErrorReceivingResponseEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("An error occurred communicating with the server\n", output.toString());
  }

  @Test
  void testProblemClosingServerConnection() {
    StringWriter output = new StringWriter();
    ErrorClosingEchoServerConnectionStub serverConnection = new ErrorClosingEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("input\nAn error occurred communicating with the server\n", output.toString());
  }

  @Test
  void testErrorReadingUserInput() throws IOException {
    StringWriter output = new StringWriter();
    BufferedReader input = new BufferedReader(Reader.nullReader());
    input.close(); // Will cause input to throw IOException when called.

    RespondsWithSentMessageEchoServerConnectionStub serverConnection = new RespondsWithSentMessageEchoServerConnectionStub();
    EchoClient client = new EchoClient(
        serverConnection,
        input,
        new PrintWriter(output));

    client.start(VALID_HOSTNAME, VALID_PORT);

    assertEquals("An error occurred reading user input\n", output.toString());
  }
}
