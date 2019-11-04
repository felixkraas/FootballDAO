package presentationLayer;

import businessObjects.ITrainer;
import dataLayer.DataLayerManager;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private ITrainer trainer;
    private ArrayList<ITrainer> trainers;
    private ITrainerDao trainerDao;
    private MainFrame frame;

    public void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 638, 456);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(5, 5, 463, 23);
        contentPane.add(toolBar);

        JButton btnCreatenewtrainer = new JButton("New");
        toolBar.add(btnCreatenewtrainer);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut);

        JButton btnChange = new JButton("Change");

        toolBar.add(btnChange);

        JButton btnDeleteTrainer = new JButton("Delete");
        btnDeleteTrainer.setSize(new Dimension(100, 1000000));
        btnDeleteTrainer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    trainerDao.delete(trainer);
                    trainer = trainerDao.first();

                    setupData();
                    updateTrainerData();
                    updateTable();
                } catch (NoTrainerFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Fehler", ex.getLocalizedMessage(),
                            JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        toolBar.add(btnDeleteTrainer);

        Component horizontalGlue = Box.createHorizontalGlue();
        toolBar.add(horizontalGlue);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_1);

        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_2);

        Box horizontalBox = Box.createHorizontalBox();
        toolBar.add(horizontalBox);

        Box horizontalBox_1 = Box.createHorizontalBox();
        horizontalBox_1.setSize(new Dimension(16, 0));
        toolBar.add(horizontalBox_1);

        txtSearch = new JTextField();
        txtSearch.setSize(new Dimension(12, 0));
        txtSearch.setToolTipText("");
        txtSearch.setHorizontalAlignment(SwingConstants.CENTER);

        toolBar.add(txtSearch);
        txtSearch.setColumns(10);


        JButton btnSearch = new JButton("Search");
        toolBar.add(btnSearch);
        btnSearch.addActionListener((event) -> {
            try {
                trainer = trainerDao.select().stream().filter(
                        t -> t.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())
                ).findFirst().get();
                if (trainer == null) {
                    trainer = trainerDao.select().stream().filter(
                            t -> t.getId() == Integer.parseInt(txtSearch.getText())
                    ).findFirst().get();
                }
                updateTrainerData();
            } catch (NoTrainerFoundException e) {
                JOptionPane.showMessageDialog(frame, "Fehler", e.getLocalizedMessage(), JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        });

        JButton btnNext = new JButton("Next");
        btnNext.setBounds(434, 388, 85, 23);
        contentPane.add(btnNext);
        btnNext.addActionListener((event) -> {
            try {
                trainer = trainerDao.next(trainer);
                updateTrainerData();
            } catch (NoNextTrainerFoundException e) {
                JOptionPane.showMessageDialog(frame, "Fehler", e.getLocalizedMessage(), JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        });

        JButton btnPrevious = new JButton("Previous");
        btnPrevious.setBounds(349, 388, 85, 23);
        contentPane.add(btnPrevious);
        btnPrevious.addActionListener((event) -> {
            try {
                trainer = trainerDao.previous(trainer);
                updateTrainerData();
            } catch (NoPreviousTrainerFoundException e) {
                JOptionPane.showMessageDialog(frame, "Fehler", e.getLocalizedMessage(), JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        });

        JButton btnLast = new JButton("Last");
        btnLast.setBounds(145, 388, 85, 23);
        contentPane.add(btnLast);
        btnLast.addActionListener((event) -> {
            try {
                trainer = trainerDao.last();
                updateTrainerData();
            } catch (NoTrainerFoundException e) {
                e.printStackTrace();
            }
        });

        JButton btnFirst = new JButton("First");
        btnFirst.setBounds(60, 388, 85, 23);
        contentPane.add(btnFirst);
        btnFirst.addActionListener((event) -> {
            try {
                trainer = trainerDao.first();
                updateTrainerData();
            } catch (NoTrainerFoundException e) {
                JOptionPane.showMessageDialog(frame, "Fehler", e.getLocalizedMessage(), JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        });

        JLabel lblId = new JLabel("Identification Number");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblId.setHorizontalAlignment(SwingConstants.LEFT);
        lblId.setBounds(304, 130, 136, 26);
        contentPane.add(lblId);

        JLabel lblName = new JLabel("Name");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblName.setBounds(304, 167, 136, 26);
        contentPane.add(lblName);

        JLabel lblAge = new JLabel("Age");
        lblAge.setHorizontalAlignment(SwingConstants.LEFT);
        lblAge.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAge.setBounds(304, 204, 136, 26);
        contentPane.add(lblAge);

        JLabel lblExperience = new JLabel("Experience");
        lblExperience.setHorizontalAlignment(SwingConstants.LEFT);
        lblExperience.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblExperience.setBounds(304, 241, 136, 26);
        contentPane.add(lblExperience);

        table = new JTable();
        table.setBounds(15, 39, 250, 335);
        table.setCellSelectionEnabled(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentPane.add(table);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBounds(432, 134, 170, 20);
        contentPane.add(txtId);
        txtId.setColumns(10);

        txtName = new JTextField();
        txtName.setEditable(false);
        txtName.setColumns(10);
        txtName.setBounds(432, 171, 170, 20);
        contentPane.add(txtName);

        txtAge = new JTextField();
        txtAge.setEditable(false);
        txtAge.setColumns(10);
        txtAge.setBounds(432, 208, 170, 20);
        contentPane.add(txtAge);

        txtExperience = new JTextField();
        txtExperience.setEditable(false);
        txtExperience.setColumns(10);
        txtExperience.setBounds(432, 245, 170, 20);
        contentPane.add(txtExperience);

        JButton btnSave = new JButton("Save");

        btnSave.setVisible(false);
        btnSave.setBounds(434, 322, 85, 23);
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Cancel");

        btnCancel.setVisible(false);
        btnCancel.setBounds(349, 322, 85, 23);
        contentPane.add(btnCancel);



        /* Button Operations*/

        btnCreatenewtrainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnFirst.setVisible(false);
                btnFirst.setVisible(false);
                btnLast.setVisible(false);
                btnNext.setVisible(false);
                btnPrevious.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);

                txtName.setEditable(true);
                txtAge.setEditable(true);
                txtExperience.setEditable(true);

                trainer = trainerDao.create();
                updateTrainerData();
            }
        });

        btnChange.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnFirst.setVisible(false);
                btnLast.setVisible(false);
                btnNext.setVisible(false);
                btnPrevious.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);

                txtName.setEditable(true);
                txtAge.setEditable(true);
                txtExperience.setEditable(true);
            }
        });

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtAge.getText() != null && txtAge.getText().length() != 0 && txtName.getText() != null &&
                        txtName.getText().length() != 0 && txtExperience.getText() != null &&
                        txtExperience.getText().length() != 0) {
                    btnFirst.setVisible(true);
                    btnLast.setVisible(true);
                    btnNext.setVisible(true);
                    btnPrevious.setVisible(true);
                    btnSave.setVisible(false);
                    btnCancel.setVisible(false);

                    txtName.setEditable(false);
                    txtAge.setEditable(false);
                    txtExperience.setEditable(false);

                    trainer = saveTrainerData();
                    trainerDao.save(trainer);
                    updateTrainerData();
                    setupData();

                } else {
                    JOptionPane.showMessageDialog(btnSave, "Please fill out every Attribute!");
                }
            }
        });

        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int input = JOptionPane.showConfirmDialog(contentPane, "Do you really want to discard your changes?", "Really Leave?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
//
                if (input == 1) {

                } else {
                    btnFirst.setVisible(true);
                    btnLast.setVisible(true);
                    btnNext.setVisible(true);
                    btnPrevious.setVisible(true);
                    btnSave.setVisible(false);
                    btnCancel.setVisible(false);

                    txtName.setEditable(false);
                    txtAge.setEditable(false);
                    txtExperience.setEditable(false);

                    txtId.setText(null);
                    txtName.setText(null);
                    txtAge.setText(null);
                    txtExperience.setText(null);
                    updateTrainerData();
                }
            }
        });

        setupData();
    }

    private void setupData() {
        trainerDao = DataLayerManager.getInstance().getDataLayer().getTrainerDao();
        try {
            trainers = (ArrayList<ITrainer>) trainerDao.select();
            updateTable();
        } catch (NoTrainerFoundException e) {
        }
    }

    private void updateTable() {
        String[] col = {"ID", "Name", "Alter", "Erfahrung"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        for (ITrainer t : trainers) {
            Object[] o = new Object[4];
            o[0] = t.getId();
            o[1] = t.getName();
            o[2] = t.getAlter();
            o[3] = t.getErfahrung();
            model.addRow(o);
        }
        table.setModel(model);
        table.doLayout();
    }

    private ITrainer saveTrainerData() {
        ITrainer t = trainer;
        if (t == null) {
            t = trainerDao.create();
        }
        t.setName(txtName.getText());
        t.setAlter(Integer.parseInt(txtAge.getText()));
        t.setErfahrung(Integer.parseInt(txtExperience.getText()));
        return t;
    }

    private void updateTrainerData() {
        txtId.setText(Integer.toString(trainer.getId()));
        txtName.setText(trainer.getName());
        txtAge.setText(Integer.toString(trainer.getAlter()));
        txtExperience.setText(Integer.toString(trainer.getErfahrung()));
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSearch;
    private JTable table;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtExperience;
}
