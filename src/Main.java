import exceptions.NoTrainerFoundException;
import presentationLayer.MainFrame;

public class Main {

    public static void main(String[] args) throws NoTrainerFoundException {
        MainFrame frame = new MainFrame();
        frame.init();
    }

}
