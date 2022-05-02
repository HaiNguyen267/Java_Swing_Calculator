package view;

import controller.Button;
import model.Processor;
import view.EquationLabel;
import view.ResultLabel;

import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private JPanel buttonPanel;

    public Calculator() {
        super("view.Calculator");
        setSize(300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initComponent();

        add(ResultLabel.getInstance());
        add(EquationLabel.getInstance());
        add(buttonPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponent() {

        // set components that all the buttons will interact with
        controller.Button.setResultLabel(ResultLabel.getInstance());
        controller.Button.setEquationLabel(EquationLabel.getInstance());
        controller.Button.setProcessor(Processor.getInstance());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,4, 10, 10));

        JButton hidden1 = new JButton();
        hidden1.setVisible(false);
        JButton hidden2 = new JButton();
        hidden2.setVisible(false);

        buttonPanel.add(hidden1);
        buttonPanel.add(hidden2);
        buttonPanel.add(new controller.Button("C", "Clear"));
        buttonPanel.add(new controller.Button("Del", "Delete"));
        buttonPanel.add(new controller.Button("7", "Seven"));
        buttonPanel.add(new controller.Button("8", "Eight"));
        buttonPanel.add(new controller.Button("9", "Nine"));
        buttonPanel.add(new controller.Button("÷", "Divide"));
        buttonPanel.add(new controller.Button("4", "Four"));
        buttonPanel.add(new controller.Button("5", "Five"));
        buttonPanel.add(new controller.Button("6", "Six"));
        buttonPanel.add(new controller.Button("x", "Multiply"));
        buttonPanel.add(new controller.Button("1", "One"));
        buttonPanel.add(new controller.Button("2", "Two"));
        buttonPanel.add(new controller.Button("3", "Three"));
        buttonPanel.add(new controller.Button("+", "Add"));
        buttonPanel.add(new controller.Button(".", "Dot"));
        buttonPanel.add(new controller.Button("0", "Zero"));
        buttonPanel.add(new controller.Button("=", "Equals"));
        buttonPanel.add(new Button("-", "Subtract"));


        buttonPanel.setBounds(5, 150, 280, 200);
    }
}
