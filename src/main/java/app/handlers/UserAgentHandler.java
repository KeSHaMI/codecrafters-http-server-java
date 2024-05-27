package app.handlers;
import http.Request;
import http.Response;

public class UserAgentHandler extends RequestHandler {
    public Response handle(Request request) {
        String userAgent = request.headers.get("User-Agent");
        Response response = new Response(200, userAgent);
        this.addContentHeaders(response);
        return response;
    }
}
