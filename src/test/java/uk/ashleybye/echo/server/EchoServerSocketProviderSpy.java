package uk.ashleybye.echo.server;

class EchoServerSocketProviderSpy extends EchoServerSocketProvider {

  private int port;
  private String message;
  private boolean socketClosed = false;

  @Override
  void openAndWait(int port) {
    this.port = port;
  }

  @Override
  String read() {
    return "received message";
  }

  @Override
  void write(String message) {
    this.message = message;
  }

  @Override
  void closeSocket() {
    socketClosed = true;
  }

  boolean openAndWaitWasCalledWith(int port) {
    return this.port == port;
  }

  String getMessageWrittenToSocket() {
    return message;
  }

  boolean closeSocketWasCalled() {
    return socketClosed;
  }
}
