package dataLayer;

import dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;

public class DataLayerManager {

    private static DataLayerManager instance;
    private IDataLayer dataLayer;

    private DataLayerManager() {
        switch ("sqlite") {
            case "sqlite":
                dataLayer = new DataLayerSqlite();
                break;
            case "xml":
                //TODO XML einfügen.
                break;
            default:
                dataLayer = null;
        }
    }

    public static DataLayerManager getInstance() {
        if (instance == null) {
            instance = new DataLayerManager();
        }
        return instance;
    }

    public IDataLayer getDataLayer() {
        return dataLayer;
    }
}
