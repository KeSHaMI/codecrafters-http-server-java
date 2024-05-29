package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public final HttpMethod method;
    public final String path;
    public Map<String, String> headers;
    final String version;
    public final String body;

    private final Map<String, HttpMethod> methods = Map.of(
            "GET", HttpMethod.GET,
            "POST", HttpMethod.POST
    );

    public Request(BufferedReader reader) {
        this.body = "";
        try {
            String headerLine = reader.readLine();
            System.out.println("Header Line: " + headerLine);
            System.out.println(headerLine);
            this.method = parseMethod(headerLine);
            this.path = parsePath(headerLine);
            this.version = parseVersion(headerLine);
            this.headers = new HashMap<String, String>();
            while (true) { 
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("Header Line: " + line);
                String[] splittedHeader = line.split(": ");
                if (splittedHeader.length == 1) {
                    System.out.println(splittedHeader[0]);
                    break;
                }
                String headerName = splittedHeader[0];
                String headerValue = splittedHeader[1];
                this.headers.put(headerName, headerValue);

            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading request");
        }

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
