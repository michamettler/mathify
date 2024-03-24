package ch.zhaw.mathify;

import ch.zhaw.mathify.controller.Router;
import ch.zhaw.mathify.model.Settings;
import ch.zhaw.mathify.model.SettingsNotFoundException;
import ch.zhaw.mathify.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final File SETTINGS_FILE = new File(Objects.requireNonNull(App.class.getClassLoader().getResource("settings.json")).getFile());
    private static Settings settings;

    /**
     * Starts the application by reading the settings.json and starting the Router
     */
    public static void run() throws SettingsNotFoundException {
        LOG.info("Starting app...");

        if (!SETTINGS_FILE.exists()) {
            LOG.error("settings.json not found! Make sure to provide a settings.json in the resources folder.");
            throw new SettingsNotFoundException("settings.json not found! Make sure to provide a settings.json in the resources folder.");
        }
        LOG.info("settings.json found!");
        try {
            settings = JsonMapper.readSettingsFromJson(SETTINGS_FILE);
        } catch (IOException e) {
            LOG.error("Could not read settings.json!", e);
            throw new SettingsNotFoundException("Could not read settings.json!");
        }
        (new Router()).startApplication();

    }

    public static Settings getSettings() {
        return settings;
    }

    public static void main(String[] args) throws SettingsNotFoundException {
        App.run();
    }
}
