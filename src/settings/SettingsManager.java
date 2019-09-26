package settings;

public class SettingsManager {

    private static SettingsManager SMInstance;
    private PersistenceSettings persistenceSettings;


    private void SettingsManager() {

    }

    public static SettingsManager getInstance() {
        if (SMInstance == null) {
            SMInstance = new SettingsManager();
        }
        return SMInstance;

    }

    public PersistenceSettings getPersistenceSettings() {
        return persistenceSettings;

    }

    private PersistenceSettings readPersistenceSettings() {
        return persistenceSettings;

    }
}
