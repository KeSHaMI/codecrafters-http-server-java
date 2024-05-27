package app.handlers;
import http.Request;
import http.Response;

public abstract class RequestHandler {
    public abstract Response handle(Request request);

    protected void addContentHeaders(Response response, String contentType) {
        response.addHeader("Content-Type", contentType);
        response.addHeader("Content-Length", response.body.length() + "");
    }
    protected void addContentHeaders(Response response) {
        addContentHeaders(response,  "text/plain");
    }
}
