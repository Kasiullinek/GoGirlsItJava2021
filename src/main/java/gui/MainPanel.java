package gui;

import Simulation.JsonParser;
import Simulation.IWorldMap;
import Simulation.Simulation;
import Simulation.SimulationParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    private MapPanel mapPanel;
    private Timer timer;

    private final GridBagConstraints constraints = new GridBagConstraints();
    private final CustomButton startBtn;
    private final CustomButton stopBtn;
    private final CustomButton continueBtn;
    private final CustomButton dumpToJsonBtn;
    private final CustomTextArea statsInput;

    private int nextRow = 0;

    private final static int DEFAULT_COL = 0;
    private final static int MAP_ROW = 0;
    private final static int MAP_COL = 2;

    private final static String STATS_FILE = "stats.json";

    private void initLayout() {
        setLayout(new GridBagLayout());
        setBackground(CustomColors.BACKGROUND_COLOR);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(
                LayoutConstants.TOP_MARGIN,
                LayoutConstants.LEFT_MARGIN,
                LayoutConstants.BOTTOM_MARGIN,
                LayoutConstants.RIGHT_MARGIN
        );
    }

    public MainPanel() {
        initLayout();

        startBtn = createButton("Start new game");
        startBtn.addActionListener((ActionEvent e) -> startApp());

        stopBtn = createButton("Stop game");
        stopBtn.addActionListener((ActionEvent e) -> stopApp());

        continueBtn = createButton("Continue game");
        continueBtn.addActionListener((ActionEvent e) -> continueApp());

        dumpToJsonBtn = createButton("Dump to JSON");
        dumpToJsonBtn.addActionListener((ActionEvent e) -> dumpStatistics());

        SimulationParams.getParamsMap().forEach((fieldName, value) ->
                new CustomTextField(fieldName, Integer.toString(value), nextRow++, DEFAULT_COL, constraints, this)
        );

        statsInput = new CustomTextArea(
                "Press 'start new game'\nto reset game",
                LayoutConstants.TEXT_AREA_HEIGHT,
                LayoutConstants.TEXT_AREA_WIDTH,
                nextRow++,
                DEFAULT_COL,
                this,
                constraints
        );

        mapPanel = new MapPanel(Simulation.getWorldMap());
        addMapPanel();
        timer = new Timer(1000 / SimulationParams.getField("speed"), this);
        timer.start();
    }

    private CustomButton createButton(String text) {
        return new CustomButton(text, this, constraints, nextRow++, DEFAULT_COL, true);
    }

    private void addMapPanel() {
        constraints.gridx = MAP_COL;
        constraints.gridy = MAP_ROW;
        constraints.gridwidth = 1;
        constraints.gridheight = nextRow + 1;
        constraints.ipadx = LayoutConstants.MAP_SCALE * Simulation.getWorldMap().getWidth();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(mapPanel, constraints);
    }

    private void rerenderMap() {
        Simulation.simulateDay();
        mapPanel.repaint();
        statsInput.setText(Simulation.getWorldMap().getStatistics().toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Simulation.getWorldMap().getAnimalsLocations().isEmpty()) {
            timer.stop();
            return;
        }
        rerenderMap();
    }

    private void startApp() {
        if (timer.isRunning()) timer.stop();
        Simulation.setSimulation();
        mapPanel.repaint();
        timer.start();
    }

    private void stopApp() {
        if (timer.isRunning()) timer.stop();
    }

    private void continueApp() {
        if (!timer.isRunning()) timer.start();
    }

    private void dumpStatistics() {
        JsonParser.dumpStatisticsToJsonFile(STATS_FILE, Simulation.getWorldMap().getStatistics());
    }
}
