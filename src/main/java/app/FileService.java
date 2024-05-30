package app;
import java.io.File;
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
        return new FileReader(this.getFullFilePath(filePath));
    }

    public void writeFile(final String filePath, final String content) throws IOException {
        File file = new File(this.getFullFilePath(filePath));
        file.createNewFile();
        file.setWritable(true);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
    private String getFullFilePath(final String filePath) {
        return this.rootDirectory + "/" + filePath;
    }
}
