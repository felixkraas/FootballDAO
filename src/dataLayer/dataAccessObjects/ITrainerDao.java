package dataLayer.dataAccessObjects;

import businessObjects.ITrainer;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.util.List;

public interface ITrainerDao {
    ITrainer create();

    void delete(ITrainer trainer);

    ITrainer first() throws NoTrainerFoundException;

    ITrainer next() throws NoNextTrainerFoundException;

    ITrainer previous() throws NoPreviousTrainerFoundException;

    ITrainer last() throws NoTrainerFoundException;

    void save(ITrainer trainer);

    List<ITrainer> select() throws NoTrainerFoundException;

    ITrainer select(int id) throws NoTrainerFoundException;
}
