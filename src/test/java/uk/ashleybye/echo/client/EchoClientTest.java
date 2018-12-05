package uk.ashleybye.echo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

class EchoClientTest {

  private static final String HOSTNAME = "hostname";
  private static final int PORT = 1234;

  @Test
  void testSendsMessage() {
    EchoClientSocketRespondsWithSentMessageStub clientSocket = new EchoClientSocketRespondsWithSentMessageStub();
    EchoClient client = new EchoClient(clientSocket);

    client.connect(HOSTNAME, PORT);
    client.sendMessage("message");

    assertTrue(clientSocket.connectCalledWith(HOSTNAME, PORT));
    assertEquals("message", clientSocket.getSentMessage());
  }

  @Test
  void testReceivesResponse() {
    EchoClientSocketRespondsWithSentMessageStub clientSocket = new EchoClientSocketRespondsWithSentMessageStub();
    EchoClient client = new EchoClient(clientSocket);

    client.connect(HOSTNAME, PORT);
    client.sendMessage("message");

    assertEquals("message", client.getResponse());
  }

  @Test
  void testSendsSingleInputAndReceivesSingleResponse() {
    StringWriter output = new StringWriter();
    EchoClientSocketRespondsWithSentMessageStub clientSocket = new EchoClientSocketRespondsWithSentMessageStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input")),
        new PrintWriter(output));

    client.run(HOSTNAME, PORT);

    assertEquals("input\n", output.toString());
  }

  @Test
  void testSendsMultipleInputsAndReceivesMultiplesResponses() {
    StringWriter output = new StringWriter();
    EchoClientSocketRespondsWithSentMessageStub clientSocket = new EchoClientSocketRespondsWithSentMessageStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\nagain")),
        new PrintWriter(output));

    client.run(HOSTNAME, PORT);

    assertEquals("input\nagain\n", output.toString());
  }

  @Test
  void testConnectsSendsAndReceivesMultipleMessagesThenDisconnects() {
    StringWriter output = new StringWriter();
    EchoClientSocketRespondsWithSentMessageStub clientSocket = new EchoClientSocketRespondsWithSentMessageStub();
    EchoClient client = new EchoClient(
        clientSocket,
        new BufferedReader(new StringReader("input\nagain")),
        new PrintWriter(output));

    client.run(HOSTNAME, PORT);

    assertTrue(clientSocket.connectCalledWith(HOSTNAME, PORT));
    assertEquals("input\nagain\n", output.toString());
    assertTrue(clientSocket.disconnectWasCalled());
  }
}
