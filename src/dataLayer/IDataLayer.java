package dataLayer;

import dataLayer.dataAccessObjects.ITrainerDao;

public interface IDataLayer {
    ITrainerDao getTrainerDao();
}
