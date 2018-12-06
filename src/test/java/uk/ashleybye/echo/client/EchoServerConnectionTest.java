package uk.ashleybye.echo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EchoServerConnectionTest {

  private static final String INVALID_HOSTNAME = "invalid_hostname";
  private static final int INVALID_PORT = 1111;
  private static final String VALID_HOSTNAME = "valid_hostname";
  private static final int VALID_PORT = 1234;

  @Test
  void testMessageCorrectlySentToSocket() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);
    serverConnection.sendMessage("sent message");

    assertEquals("sent message", socketProvider.getSentMessage());
  }

  @Test
  void testMessageCorrectlyReceivedFromSocket() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);

    assertEquals("received message", serverConnection.getResponse());
  }

  @Test
  void testReceivesCorrectConnectionParameters() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);
    serverConnection.open(VALID_HOSTNAME, VALID_PORT);

    assertTrue(socketProvider.socketWasOpenedFor("valid_hostname", 1234));
  }

  @Test
  void testReceivesDisconnectionRequest() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);
    serverConnection.close();

    assertTrue(socketProvider.socketWasClosed());
  }

  @Test
  void testInvalidSocketConnectionParameters() {
    UnknownHostEchoClientSocketProviderStub socketProvider = new UnknownHostEchoClientSocketProviderStub();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);

    assertThrows(InvalidConnectionParametersException.class,
        () -> serverConnection.open(INVALID_HOSTNAME, INVALID_PORT));
  }

  @Test
  void testGetSocketReaderAndWriterIOError() {
    IOExceptionEchoClientSocketProviderStub socketProvider = new IOExceptionEchoClientSocketProviderStub();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);

    assertThrows(ClientConnectionException.class, () -> serverConnection.open(VALID_HOSTNAME, VALID_PORT));
  }

  @Test
  void testSocketReaderIOError() {
    IOExceptionEchoClientSocketProviderStub socketProvider = new IOExceptionEchoClientSocketProviderStub();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);

    assertThrows(ClientConnectionException.class, () -> serverConnection.getResponse());
  }

  @Test
  void testCloseSocketError() {
    IOExceptionEchoClientSocketProviderStub socketProvider = new IOExceptionEchoClientSocketProviderStub();

    EchoServerConnection serverConnection = new EchoServerConnection(socketProvider);

    assertThrows(ClientConnectionException.class, () -> serverConnection.close());
  }
}
