package Textures;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class that allows transparent reading of files from
 * the current working directory or from the classpath.
 * @author Pepijn Van Eeckhoudt
 */
public class ResourceRetriever {
    @SuppressWarnings("deprecation")
    public static URL getResource(final String filename) throws IOException {
        URL url = ClassLoader.getSystemResource(filename);
        if (url == null) {
            return new URL("file", "localhost", filename);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(final String filename) throws IOException {
        // Try to load resource from jar
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        if (stream == null) {
            return new FileInputStream(filename);
        } else {
            return stream;
        }
    }
}
