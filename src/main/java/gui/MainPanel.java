package gui;

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

    public MainPanel() {
        mapPanel = new MapPanel(Simulation.getWorldMap());
        mapPanel.setPreferredSize(new Dimension(700, 500));
        add(mapPanel);
        timer = new Timer(1000 / SimulationParams.getField("speed"), this);
        timer.start();
    }

    private void rerenderMap() {

        Simulation.simulateDay();
        mapPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Simulation.getWorldMap().getAnimalsLocations().isEmpty()) {
            timer.stop();
            return;
        }
        rerenderMap();
    }
}
