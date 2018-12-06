package uk.ashleybye.echo.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

class EchoServerTest {

  private static final int INVALID_PORT = 1111;
  private static final int VALID_PORT = 1234;

  @Test
  void testServerListensRespondsToMultipleRequestsAndShutsDown() {
    MultipleRequestsEchoClientConnectionSpy clientConnection = new MultipleRequestsEchoClientConnectionSpy();
    EchoServer server = new EchoServer(clientConnection);

    server.start(VALID_PORT);

    assertTrue(clientConnection.listenCalledWith(1234));
    assertEquals("input\nagain\n", clientConnection.getSentResponses());
    assertTrue(clientConnection.disconnectWasCalled());
  }

  @Test
  void testErrorStartingServer() {
    StringWriter output = new StringWriter();
    EchoClientConnection clientConnection = new ErrorConnectingEchoClientConnectionStub();
    EchoServer server = new EchoServer(clientConnection, new PrintWriter(output));

    server.start(INVALID_PORT);

    assertEquals("Server encountered a connection error\n", output.toString());
  }

  @Test
  void testErrorReadingClientMessage() {
    StringWriter output = new StringWriter();
    EchoClientConnection clientConnection = new ErrorReadingMessageEchoClientConnectionStub();
    EchoServer server = new EchoServer(clientConnection, new PrintWriter(output));

    server.start(VALID_PORT);

    assertEquals("Server encountered a connection error\n", output.toString());
  }

  @Test
  void testErrorDisconnectingServer() {
    StringWriter output = new StringWriter();
    EchoClientConnection clientConnection = new ErrorDisconnectingEchoClientConnectionStub();
    EchoServer server = new EchoServer(clientConnection, new PrintWriter(output));

    server.start(VALID_PORT);

    assertEquals("Server encountered a connection error\n", output.toString());
  }
}
