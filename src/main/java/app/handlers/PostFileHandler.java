package app.handlers;
import java.io.IOException;

import app.FileService;
import http.Request;
import http.Response;


public class PostFileHandler extends RequestHandler{
    public PostFileHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        System.out.println("here");
        System.out.println(request.getBody());
        String fileName = request.path.split("/")[2];
        try {
            this.fileService.writeFile(fileName, request.getBody());
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
            return Response.getNotFoundResponse();
        }
        Response response = new Response(201, "");
        this.addContentHeaders(response);
        return response;
    }

}
