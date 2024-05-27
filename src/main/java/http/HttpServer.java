package http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.Router;
import app.handlers.RequestHandler;

public class HttpServer {
    final int port;
      private final ExecutorService executorService;


  public HttpServer(final int port, final int concurrencyLevel) {
    this.port = port;
    this.executorService = Executors.newFixedThreadPool(concurrencyLevel);
  }

    public ServerSocket createServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(this.port);
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    public Socket getClientScoket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private void handleRequest(Socket clientSocket){
        try {
            System.out.println("accepted new connection");
    
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Request request = new Request(reader);
            RequestHandler handler = new Router().getHandler(request);
    
            request.print();
    
            Response response = handler.handle(request);
            output.write(response.encode());
    
    
            } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            } 
    }

    public void serve() {
    try {

        ServerSocket serverSocket = this.createServerSocket();
        serverSocket.setReuseAddress(true);
        while (true) { 
            Socket clientSocket = this.getClientScoket(serverSocket); // Wait for connection from client.
            this.executorService.submit(() -> this.handleRequest(clientSocket));
        }
        } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
        } 
    }
}
