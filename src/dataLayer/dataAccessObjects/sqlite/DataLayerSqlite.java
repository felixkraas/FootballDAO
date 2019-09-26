package dataLayer.dataAccessObjects.sqlite;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

public class DataLayerSqlite implements IDataLayer {

    private ITrainerDao trainerDao;

    public DataLayerSqlite() {
        this.trainerDao = new TrainerDaoSqlite();
    }

    @Override
    public ITrainerDao getTrainerDao() {
        return trainerDao;
    }
}
