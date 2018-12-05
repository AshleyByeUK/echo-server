package uk.ashleybye.echo.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EchoClientSocketTest {

  private static final String INVALID_HOSTNAME = "invalid_hostname";
  private static final int INVALID_PORT = 1111;
  private static final String VALID_HOSTNAME = "valid_hostname";
  private static final int VALID_PORT = 1234;

  @Test
  void testMessageCorrectlySentToSocket() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);
    clientSocket.sendMessage("sent message");

    assertEquals("sent message", socketProvider.getSentMessage());
  }

  @Test
  void testMessageCorrectlyReceivedFromSocket() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);

    assertEquals("received message", clientSocket.getResponse());
  }

  @Test
  void testReceivesCorrectConnectionParameters() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);
    clientSocket.connect(VALID_HOSTNAME, VALID_PORT);

    assertTrue(socketProvider.socketWasOpenedFor("valid_hostname", 1234));
  }

  @Test
  void testReceivesDisconnectionRequest() {
    EchoClientSocketProviderSpy socketProvider = new EchoClientSocketProviderSpy();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);
    clientSocket.disconnect();

    assertTrue(socketProvider.socketWasClosed());
  }

  @Test
  void testInvalidSocketConnectionParameters() {
    UnknownHostEchoClientSocketProviderStub socketProvider = new UnknownHostEchoClientSocketProviderStub();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);

    assertThrows(InvalidConnectionParametersException.class,
        () -> clientSocket.connect(INVALID_HOSTNAME, INVALID_PORT));
  }

  @Test
  void testGetSocketReaderAndWriterIOError() {
    IOExceptionEchoClientSocketProviderStub socketProvider = new IOExceptionEchoClientSocketProviderStub();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);

    assertThrows(ServerConnectionException.class, () -> clientSocket.connect(VALID_HOSTNAME, VALID_PORT));
  }

  @Test
  void testCloseSocketError() {
    IOExceptionEchoClientSocketProviderStub socketProvider = new IOExceptionEchoClientSocketProviderStub();

    EchoClientSocket clientSocket = new EchoClientSocket(socketProvider);

    assertThrows(ServerConnectionException.class, () -> clientSocket.disconnect());
  }
}
