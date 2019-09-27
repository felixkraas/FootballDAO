package dataLayer.dataAccessObjects.sqlite;

import businessObjects.ITrainer;
import businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoSqlite implements ITrainerDao {
    private final String CLASSNAME = "org.sqlite.JDBC";
    private String CONNECTION_STRING;
    private Connection conn;

    public TrainerDaoSqlite() {
        try {
            Class.forName(CLASSNAME);
            CONNECTION_STRING = "jdbc:sqlite:Trainer.db3";
            conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ITrainer trainer) {
        String sql = "DELETE FROM trainer WHERE id = ?;";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, trainer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ITrainer first() throws NoTrainerFoundException {
        Trainer t = null;
        String sql = "SELECT * FROM trainer ORDER BY id ASC;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                t = new Trainer();
                t.setId(resultSet.getInt("id"));
                t.setAlter(resultSet.getInt("age"));
                t.setErfahrung(resultSet.getInt("experience"));
                t.setName(resultSet.getString("name"));
            } else {
                throw new NoTrainerFoundException("No Trainer found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException {
        Trainer t = null;
        String sql = "SELECT * FROM trainer WHERE id > ? ORDER BY id ASC;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, trainer.getId());
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                t = new Trainer();
                t.setId(resultSet.getInt("id"));
                t.setAlter(resultSet.getInt("age"));
                t.setErfahrung(resultSet.getInt("experience"));
                t.setName(resultSet.getString("name"));
            } else {
                throw new NoNextTrainerFoundException("No Next Trainer found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException {
        Trainer t = null;
        String sql = "SELECT * FROM trainer WHERE id < ? ORDER BY id ASC;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, trainer.getId());
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                t = new Trainer();
                t.setId(resultSet.getInt("id"));
                t.setAlter(resultSet.getInt("age"));
                t.setErfahrung(resultSet.getInt("experience"));
                t.setName(resultSet.getString("name"));
            } else {
                throw new NoPreviousTrainerFoundException("No Previous Trainer found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public ITrainer last() throws NoTrainerFoundException {
        Trainer t = null;
        String sql = "SELECT * FROM trainer ORDER BY id DESC;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                t = new Trainer();
                t.setId(resultSet.getInt("id"));
                t.setAlter(resultSet.getInt("age"));
                t.setErfahrung(resultSet.getInt("experience"));
                t.setName(resultSet.getString("name"));
            } else {
                throw new NoTrainerFoundException("No Trainer found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void save(ITrainer trainer) {
        try {
            if (select(trainer.getId()) != null) {
                update(trainer);
            } else {
                insert(trainer);
            }
        } catch (NoTrainerFoundException e) {
            e.printStackTrace();
        }
    }

    private void insert(ITrainer trainer) {
        String sql = "INSERT INTO trainer (name, age, experience) VALUES (?, ?, ?);";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, trainer.getName());
            statement.setInt(2, trainer.getAlter());
            statement.setInt(3, trainer.getErfahrung());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(ITrainer trainer) {
        String sql = "UPDATE trainer SET name = ?, age = ?, experience = ? WHERE id = ?;";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, trainer.getName());
            statement.setInt(2, trainer.getAlter());
            statement.setInt(3, trainer.getErfahrung());
            statement.setInt(4, trainer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ITrainer> select() throws NoTrainerFoundException {
        List<ITrainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM trainer;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                Trainer t;
                while (resultSet.next()) {
                    t = new Trainer();
                    t.setId(resultSet.getInt("id"));
                    t.setAlter(resultSet.getInt("age"));
                    t.setErfahrung(resultSet.getInt("experience"));
                    t.setName(resultSet.getString("name"));
                    trainers.add(t);
                }
            } else {
                throw new NoTrainerFoundException("No Trainers found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return trainers;
    }

    @Override
    public ITrainer select(int id) throws NoTrainerFoundException {
        Trainer t = null;
        String sql = "SELECT * FROM trainer WHERE id = ?;";
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                t = new Trainer();
                t.setId(resultSet.getInt("id"));
                t.setAlter(resultSet.getInt("age"));
                t.setErfahrung(resultSet.getInt("experience"));
                t.setName(resultSet.getString("name"));
            } else {
                throw new NoTrainerFoundException("No Trainer found with id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
