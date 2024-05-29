package app.handlers;
import app.FileService;
import http.Request;
import http.Response;

public class EchoHandler extends RequestHandler {
    public EchoHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        String echoData = request.path.split("/")[2];
        Response response = new Response(200, echoData);
        this.addContentHeaders(response);
        return response;
    }
}
