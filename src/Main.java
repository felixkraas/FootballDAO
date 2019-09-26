import businessObjects.Trainer;
import dataLayer.DataLayerManager;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoTrainerFoundException;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, NoTrainerFoundException {
        ITrainerDao dao = DataLayerManager.getInstance().getDataLayer().getTrainerDao();
        Trainer t = (Trainer) dao.create();
        t.setName("Test13");
        dao.save(t);
        dao.select().forEach(trainer -> System.out.println(trainer.getName() + " | " + trainer.getId()));
    }

}
