package app.handlers;
import app.FileService;
import http.Request;
import http.Response;


public class NotFoundHandler extends RequestHandler{
    public NotFoundHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        Response response = new Response(404, "Not Found");
        this.addContentHeaders(response);
        return response;
    }
}
