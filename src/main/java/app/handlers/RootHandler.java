package app.handlers;
import app.FileService;
import http.Request;
import http.Response;

public class RootHandler extends RequestHandler {
    public RootHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        Response response = new Response(200, "");
        this.addContentHeaders(response);
        return response;
    }
}
