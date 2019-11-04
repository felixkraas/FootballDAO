package dataLayer.dataAccessObjects;

import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;
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
     * returns the first trainer in the xml file
     *
     * @return if the xml file isn't empty: the first trainer, if empty: null
     * @throws NoTrainerFoundException
     */
    ITrainer first() throws NoTrainerFoundException;

    /**
     * returns the trainer that comes next after the given trainer
     *
     * @param trainer, the current trainer
     * @return Itrainer, the trainer after the current one
     * @throws NoNextTrainerFoundException when no Trainer was found
     */
    ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException;

    /**
     * returns the trainer that comes before the given trainer
     *
     * @param trainer, the current trainer
     * @return Itrainer, the trainer before the current one
     * @throws NoPreviousTrainerFoundException when no Trainer was found
     */
    ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException;

    /**
     * returns the last trainer in the xml file
     *
     * @return if the xml file isn't empty: the last trainer, if empty: null
     * @throws NoTrainerFoundException
     */
    ITrainer last() throws NoTrainerFoundException;

    /**
     * saves the given trainer in the xml file/deletes the trainer with the same ID to avoid
     * having the same trainer with the same ID twice
     *
     * @param trainer, the trainer that should be saved
     */
    void save(ITrainer trainer);

    /**
     * select, reads and returns a List of all Trainers in the xml file
     *
     * @return list of all trainers in the xml file
     * NO NoTrainerFoundException because it's also used when saving a Trainer and is actually
     * desired to behave this way
     */
    List<ITrainer> select() throws NoTrainerFoundException;

    /**
     * selects the trainer with the given ID from the xml file and returns it
     *
     * @param id, the ID you are looking for (Star Wars reference)
     * @return ITrainer, the trainer with the given ID
     * @throws NoTrainerFoundException
     */
    ITrainer select(int id) throws NoTrainerFoundException;
}
