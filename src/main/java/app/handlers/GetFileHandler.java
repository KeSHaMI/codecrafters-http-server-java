package app.handlers;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import app.FileService;
import http.Request;
import http.Response;

public class GetFileHandler extends RequestHandler{
    public GetFileHandler(FileService fileService) {
        super(fileService);
    }
    public Response handle(Request request) {
        String filePath = request.path.split("/")[2];
        try {
            String fileContent = this.readFile(filePath);
            System.out.println(fileContent);
            Response response = new Response(
                200,
                fileContent
            );
            this.addContentHeaders(response, "application/octet-stream");
            return response;
        }
        catch (FileNotFoundException e) {
            return Response.getNotFoundResponse();
        }
        catch (IOException e) {
            return Response.getNotFoundResponse();
        }

    }
    private String readFile(String filePath) throws FileNotFoundException, IOException {
        FileReader file = this.fileService.getFileStream(filePath);
        BufferedReader reader = new BufferedReader(file);
        String fileContent = "";
        while (reader.ready()) {
            fileContent += reader.readLine() + "\n";
        }
        return fileContent.substring(0, fileContent.length() - 1);
    
    }
}
