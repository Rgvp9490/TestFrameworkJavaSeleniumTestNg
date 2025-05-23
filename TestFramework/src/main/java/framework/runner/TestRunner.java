package framework.runner;

import com.beust.jcommander.JCommander;
import framework.reader.jsonFileReader;
import org.json.JSONArray;
import org.testng.TestNG;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static String currentConfig = "";

    private String getConfigFromCommandLine(String[] args) {
        framework.reader.CommandlineOption option = new framework.reader.CommandlineOption();
        try {
            JCommander.newBuilder().addObject(option).build().parse(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return option.getConfigName();
    }

    public void start(String[] args) {
        TestNG testng = new TestNG();
        jsonFileReader config = new jsonFileReader();

        try {
            String configToRun = getConfigFromCommandLine(args);
            currentConfig = (configToRun != null && !configToRun.trim().isEmpty())
                    ? configToRun
                    : config.getRunConfig();

            System.out.println("Running Config: " + currentConfig);

            JSONArray suiteList = config.getSuites(currentConfig);
            if (suiteList.length() == 0) {
                throw new RuntimeException("No test suites found in configuration.");
            }

            String pathSeparator = FileSystems.getDefault().getSeparator();
            String suiteDirectory = "." + pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "java" + pathSeparator + "suites" + pathSeparator;
            List<String> testSuites = new ArrayList<>();

            for (Object suiteName : suiteList) {
                testSuites.add(new File(suiteDirectory + suiteName).getAbsolutePath());
            }

            testng.setTestSuites(testSuites);
            testng.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TestRunner().start(args);
    }
}
