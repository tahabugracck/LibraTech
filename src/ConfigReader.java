import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class ConfigReader {
    private static final String CONFIG_FILE_PATH = "config.properties";

    public static Properties readConfig() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }
}