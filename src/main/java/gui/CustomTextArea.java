package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import static gui.LayoutConstants.TEXT_AREA_HEIGHT;
import static gui.LayoutConstants.TEXT_AREA_WIDTH;

public class CustomTextArea extends JTextArea {
    public CustomTextArea(
            String text,
            int height,
            int width,
            int row,
            int col,
            JPanel panel,
            GridBagConstraints constraints
    ) {
        super(text, height, width);
        setFont(new Font("Dialog", Font.BOLD, 12));
        setBackground(CustomColors.TEXT_BACKGROUND_COLOR);
        Border margins = BorderFactory.createEmptyBorder(
                LayoutConstants.TOP_MARGIN,
                LayoutConstants.LEFT_MARGIN,
                0,
                0
        );
        Border border = BorderFactory.createLineBorder(CustomColors.BORDER_COLOR, 2);
        setBorder(new CompoundBorder(margins, border));
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = TEXT_AREA_WIDTH;
        constraints.gridheight = TEXT_AREA_HEIGHT;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(this, constraints);
    }
}
