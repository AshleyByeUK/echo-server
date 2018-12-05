package uk.ashleybye.echo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class EchoClient {

  private final EchoClientSocket clientSocket;
  private final BufferedReader input;
  private final PrintWriter output;

  EchoClient() {
    this(new EchoClientSocket());
  }

  EchoClient(EchoClientSocket clientSocket) {
    this(clientSocket, new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
  }

  EchoClient(EchoClientSocket clientSocket, BufferedReader input, PrintWriter output) {
    this.clientSocket = clientSocket;
    this.input = input;
    this.output = output;
  }

  public void run(String hostname, int port) {
    try {
      connect(hostname, port);
      sendAndReceiveUserInputLinesIndefinitely();
      disconnect();
    } catch (IOException ex) {
      exitWithMessage("An error occurred reading user input");
    }
  }

  public void connect(String hostname, int port) {
    clientSocket.connect(hostname, port);
  }

  private void sendAndReceiveUserInputLinesIndefinitely() throws IOException {
    String userInput;
    while ((userInput = input.readLine()) != null) {
      sendAndReceiveUserInputLine(userInput);
    }
  }

  private void sendAndReceiveUserInputLine(String userInput) {
    sendMessage(userInput);
    output.println(getResponse());
  }

  void sendMessage(String message) {
    clientSocket.sendMessage(message);
  }

  String getResponse() {
    return clientSocket.getResponse();
  }

  public void disconnect() {
    clientSocket.disconnect();
  }

  private void exitWithMessage(String message) {
    output.println(message);
    System.exit(1);
  }
}
