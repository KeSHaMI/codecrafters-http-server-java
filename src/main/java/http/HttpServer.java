package http;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.FileService;
import app.Router;
import app.handlers.RequestHandler;

public class HttpServer {
    final int port;
    private final ExecutorService executorService;
    private final FileService fileService;


  public HttpServer(final int port, final int concurrencyLevel, final String filesDirectory) {
    this.port = port;
    this.executorService = Executors.newFixedThreadPool(concurrencyLevel);
    if (filesDirectory == null) {
        this.fileService = null;
        return;
    }
    File directory = new File(filesDirectory);
    if (!directory.exists()) {
        directory.mkdirs();
    }
    this.fileService = new FileService(filesDirectory);
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
            System.out.println("Reading request");
            Request request = new Request(reader);
            request.print();
            try {
                RequestHandler handler = new Router(this.fileService).getHandler(request);
                Response response = handler.handle(request);
                output.write(response.encode());
            } catch (Exception e) {
                System.out.println("Error reading request: ");
                e.printStackTrace();
            }
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
