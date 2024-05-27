package app.handlers;
import http.Request;
import http.Response;

public class RootHandler extends RequestHandler {
    public Response handle(Request request) {
        Response response = new Response(200, "");
        this.addContentHeaders(response);
        return response;
    }
}
