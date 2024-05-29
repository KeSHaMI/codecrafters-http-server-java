package app;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileService {
    private final String rootDirectory;
    public FileService(final String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public FileReader getFileStream(final String filePath) throws FileNotFoundException {
        return new FileReader(this.rootDirectory + filePath);
    }
}
