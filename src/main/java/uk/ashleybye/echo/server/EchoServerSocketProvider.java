package uk.ashleybye.echo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class EchoServerSocketProvider {

  private ServerSocket serverSocket;
  private Socket clientSocket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  void openAndWait(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
  }

  String read() throws IOException {
    return socketReader.readLine();
  }

  void write(String message) {
    socketWriter.println(message);
  }

  void closeSocket() throws IOException {
    socketReader.close();
    socketWriter.close();
    clientSocket.close();
    serverSocket.close();
  }
}
