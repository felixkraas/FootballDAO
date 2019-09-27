package dataLayer.dataAccessObjects.xml;

import businessObjects.ITrainer;
import businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jannis
 * Xml implementation of TrainerDao
 */
public class TrainerDaoXml implements ITrainerDao {

    /**
     * creates a new empty trainer, but sets the ID itself so it'll be unique
     *
     * @return an empty trainer with an unique ID set (unless the existing Xml file is not sorted)
     */
    @Override
    public ITrainer create() {
        Trainer trainer = new Trainer();
        int highestID = 0;
        if (!select().isEmpty()) {
            List<ITrainer> trainers = select();

            for (ITrainer t : trainers) {
                if (highestID < t.getId()) {
                    highestID = t.getId();
                }
            }
        }
        trainer.setId(highestID + 1);
        return trainer;
    }

    /**
     * deletes the given trainer
     *
     * @param trainer, the trainer that should be deleted
     * @throws NoTrainerFoundException if the trainer wasn't found
     */
    @Override
    public void delete(ITrainer trainer) throws NoTrainerFoundException {
        /**
         * gets all the trainers in the xml file and adds them to a list, then
         * looks if the given trainer is in the list, then writes the new list
         * to the xml file in xml format (thus without the deleted trainer)
         */
        List<ITrainer> trainers = select();
        if (trainers.contains(trainer)) {
            trainers.remove(trainers.lastIndexOf(trainer));

            /*
             * overwrites the whole xml file with the new list
             */
            try {

                File file = new File("./trainers.xml");

                FileWriter fw = new FileWriter(file, false);
                fw.write("<TRAINERS>\n");

                for (ITrainer t : trainers) {
                    fw.write("\t<TRAINER id=" + t.getId() + ">\n");
                    fw.write("\t\t<NAME>" + t.getName() + "</NAME>\n");
                    fw.write("\t\t<AGE>" + t.getAlter() + "</AGE>\n");
                    fw.write("\t\t<EXPERIENCE>" + t.getErfahrung() + "</EXPERIENCE>\n");
                    fw.write("\t</TRAINER>\n");
                }

                fw.write("</TRAINERS>");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoTrainerFoundException("The given trainer doesn't exist");
        }
    }

    /**
     * returns the first trainer in the xml file
     *
     * @return if the xml file isn't empty: the first trainer, if empty: null
     * @throws NoTrainerFoundException
     */
    @Override
    public ITrainer first() throws NoTrainerFoundException {
        List<ITrainer> trainers = select();
        if (trainers.size() > 0) {
            ITrainer trainer = trainers.get(0);
            return trainer;
        } else {
            throw new NoTrainerFoundException("The given trainer doesn't exist");
        }
    }

    /**
     * returns the last trainer in the xml file
     *
     * @return if the xml file isn't empty: the last trainer, if empty: null
     * @throws NoTrainerFoundException
     */
    @Override
    public ITrainer last() throws NoTrainerFoundException {
        List<ITrainer> trainers = select();
        if (trainers.size() > 0) {
            return trainers.get(trainers.size() - 1);
        } else {
            throw new NoTrainerFoundException("The given trainer doesn't exist");
        }
    }

    /**
     * returns the trainer that comes next after the given trainer
     *
     * @param trainer, the current trainer
     * @return Itrainer, the trainer after the current one
     * @throws NoNextTrainerFoundException when no Trainer was found
     */
    @Override
    public ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException {
        List<ITrainer> trainers = select();
        if (trainers.stream().filter(o -> o.getId() == (trainer.getId())).findFirst().isPresent() &&
                trainers.lastIndexOf(trainers.stream().filter(o -> o.getId() == (trainer.getId())).findFirst().get()) != trainers.size() - 1) {
            return trainers.get(trainers.lastIndexOf(trainers.stream().filter(o -> o.getId() == (trainer.getId())).findFirst().get()) + 1);
        } else {
            throw new NoNextTrainerFoundException("No next trainer found :(");
        }
    }

    /**
     * returns the trainer that comes before the given trainer
     *
     * @param trainer, the current trainer
     * @return Itrainer, the trainer before the current one
     * @throws NoPreviousTrainerFoundException when no Trainer was found
     */
    @Override
    public ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException {
        List<ITrainer> trainers = select();
        if (trainers.lastIndexOf(trainers.stream().filter(o -> o.getId() == (trainer.getId())).findFirst().get()) >= 1) {
            return trainers.get(trainers.lastIndexOf(trainers.stream().filter(o -> o.getId() == (trainer.getId())).findFirst().get()) - 1);
        } else {
            throw new NoPreviousTrainerFoundException("No previous trainer found :(");
        }
    }

    /**
     * saves the given trainer in the xml file/deletes the trainer with the same ID to avoid
     * having the same trainer with the same ID twice
     *
     * @param trainer, the trainer that should be saved
     */
    @Override
    public void save(ITrainer trainer) {
        List<ITrainer> trainers = select();

        /*
         * other option: use select(int id) -> shorter
         * this way should be faster since we already have the list and won't read through the xml file again
         */
        for (ITrainer t : trainers) {
            if (t.getId() == trainer.getId()) {
                try {
                    delete(t);
                } catch (NoTrainerFoundException e) {
                    /*empty catch statement because it shouldn't go there and I don't want
                     * to add a throws NoTrainerFoundException to save :)
                     */
                }
            }
        }
        trainers.add(trainer);

        /*
         * overwrites the whole xml file with the new list
         */
        try {
            File file = new File("./trainers.xml");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, false);
            fw.write("<TRAINERS>\n");

            for (ITrainer t : trainers) {
                fw.write("\t<TRAINER id=" + t.getId() + ">\n");
                fw.write("\t\t<NAME>" + t.getName() + "</NAME>\n");
                fw.write("\t\t<AGE>" + t.getAlter() + "</AGE>\n");
                fw.write("\t\t<EXPERIENCE>" + t.getErfahrung() + "</EXPERIENCE>\n");
                fw.write("\t</TRAINER>\n");
            }

            fw.write("</TRAINERS>");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * select, reads and returns a List of all Trainers in the xml file
     *
     * @return list of all trainers in the xml file
     * NO NoTrainerFoundException because it's also used when saving a Trainer and is actually
     * desired to behave this way
     */
    @Override
    public List<ITrainer> select() {
        List<ITrainer> trainers = new ArrayList<>();

        try {
            File file = new File("./trainers.xml");
            if (!file.exists()) {
                return new ArrayList<>();
            }
            /**
             * selfmade, terrible xml parser that only works for this type of document, but it works
             */
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String identifier = "<TRAINER id=";
            String line = bufReader.readLine();

            while (line != null) {
                if (line.contains(identifier)) {
                    Trainer trainer = new Trainer();
                    String[] splittedString = line.split("<TRAINER id=");
                    splittedString = splittedString[1].split(">");
                    trainer.setId(Integer.parseInt(splittedString[0]));
                    line = bufReader.readLine();
                    splittedString = line.split("<NAME>");
                    splittedString = splittedString[1].split("</NAME>");
                    trainer.setName(splittedString[0]);
                    line = bufReader.readLine();
                    splittedString = line.split("<AGE>");
                    splittedString = splittedString[1].split("</AGE>");
                    trainer.setAlter(Integer.parseInt(splittedString[0]));
                    line = bufReader.readLine();
                    splittedString = line.split("<EXPERIENCE>");
                    splittedString = splittedString[1].split("</EXPERIENCE>");
                    trainer.setAlter(Integer.parseInt(splittedString[0]));
                    trainers.add(trainer);
                }
                line = bufReader.readLine();
            }
            bufReader.close();
        } catch (IOException e) {

        }

        return trainers;
    }

    /**
     * selects the trainer with the given ID from the xml file and returns it
     *
     * @param id, the ID you are looking for (Star Wars reference)
     * @return ITrainer, the trainer with the given ID
     * @throws NoTrainerFoundException
     */
    @Override
    public ITrainer select(int id) throws NoTrainerFoundException {
        Trainer trainer = new Trainer();

        try {
            File file = new File("./trainers.xml");
            if (!file.exists()) {
                throw new NoTrainerFoundException("The given trainer couldn't be found anywhere :(");
            }
            /**
             * selfmade, terrible xml parser that only works for this document
             */
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String identifier = "<TRAINER id=" + id + ">";
            String line = bufReader.readLine();
            while (line != null) {
                if (line.contains(identifier)) {
                    String[] splittedString = line.split("<TRAINER id=");
                    splittedString = splittedString[1].split(">");
                    trainer.setId(Integer.parseInt(splittedString[0]));
                    line = bufReader.readLine();
                    splittedString = line.split("<NAME>");
                    splittedString = splittedString[1].split("</NAME>");
                    trainer.setName(splittedString[0]);
                    line = bufReader.readLine();
                    splittedString = line.split("<AGE>");
                    splittedString = splittedString[1].split("</AGE>");
                    trainer.setAlter(Integer.parseInt(splittedString[0]));
                    line = bufReader.readLine();
                    splittedString = line.split("<EXPERIENCE>");
                    splittedString = splittedString[1].split("</EXPERIENCE>");
                    trainer.setAlter(Integer.parseInt(splittedString[0]));
                    bufReader.close();
                    return trainer;
                }
                line = bufReader.readLine();
            }
            bufReader.close();
            throw new NoTrainerFoundException("The given trainer couldn't be found anywhere :(");
        } catch (IOException e) {
            //who doesn't like empty catch blocks? Quality programming right here!
        }

        return trainer;
    }

}
