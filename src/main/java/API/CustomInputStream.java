package API;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CustomInputStream {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    private int MAX_SKIP_BUFFER_SIZE = 2048;

    private int DEFAULT_BUFFER_SIZE = 8192;

    private int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    private InputStream inputStream;

    public CustomInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] readAllBytes() throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int capacity = buf.length;
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initial buffer size
            while ((n = inputStream.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if the last call to read returned -1, then we're done
            if (n < 0)
                break;

            // need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = capacity << 1;
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
        }
        inputStream.close();
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }
}
