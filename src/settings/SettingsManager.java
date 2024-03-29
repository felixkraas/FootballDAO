package settings;

public class SettingsManager {

    private static SettingsManager SMInstance;
    private PersistenceSettings persistenceSettings;


    private SettingsManager() {

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

    public void setPersistenceSettings(PersistenceSettings settings) {
        this.persistenceSettings = settings;
    }

    private PersistenceSettings readPersistenceSettings() {
        return persistenceSettings;

    }
}
