package app.handlers;
import app.FileService;
import http.Request;
import http.Response;

public class UserAgentHandler extends RequestHandler {
    public UserAgentHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        String userAgent = request.headers.get("User-Agent");
        Response response = new Response(200, userAgent);
        this.addContentHeaders(response);
        return response;
    }
}
