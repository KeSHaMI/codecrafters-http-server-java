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
    private String body;

    private final Map<String, HttpMethod> methods = Map.of(
            "GET", HttpMethod.GET,
            "POST", HttpMethod.POST
    );

    public Request(BufferedReader reader) {
        try {
            String headerLine = reader.readLine();
            this.method = parseMethod(headerLine);
            this.path = parsePath(headerLine);
            this.version = parseVersion(headerLine);
            this.headers = new HashMap<String, String>();

            this.processHeaders(reader);
            if (this.method == HttpMethod.POST) {
                this.processBody(reader);    
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
    private void processHeaders(BufferedReader reader) {
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) {
                    return;
                }
                System.out.println(line);
                String[] splittedHeader = line.split(": ");

                String headerName = splittedHeader[0];
                String headerValue = splittedHeader[1];
                this.headers.put(headerName, headerValue);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading request");
        }
    }
    private void processBody(BufferedReader reader) {
        try {
            int contentLength = Integer.parseInt(this.headers.get("Content-Length"));
            char[] body = new char[contentLength];
            reader.read(body, 0, contentLength);            
            this.body = new String(body);
        } catch (IOException e) {
            throw new RuntimeException("Error reading request");
        }
    }
    public void print() {
        System.out.println("Method: " + method);
        System.out.println("Path: " + path);
        System.out.println("Version: " + version);
        System.out.println("Headers: " + headers);
        System.out.println("Body: " + body);
    }
    public String getBody() {
        return this.body;
    }
}
