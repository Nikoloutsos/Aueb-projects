
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A workload class that generates requests for items. Takes a workload file as
 * input, during construction of the object
 */
public class WorkloadReader {

    private final BufferedReader reader;
    private boolean closed = false;

    /**
     * Creates a new WorkloadReader.
     *
     * @param file
     *            the path to the file that stores the series of requests
     * @throws FileNotFoundException
     *             if the file is not found in the file-system
     */
    public WorkloadReader(String file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    /**
     * Reads and returns the next item to be requested
     *
     * @return the next item to be requested
     * @throws IOException
     *             if something "bad" happens while reading the text file
     */
    public String nextRequest() throws IOException {
        return reader.readLine();
    }

    /**
     * Close the reader and close the underlying reader
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            reader.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

}
