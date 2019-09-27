package dataLayer.dataAccessObjects.xml;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

/**
 * @author Jannis
 * Xml Implementation of DataLayer
 */
public class DataLayerXml implements IDataLayer {

    private ITrainerDao trainerDao;

    /**
     * Constructor, creates a new TrainerDaoXml
     */
    public DataLayerXml() {
        this.trainerDao = new TrainerDaoXml();
    }

    @Override
    public ITrainerDao getTrainerDao() {
        return trainerDao;
    }

}
