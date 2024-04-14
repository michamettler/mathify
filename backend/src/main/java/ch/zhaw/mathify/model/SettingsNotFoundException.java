package ch.zhaw.mathify.model;

/**
 * Exception that is thrown when the settings.json file is not found
 */
public class SettingsNotFoundException extends Exception {
    public SettingsNotFoundException(String message) {
        super(message);
    }
}
