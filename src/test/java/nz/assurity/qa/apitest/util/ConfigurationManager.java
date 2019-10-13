package nz.assurity.qa.apitest.util;

import nz.assurity.qa.apitest.Constants;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages configuration value reading from properties file.
 *
 * @author Nilmi
 */
public class ConfigurationManager {

    //Configuration keys
    private final static String CONFIG_PROJECT_NAME = "project.name";
    private final static String CONFIG_ENVIRONMENT = "environment";

    private final static String CONFIG_BASE_URL = "base.url";

    private final static String CONFIG_PATH_BASE = "path.base";
    private final static String CONFIG_PATH_OUTPUT = "path.output";
    private final static String CONFIG_PATH_REPORT_CONFIG = "path.report.config";
    private final static String CONFIG_REPORT_FILENAME = "path.report.file.name";


    //Configurations
    public static String PROJECT_NAME;
    public static String ENVIRONMENT;

    public static String BASE_URL;

    public static String BASE_PATH;
    public static String REPORT_FOLDER_NAME;
    public static String REPORT_FILE_NAME;
    public static String REPORT_FOLDER_PATH;
    public static String REPORT_CONFIG_PATH;

    private static Configuration configuration;

    /**
     * Returns current configurations instance.
     * If null initializes the configurations and returns configurations instance.
     *
     * @return Configurations instance
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
            init();
        }
        return configuration;
    }

    /**
     * Initialize configurations
     */
    public static void init() {
        try {
            System.setProperty("java.util.logging.config.file", System.getProperty("log.config.path"));
            String propertyFilePath = System.getProperty("config.path");
            configuration = new Configurations().properties(new File(propertyFilePath));
            printLoadedConfigurations();
            loadDefaultConfigurations();
        } catch (ConfigurationException e) {
            Logger.getLogger(Constants.LOG_CODE).log(Level.SEVERE, "Error in loading configurations. " +
                    "Please make sure that the -Dtestbase.config parameters is set to a valid property file.");
        }
    }

    /**
     * Helper method, prints currently available configuration key value pairs.
     */
    private static void printLoadedConfigurations() {
        Iterator<String> keys = configuration.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            Logger.getLogger(Constants.LOG_CODE).log(Level.INFO,
                    String.format("%s: %s", key, configuration.getString(key)));
        }
    }

    /**
     * Helper method, load default configurations from property file.
     */
    private static void loadDefaultConfigurations() {
        PROJECT_NAME = configuration.getString(CONFIG_PROJECT_NAME);
        ENVIRONMENT = configuration.getString(CONFIG_ENVIRONMENT);

        BASE_URL = configuration.getString(CONFIG_BASE_URL);
        BASE_PATH = configuration.getString(CONFIG_PATH_BASE);

        REPORT_CONFIG_PATH = toAbsolutePath(configuration.getString(CONFIG_PATH_REPORT_CONFIG));
        REPORT_FOLDER_NAME = FileHelper.getTimestampedName(null);

        REPORT_FOLDER_PATH = toAbsolutePath(configuration.getString(CONFIG_PATH_OUTPUT)) + REPORT_FOLDER_NAME;
        FileHelper.createDirs(new File(REPORT_FOLDER_PATH));
        REPORT_FILE_NAME = configuration.getString(CONFIG_REPORT_FILENAME);


    }

    /**
     * Check whether the given folder path is an absolute path. If the path is not an absolute path
     * prefix the BASE_PATH
     *
     * @param path path as a String
     * @return absolute path
     */
    private static String toAbsolutePath(String path) {
        return (new File(path).isAbsolute() ? "" : BASE_PATH) + path;
    }

    /**
     * Reads custom configurations. Can be used to read project specific configurations.
     *
     * @param configurationKey Configuration key
     * @return Configuration value
     */
    public static String readCustomConfig(String configurationKey) {
        return configuration.getString(configurationKey);
    }

}
