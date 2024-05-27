package http;

import java.util.Map;

public class Request {
    public final HttpMethod method;
    public final String path;
    final String version;

    private final Map<String, HttpMethod> methods = Map.of(
            "GET", HttpMethod.GET,
            "POST", HttpMethod.POST
    );

    public Request(String headerLine) {
        System.out.println(headerLine);
        this.method = parseMethod(headerLine);
        this.path = parsePath(headerLine);
        this.version = parseVersion(headerLine);
    }
    private HttpMethod parseMethod(String headerLine) {

        return this.methods.get(headerLine.split(" ")[0]);
    }
    private String parsePath(String headerLine) {
        return headerLine.split(" ")[1];
    }

    private String parseVersion(String headerLine) {
        return headerLine.split(" ")[2];
    }
    public void print() {
        System.out.println("Method: " + method);
        System.out.println("Path: " + path);
        System.out.println("Version: " + version);
    }
}
