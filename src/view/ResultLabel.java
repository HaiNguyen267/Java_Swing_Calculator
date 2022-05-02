package view;

import javax.swing.*;
import java.awt.*;


public class ResultLabel extends JLabel {
    private static ResultLabel resultLabel;

    private String resultString;

    private ResultLabel() {
        super("0");
        resultString = "0";
        setName("view.ResultLabel");
        setBackground(Color.WHITE);
        setOpaque(true);
        setFont(new Font("monospaced", Font.BOLD, 30));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setBounds(5,0,280, 50);
    }

    public void displayEquationResult(String text) {
        if (isErrorMessage(text)) {
            displayEquationError(text);
        } else {
            if (hasSomethingNewToDiplay(text)) {
                resultString = text; //store the result string for later use
                setText(text);
            }
        }
    }

    public void displayEquationError(String errorMsg) {
        // if there is no error to display, display the result of previous equation
        if (errorMsg == null) {
            displayResultOfPreviousEquation();
        } else {
            setText(errorMsg);
        }
    }

    public static ResultLabel getInstance() {
        if (resultLabel == null) {
            resultLabel = new ResultLabel();
        }

        return resultLabel;
    }
    private void displayResultOfPreviousEquation() {
        if (hasSomethingNewToDiplay(resultString)) {
            setText(resultString);
        }
    }

    private boolean hasSomethingNewToDiplay(String textToDisplay) {
        // to avoid rendering the same text again
        return !getText().equals(textToDisplay);
    }

    private boolean isErrorMessage(String text) {
        return text == null || !isNumber(text);
    }

    private boolean isNumber(String num) {
        try {
            double d = Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
