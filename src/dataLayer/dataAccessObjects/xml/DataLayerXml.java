package dataLayer.dataAccessObjects.xml;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

public class DataLayerXml implements IDataLayer {

    private ITrainerDao trainerDao;

    public DataLayerXml() {
        this.trainerDao = new TrainerDaoXml();
    }

    @Override
    public ITrainerDao getTrainerDao() {
        return trainerDao;
    }

}
