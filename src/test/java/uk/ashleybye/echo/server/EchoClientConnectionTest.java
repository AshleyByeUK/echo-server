package uk.ashleybye.echo.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EchoClientConnectionTest {

  private static final int VALID_PORT = 1234;

  @Test
  void testListensForConnection() {
    EchoServerSocketProviderSpy socketProvider = new EchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    clientConnection.listen(VALID_PORT);

    assertTrue(socketProvider.openAndWaitWasCalledWith(1234));
  }

  @Test
  void testReceivesMessageFromSocket() {
    EchoServerSocketProviderSpy socketProvider = new EchoServerSocketProviderSpy();

    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    assertEquals("received message", clientConnection.receiveMessage());
  }

  @Test
  void testWritesResponseToSocket() {
    EchoServerSocketProviderSpy socketProvider = new EchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    clientConnection.sendResponse("sent response");

    assertEquals("sent response", socketProvider.getMessageWrittenToSocket());
  }

  @Test
  void testClosesSocket() {
    EchoServerSocketProviderSpy socketProvider = new EchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    clientConnection.disconnect();

    assertTrue(socketProvider.closeSocketWasCalled());
  }

  @Test
  void testOpenAndWaitIOException() {
    IOExceptionEchoServerSocketProviderSpy socketProvider = new IOExceptionEchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    assertThrows(ServerConnectionException.class, () -> clientConnection.listen(VALID_PORT));
  }

  @Test
  void testReceiveMessageIOException() {
    IOExceptionEchoServerSocketProviderSpy socketProvider = new IOExceptionEchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    assertThrows(ServerConnectionException.class, () -> clientConnection.receiveMessage());
  }

  @Test
  void testDisconnectIOException() {
    IOExceptionEchoServerSocketProviderSpy socketProvider = new IOExceptionEchoServerSocketProviderSpy();
    EchoClientConnection clientConnection = new EchoClientConnection(socketProvider);

    assertThrows(ServerConnectionException.class, () -> clientConnection.disconnect());
  }
}
