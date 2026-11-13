import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class Calculator {
    private JPanel Calculator;
    private JTextField txtDisplay;
    private JButton additionButton, subtractButton, multiplyButton, divideButton;
    private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    private JButton decimalButton, posNegButton, percentButton, equalsButton, clearButton, squaredButton;
    private JButton[] otherButtons = {decimalButton, posNegButton, percentButton, equalsButton, clearButton, squaredButton};
    private JButton[] operatorButtons = {additionButton, subtractButton, multiplyButton, divideButton};
    private JButton[] numButtons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button0};


    static ImageIcon icon = new ImageIcon(Objects.requireNonNull(Calculator.class.getClassLoader().getResource("Calculator-icon.png")));


    private String previous, current, operator;
    public Calculator() {
        txtDisplay.setText("0");

        current = "";
        previous = "";

        NumberButtonHandler numHandler = new NumberButtonHandler();
        OperatorButtonHandler operatorHandler = new OperatorButtonHandler();
        OtherButtonHandler otherHandler = new OtherButtonHandler();

        for (JButton numButton : numButtons) {
            numButton.addActionListener(numHandler);
        }
        for (JButton operatorButton : operatorButtons) {
            operatorButton.addActionListener(operatorHandler);
        }
        for (JButton otherButton : otherButtons) {
            otherButton.addActionListener(otherHandler);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().Calculator);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setIconImage(icon.getImage());

        new Calculator();

    }
    public void clear() {
        current = "";
        previous = "";
        operator = null;
    }
    public void updateOutput() {
        txtDisplay.setText(current);
    }
    public void appendToOutput(String num) {
        if(num.equals(".") && current.contains(".")) {
            return;
        }
        current += num;
    }
    public void selectOperator(String newOperator) {
        if (current.isEmpty()) {
            operator = newOperator;
            return;
        }

        if (!previous.isEmpty()) {
            calculate();
        }

        operator = newOperator;
        previous = current;
        current = "";
    }
    public void positiveNegative() {
        calculate();
        if(current.equals("")) {
            current = "0";
        }
        double num = Double.parseDouble(current);

        current = String.valueOf(num * -1);
        processOutputNumber();
    }
    public void squared() {
        calculate();
        double num = Double.parseDouble(current);
        current = String.valueOf(num * num);
        processOutputNumber();
    }
    public void percentage() {
        calculate();
        double num = Double.parseDouble(current);
        current = String.valueOf(num / 100);
    }
    public void calculate() {
        if (previous.length() < 1 || current.length() < 1) {
            return;
        }
        double result = 0.0;
        double num1 = Double.parseDouble(previous);
        double num2 = Double.parseDouble(current);
        switch (operator) {
            case "x" -> result = num1 * num2;
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "/" -> result = num1 / num2;
            default -> {
            }
        }
        current = String.valueOf(result);
        operator = null;
        previous = "";
        processOutputNumber();
    }
    public void processOutputNumber() {
        if (current.length() > 0) {
            String integerPart = current.split("\\.")[0];
            String decimalPart = current.split("\\.")[1];
            if (decimalPart.equals("0")) {
                current = integerPart;
            }
        }
    }

    private class NumberButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            for(JButton button : numButtons) {
                if(selectedButton.equals(button)) {
                    appendToOutput(button.getText());
                    updateOutput();
                }
            }
        }
    }
    private class OperatorButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if(selectedButton.equals(multiplyButton)) {
                selectOperator(multiplyButton.getText());
            } else if(selectedButton.equals(divideButton)) {
                selectOperator(divideButton.getText());
            } else if(selectedButton.equals(additionButton)) {
                selectOperator(additionButton.getText());
            } else if(selectedButton.equals(subtractButton)) {
                selectOperator(subtractButton.getText());
            }
            updateOutput();
        }
    }
    private class OtherButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if(selectedButton.equals(clearButton)) {
                clear();
            } else if (selectedButton.equals(decimalButton)) {
                appendToOutput(decimalButton.getText());
            } else if (selectedButton.equals(posNegButton)) {
                calculate();
                positiveNegative();
            } else if (selectedButton.equals(equalsButton)) {
                calculate();
            } else if (selectedButton.equals(percentButton)) {
                calculate();
                percentage();
            } else if (selectedButton.equals(squaredButton)) {
                squared();
            }
            updateOutput();
        }
    }
}
