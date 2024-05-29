
import http.HttpServer;

public class Main {
  public static void main(String[] args) {
    String fileDirectory = null;
    if (args.length >= 2) {
      fileDirectory = args[1];
    } 
    HttpServer server = new HttpServer(4221, 10, fileDirectory);
    server.serve();
  }
}
