package view;

import javax.swing.*;
import java.awt.*;

public class EquationLabel extends JLabel {
    private static final Color RED = new Color(255, 60, 60);
    private static final Color GREEN = new Color(49, 159, 34);
    private static EquationLabel equationLabel = null;
    private EquationLabel() {
        super();
        setName("view.EquationLabel");
        setBackground(Color.WHITE);
        setOpaque(true);
        setForeground(GREEN);
        setBorder(null);
        setHorizontalAlignment(SwingConstants.RIGHT);
        setBounds(5, 50, 280, 50);
    }

    public static EquationLabel getInstance() {
        if (equationLabel == null) {
            equationLabel = new EquationLabel();
        }

        return equationLabel;
    }

    public void displayEquation(String text, Color color) {
        if (hasSomeThingNewToDisplay(text)) {
            setText(text);
            setForeground(color);
        }
    }

    private boolean hasSomeThingNewToDisplay(String text) {
        return !getText().equals(text);
    }
}
