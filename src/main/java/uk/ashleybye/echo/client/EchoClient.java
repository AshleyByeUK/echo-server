package uk.ashleybye.echo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class EchoClient {

  private final EchoServerConnection serverConnection;
  private final BufferedReader input;
  private final PrintWriter output;

  public EchoClient() {
    this(new EchoServerConnection(), new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out, true));
  }

  EchoClient(EchoServerConnection serverConnection, BufferedReader input, PrintWriter output) {
    this.serverConnection = serverConnection;
    this.input = input;
    this.output = output;
  }

  public void start(String hostname, int port) {
    try {
      serverConnection.open(hostname, port);
      sendAndReceiveUserInputLinesIndefinitely();
      serverConnection.close();
    } catch (IOException ex) {
      exitWithMessage("An error occurred reading user input");
    } catch (InvalidConnectionParametersException ex) {
      exitWithMessage("Invalid connection parameters specified");
    } catch (ServerConnectionException ex) {
      exitWithMessage("An error occurred communicating with the server");
    }
  }

  private void sendAndReceiveUserInputLinesIndefinitely() throws IOException {
    String userInput;
    while ((userInput = input.readLine()) != null) {
      sendAndReceiveUserInputLine(userInput);
    }
  }

  private void sendAndReceiveUserInputLine(String userInput) {
    serverConnection.sendMessage(userInput);
    String response = serverConnection.getResponse();
    output.println(response);
  }

  private void exitWithMessage(String message) {
    output.println(message);
  }
}
