package controller;

import model.Equation;
import model.Processor;
import view.EquationLabel;
import view.ResultLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Button extends JButton {
    private static Processor processor;
    private static EquationLabel equationLabel;
    private static ResultLabel resultLabel;
    private String symbol;

    public Button(String symbol, String buttonName) {
        super(symbol);
        this.symbol = symbol;
        setName(buttonName);
        setBackground(Color.WHITE);
        setBorder(null);

        addButtonFunctionality();

    }

    private void addButtonFunctionality() {

        switch (symbol) {
            case "=": addActionListener(this::solveEquation); break;
            case "C": addActionListener(this::clearEquation); break;
            case "Del": addActionListener(this::deleteLastSymbolOfEquation); break;
            default: addActionListener(this::appendSymbolToEquation);
        }
    }

    private void appendSymbolToEquation(ActionEvent actionEvent) {
        processor.appendSymbolToEquation(symbol);

        // get and display the new equation
        Equation equation = processor.getEquation();
        displayEquation(equation);

        // get and display the error of the new equation if any
        String errorMsg = processor.getEquationError();
        displayEquationError(errorMsg);
    }

    private void deleteLastSymbolOfEquation(ActionEvent actionEvent) {
        processor.deleteLastSymbolOfEquation();

        // get and display the new equation
        Equation equation = processor.getEquation();
        displayEquation(equation);

        // get and display error in the new equation if any
        String error = processor.getEquationError();
        displayEquationError(error);
    }

    private void clearEquation(ActionEvent actionEvent) {
        processor.clearEquation();

        // get and display the empty equation
        Equation equation = processor.getEquation();
        displayEquation(equation);

        // the processor will return null because now the equation is empty and there is no error
        String error = processor.getEquationError();
        displayEquationError(error); // null error makes the result label to display the result of previous equation
    }

    private void solveEquation(ActionEvent actionEvent) {
        String result = processor.getEquationResult();
        displayEquationResult(result);
    }

    private void displayEquation(Equation equation) {
        equationLabel.displayEquation(equation.getText(), equation.getColor());
    }

    private void displayEquationError(String errorMsg) {
        resultLabel.displayEquationError(errorMsg);
    }

    private void displayEquationResult(String result) {
        resultLabel.displayEquationResult(result);
    }

    public static void setResultLabel(ResultLabel resultLabelInstance) {
        resultLabel = resultLabelInstance;
    }

    public static void setEquationLabel(EquationLabel equationLabelInstance) {
        equationLabel = equationLabelInstance;
    }

    public static void setProcessor(Processor processorInstance) {
        processor = processorInstance;
    }
}
