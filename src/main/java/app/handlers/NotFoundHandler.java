package app.handlers;
import http.Request;
import http.Response;


public class NotFoundHandler extends RequestHandler{
    public Response handle(Request request) {
        Response response = new Response(404, "Not Found");
        this.addContentHeaders(response);
        return response;
    }
}
