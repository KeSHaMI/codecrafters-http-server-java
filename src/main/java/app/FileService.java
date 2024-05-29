package app;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {
    private final String rootDirectory;
    public FileService(final String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public FileReader getFileStream(final String filePath) throws FileNotFoundException {
        return new FileReader(this.rootDirectory + filePath);
    }
    public void writeFile(final String filePath, final String content) throws IOException {
        FileWriter fileWriter = new FileWriter(this.rootDirectory + filePath);
        fileWriter.write(content);
    }
}
