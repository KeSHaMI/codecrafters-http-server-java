package http;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import app.encoding.GzipEncoder;


public class Response {

    private final int statusCode;
    public final String body;
    private final String httpVersion = "HTTP/1.1";
    private final Map<Integer, String> statusCodesMessages = Map.of(
            200, "OK",
            201, "Created",
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
    public byte[] encode() throws IOException{
        System.out.println("Encoding response");

        String encoding = headers.get("Content-Encoding");
        byte[] encodedBody;
        if (encoding != null) {
            System.out.println("Encoding response");
            if (encoding.equals("gzip")) {
                System.out.println("Gzipping response");
                encodedBody = new GzipEncoder().encode(body);
            }
            else {
                encodedBody = body.getBytes();
            }
            
        } else {
            encodedBody = body.getBytes();
        }
        this.headers.put("Content-Length", String.valueOf(encodedBody.length));
        String response = httpVersion + " " + statusCode + " " + statusCodesMessages.get(this.statusCode) + "\r\n";
        for (String key : headers.keySet()) {
            response += key + ": " + headers.get(key) + "\r\n";
        }
        response += "\r\n";
        byte[] responseBytes = response.getBytes();
        System.out.println(response);
        
        return mergeByteArrays(responseBytes, encodedBody);
    }
    private byte[] mergeByteArrays(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( a );
        outputStream.write( b );
        return outputStream.toByteArray();
    }

}
