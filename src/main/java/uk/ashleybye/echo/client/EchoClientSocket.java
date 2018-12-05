package uk.ashleybye.echo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClientSocket {

  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public void connect(String hostname, int port) {
    try {
      socket = new Socket(hostname, port);
      socketWriter = new PrintWriter(socket.getOutputStream());
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (UnknownHostException ex) {
      throw new InvalidConnectionParametersException();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public void sendMessage(String message) {
    socketWriter.write(message);
  }

  public String getResponse() {
    try {
      return socketReader.readLine();
    } catch (IOException ex) {
      throw new ServerConnectionException();
    }
  }

  public void disconnect() {
    try {
      socketReader.close();
      socketWriter.close();
      socket.close();
    } catch (IOException e) {
      throw new ServerConnectionException();
    }
  }
}
