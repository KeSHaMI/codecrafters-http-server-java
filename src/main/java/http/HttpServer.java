package http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import app.Router;
import app.handlers.RequestHandler;

public class HttpServer {
    final int port;
    public HttpServer(int port){
        this.port = port;
    }

    public ServerSocket createServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(this.port);
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    public Socket getClientScoket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    public void serve() {
    try {

        ServerSocket serverSocket = this.createServerSocket();
        Socket clientSocket = this.getClientScoket(serverSocket); // Wait for connection from client.
       
        System.out.println("accepted new connection");

        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String data = reader.readLine();
        Request request = new Request(data);
        RequestHandler handler = new Router().getHandler(request);

        request.print();

        Response response = handler.handle(request);
        output.write(response.encode());


        } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
        } 
    }
}
