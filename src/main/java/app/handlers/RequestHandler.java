package app.handlers;
import java.util.Set;

import app.FileService;
import http.Request;
import http.Response;

public abstract class RequestHandler {
    protected static final Set<String> allowedEncodings = Set.of("gzip");
    protected final FileService fileService;
    public RequestHandler(FileService fileService) {
        this.fileService = fileService;
    }
    public abstract Response handle(Request request);

    protected void addContentHeaders(Response response, String contentType) {
        response.addHeader("Content-Type", contentType);
    }
    protected void addContentHeaders(Response response) {
        addContentHeaders(response,  "text/plain");
    }
    protected void addEncodingHeaders(Response response, Request request) {
        String encodings = request.headers.get("Accept-Encoding");
        if (encodings == null) {
            return;
        }
        String validEncodings = allowedEncodings.stream()
                .filter(encodings::contains)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        if (!validEncodings.isEmpty()) {
            response.addHeader("Content-Encoding", validEncodings);
        }
    }
}
