package model;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;


public class Processor {
    private static Processor processor = null;
    private static final Color RED = new Color(255, 60, 60);
    private static final Color GREEN = new Color(49, 159, 34);

    private String resultString;
    private StringBuilder equationString;

    private Processor() {
        this.equationString = new StringBuilder();
        this.resultString = "";
    }

    public static Processor getInstance() {
        if (processor == null) {
            processor = new Processor();
        }

        return processor;
    }

    public void appendSymbolToEquation(String symbol) {
        if (equationIsAppendable()) {
            equationString.append(symbol);
        }
    }

    private boolean equationIsAppendable() {
        return !equationStartsWithOperator();
    }

    public void deleteLastSymbolOfEquation() {
        if (equationString.length() > 0) {
            equationString.deleteCharAt(equationString.length() - 1);
        }
    }

    public void clearEquation() {
        equationString = new StringBuilder();
    }

    public Equation getEquation() {
        Color color = GREEN;

        if (getEquationError() != null) {
            color = RED;
        }

        return new Equation(equationString.toString(), color);
    }

    public String getEquationResult() {

        // first if the equation contains error or not
        String error = getEquationError();
        if (error != null) {
            return error;
        } else if (equationEndsWithOperator()) {
            // check 1 more error if the equation ends with operator, after the user click "=" button
            error = Error.ENDS_WITH_OPERATOR.toString();
            return error;
        } else {
            // if there is no error, solve the equation and return the resutl
            String result = solveEquation();
            return result;
        }
    }

    public String getEquationError() {
        // check 2 possible errors in the equations, start with operator and contain zero division
        String error = null;

        if (equationStartsWithOperator()) {
            error = Error.STARTS_WITH_OPERATOR.toString();
        } else if (equationContainsZeroDivision()) {
            error = Error.CONTAINS_ZERO_DIVISION.toString();
        }

        return error;
    }

    private boolean equationContainsZeroDivision() {
        return equationString.toString().matches(".*÷0[^.].*");
    }

    private boolean equationStartsWithOperator() {
        return equationString.toString().matches("^[+\\-x÷]");
    }

    private boolean equationEndsWithOperator() {
        return equationString.toString().matches(".*[=\\-x÷]$");
    }

    private String solveEquation() {
        Deque<String> postfix = convertStringToPostfix(equationString.toString());
        BigDecimal result = evaluatePostfix(postfix);
        return result.toString();
    }

    private Deque<String> convertStringToPostfix(String str) {
        Deque<String> infix = convertStringToInfix(str);
        Deque<String> postfix = convertInfixToPostfix(infix);
        return postfix;
    }

    private Deque<String> convertStringToInfix(String str) {
        String[] numbers = str.split("[+\\-x÷]"); // extract numbers from the equation string
        String[] operators = str.replaceAll("[^+\\-x÷]", "").split(""); // extract operators from the equation string

        int totalElement = numbers.length + operators.length;
        int count = 0, index1 = 0, index2 = 0;

        Deque<String> infix = new ArrayDeque<>();

        // enqueue the numbers and operators to the queue alternately

        while (count < totalElement) {
            if (count % 2 == 0) {
                infix.offerFirst(numbers[index1]);
                index1++;
            } else {
                infix.offerFirst(operators[index2]);
                index2++;
            }

            count++;
        }

        return infix;
    }

    private Deque<String> convertInfixToPostfix(Deque<String> infix) {
        Deque<String> postfix = new ArrayDeque<>();
        Deque<String> stack = new ArrayDeque<>();

        while (!infix.isEmpty()) {
            String element = infix.pollLast();
            // if the element is a number, then enqueue it to the postfix
            if (isNumber(element)) {
                postfix.offerFirst(element);
            } else {

                // if the element is an operator, enqueue it the stack
                // but an operator can only stay above other operators in the stack if it has higher priority (lower prec value)
                // pop all the operators that the elemetn cannot stay above and enqueue them to the post fix
                while (!stack.isEmpty() && prec(element) >= prec(stack.peekFirst())) {
                    postfix.offerFirst(stack.pollFirst());
                }

                // now push the element to the stack
                stack.offerFirst(element);
            }

        }
        // enqueue all the rest operators in the stack to the postfix
        while (!stack.isEmpty()) {
            postfix.offerFirst(stack.pollFirst());
        }

        return postfix;
    }

    private int prec(String operator) {
        if (operator.equals("x") || operator.equals("÷")) {
            return 1;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 2;
        }

        return 0;
    }

    private boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private BigDecimal evaluatePostfix(Deque<String> postfix) {
        Deque<BigDecimal> resultStack = new ArrayDeque<>();

        while (!postfix.isEmpty()) {
            String element = postfix.pollLast();

            // if the element is a number, push it the the stack for later calculation
            if (isNumber(element)) {
                resultStack.offerFirst(new BigDecimal(element));
            } else {
                // if the element is an operator, pop out 2 numbers from the stack and do calculation
                BigDecimal secondNum = resultStack.pollFirst();
                BigDecimal firstNum = resultStack.pollFirst();
                BigDecimal result = calculate(firstNum, secondNum, element);

                // push the result back to the stack
                resultStack.offerFirst(result);
            }
        }

        // after all, the stack only contains 1 element, and it's the final result
        return resultStack.getFirst();
    }

    private BigDecimal calculate(BigDecimal firstNum, BigDecimal secondNum, String operator) {

        BigDecimal result = new BigDecimal("0");

        switch (operator) {
            case "+": result = firstNum.add(secondNum); break;
            case "-": result = firstNum.subtract(secondNum); break;
            case "x": result = firstNum.multiply(secondNum); break;
            case "÷": result = firstNum.divide(secondNum, 5, RoundingMode.HALF_UP); break;
        }

        result.stripTrailingZeros();
        return result;
    }

}
