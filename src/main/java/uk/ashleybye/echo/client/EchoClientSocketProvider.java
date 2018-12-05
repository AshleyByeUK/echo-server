package uk.ashleybye.echo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class EchoClientSocketProvider {

  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  void openSocket(String hostname, int port) throws IOException {
    socket = new Socket(hostname, port);
    socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    socketWriter = new PrintWriter(socket.getOutputStream());
  }

  void write(String message) {
    socketWriter.println(message);
  }

  String read() throws IOException {
    return socketReader.readLine();
  }

  void closeSocket() throws IOException {
    socketReader.close();
    socketWriter.close();
    socket.close();
  }
}
