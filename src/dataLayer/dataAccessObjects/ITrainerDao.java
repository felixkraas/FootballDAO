package dataLayer.dataAccessObjects;

import businessObjects.ITrainer;
import businessObjects.Trainer;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.util.List;

public interface ITrainerDao {
    default ITrainer create() {
        return new Trainer();
    }

    /**
     * Delete a Trainer from the datasource
     *
     * @param trainer The trainer to delete
     */
    void delete(ITrainer trainer) throws NoTrainerFoundException;

    /**
     * delete
     * @return
     * @throws NoTrainerFoundException
     */
    ITrainer first() throws NoTrainerFoundException;

    ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException;

    ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException;

    ITrainer last() throws NoTrainerFoundException;

    void save(ITrainer trainer);

    List<ITrainer> select() throws NoTrainerFoundException;

    ITrainer select(int id) throws NoTrainerFoundException;
}
