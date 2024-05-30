package app.encoding;

import java.io.IOException;

public interface ContentEncoder {
    public byte[] encode(String content) throws IOException;
}
