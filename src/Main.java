import exceptions.NoTrainerFoundException;
import presentationLayer.MainFrame;
import settings.PersistenceSettings;
import settings.SettingsManager;

public class Main {

    public static void main(String[] args) throws NoTrainerFoundException {
        PersistenceSettings settings = new PersistenceSettings();
        settings.setPersistenceType(args[0]);
        SettingsManager.getInstance().setPersistenceSettings(settings);
        MainFrame frame = new MainFrame();
        frame.init();
    }

}
