import businessObjects.Trainer;
import dataLayer.DataLayerManager;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoTrainerFoundException;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, NoTrainerFoundException {
        Trainer t = new Trainer();
        t.setName("Test");
        t.setErfahrung(5);
        t.setAlter(12);
        ITrainerDao dao = DataLayerManager.getInstance().getDataLayer().getTrainerDao();
        //dao.save(t);
        dao.select().forEach(trainer -> System.out.println(trainer.getName()));
    }

}
