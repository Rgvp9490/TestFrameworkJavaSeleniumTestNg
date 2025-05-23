package framework.reader;

import framework.runner.TestRunner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class to read and extract test configuration data from Config.json.
 */
public class jsonFileReader {

    private static final String CONFIG_PATH = "src/config/config.json";
    private final JSONObject configRoot;

    public jsonFileReader() {
        this.configRoot = loadConfig();
    }

    private JSONObject loadConfig() {
        try {
            Path path = Paths.get(CONFIG_PATH).toAbsolutePath();
            String content = Files.readString(path);
            return new JSONObject(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file: " + CONFIG_PATH, e);
        }
    }

    public String getRunConfig() {
        return configRoot.getString("run");
    }

    public JSONObject getConfig(String configName) {
        return configRoot.getJSONObject("config").getJSONObject(configName);
    }

    public String getPlatform(String configName) {
        return getConfig(configName).optString("platform", "web");
    }

    public String getEnv(String configName) {
        return getConfig(configName).optString("env", "");
    }

    public boolean isGrid(String configName) {
        return getConfig(configName).optBoolean("isGrid", false);
    }

    public String getBrowser(String configName) {
        return getConfig(configName).optString("browser", "chrome");
    }

    public JSONObject getCapabilities(String configName) {
        return getConfig(configName).getJSONObject("capabilities");
    }

    public JSONObject getLambdaTestOptions(String configName) {
        return getConfig(configName).getJSONObject("lambdaTestOption");
    }

    public JSONArray getSuites(String configName) {
        JSONObject configObj = getConfig(configName);
        return configObj.getJSONArray("testNGsuite");
    }

    public JSONObject getAPIConfig(String configName) {
        return getConfig(configName).getJSONObject("apiTestConfig");
    }

    public boolean isAPITestConfigEnable(String configName) {
        return getAPIConfig(configName).optBoolean("enable", false);
    }

    public String getAPIEnv(String configName) {
        return getAPIConfig(configName).optString("env", "");
    }

    public String getAPIEnvDirect(String configName) {
        return getConfig(configName).optString("env", "");
    }
}
