package uk.ashleybye.echo.client;

public class EchoClientDemo {

  public static void main(String[] args) {
    EchoClient client = new EchoClient();
    client.start("127.0.0.1", 9999);
  }
}
