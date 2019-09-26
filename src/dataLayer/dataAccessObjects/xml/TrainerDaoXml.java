package dataLayer.dataAccessObjects.xml;

import businessObjects.ITrainer;
import businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoXml implements ITrainerDao {


    @Override
    public ITrainer create() {
        Trainer trainer = new Trainer();
        trainer.setId(last().getId() + 1);
        return trainer;
    }

    @Override
    public void delete(ITrainer trainer) {
        List<ITrainer> trainers = select();
        if (trainers.contains(trainer)) {
            trainers.remove(trainers.lastIndexOf(trainer));
        }

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
    }

    @Override
    public ITrainer first() {
        List<ITrainer> trainers = select();
        if (trainers.size() > 0) {
            ITrainer trainer = trainers.get(0);
            return trainer;
        }
        return null;
    }

    @Override
    public ITrainer last() {
        List<ITrainer> trainers = select();
        if (trainers.size() > 0) {
            return trainers.get(trainers.size() - 1);
        }
        return null;
    }

    @Override
    public ITrainer next(ITrainer trainer) {
        List<ITrainer> trainers = select();
        if (trainers.lastIndexOf(trainer) != -1 && trainers.lastIndexOf(trainer) != trainers.size() - 1) {
            return trainers.get(trainers.lastIndexOf(trainer) + 1);
        }
        return null;
    }

    @Override
    public ITrainer previous(ITrainer trainer) {
        List<ITrainer> trainers = select();
        if (trainers.lastIndexOf(trainer) > 1) {
            return trainers.get(trainers.lastIndexOf(trainer) - 1);
        }
        return null;
    }

    @Override
    public void save(ITrainer trainer) {
        List<ITrainer> trainers = select();
        trainers.add(trainer);

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

    @Override
    public List<ITrainer> select() {
        List<ITrainer> trainers = new ArrayList<>();

        try {
            File file = new File("./trainers.xml");
            if (!file.exists()) {
                return new ArrayList<>();
            }
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

    @Override
    public ITrainer select(int id) {
        Trainer trainer = new Trainer();

        try {
            File file = new File("./trainers.xml");
            if (!file.exists()) {
                return null;
            }
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
                    break;
                }
                line = bufReader.readLine();
            }
            bufReader.close();
        } catch (IOException e) {

        }

        return trainer;
    }

}
