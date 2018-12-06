package uk.ashleybye.echo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import uk.ashleybye.echo.client.EchoClient;
import uk.ashleybye.echo.client.EchoServerConnection;
import uk.ashleybye.echo.server.EchoServer;

public class IntegrationTest {

  private static final String HOSTNAME = "127.0.0.1";
  private static final int PORT = 9999;

  @Test
  void testClientAndServerCanCommunicateOverRealSocket() throws InterruptedException {
    StringWriter output = new StringWriter();

    startEchoServer();
    Thread.sleep(2000);
    startEchoClient(output);


    assertEquals("input\n", output.toString());
  }

  private void startEchoServer() {
    Thread thread = new Thread(runnableEchoServer());
    thread.start();
  }

  private Runnable runnableEchoServer() {
    return () -> {
      EchoServer server = new EchoServer();
      server.start(PORT);
    };
  }

  private void startEchoClient(StringWriter output) {
    EchoServerConnection connection = new EchoServerConnection();
    EchoClient client = new EchoClient(
        connection,
        new BufferedReader(new StringReader("input\n")),
        new PrintWriter(output));
    client.start(HOSTNAME, PORT);
  }
}
