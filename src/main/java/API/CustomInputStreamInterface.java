package API;

import java.io.IOException;
import java.io.InputStream;

public interface CustomInputStreamInterface {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
     int MAX_SKIP_BUFFER_SIZE = 2048;

     int DEFAULT_BUFFER_SIZE = 8192;

     int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    byte[] readAllBytes(InputStream in) throws IOException;
}
