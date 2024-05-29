package http;
import java.util.Map;
import java.util.HashMap;


public class Response {

    private final int statusCode;
    public final String body;
    private final String httpVersion = "HTTP/1.1";
    private final Map<Integer, String> statusCodesMessages = Map.of(
            200, "OK",
            404, "Not Found",
            500, "Internal Server Error"
    ); 

    private Map<String, String> headers =  new HashMap<String, String>();

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
    public static Response getNotFoundResponse() {
        return new Response(404, "Not Found");
    }
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    public byte[] encode() {
        String response = httpVersion + " " + statusCode + " " + statusCodesMessages.get(this.statusCode) + "\r\n";
        for (String key : headers.keySet()) {
            response += key + ": " + headers.get(key) + "\r\n";
        }
        response += "\r\n" + body;
        System.out.println(response);
        return response.getBytes();
    }

}
