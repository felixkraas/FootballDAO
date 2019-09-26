package dataLayer;

import dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;
import dataLayer.dataAccessObjects.xml.DataLayerXml;

public class DataLayerManager {

    private static DataLayerManager instance;
    private IDataLayer dataLayer;

    private DataLayerManager() {
        switch ("xml") {
            case "sqlite":
                dataLayer = new DataLayerSqlite();
                break;
            case "xml":
                dataLayer = new DataLayerXml();
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
