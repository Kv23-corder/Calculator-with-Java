import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
            "AC", "+/-", "%", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "^", "=",
    };
    String[] rightSymbols = { "/", "x", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel1 = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // A+B , A-B , A*B , A/B
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel1.setBackground(customBlack);
        displayLabel1.setForeground(Color.white);
        displayLabel1.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel1.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel1.setText("0");
        displayLabel1.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel1);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel1.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    displayLabel1.setText(removeZeroDecimal(numA + numB));
                                } else if (operator == "-") {
                                    displayLabel1.setText(removeZeroDecimal(numA - numB));
                                } else if (operator == "x") {
                                    displayLabel1.setText(removeZeroDecimal(numA * numB));
                                } else if (operator == "/") {
                                    displayLabel1.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }

                        } else if ("+-x/".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel1.getText();
                                displayLabel1.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }

                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel1.setText("0");

                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel1.getText());
                            numDisplay *= -1;
                            displayLabel1.setText(removeZeroDecimal(numDisplay));

                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel1.getText());
                            numDisplay /= 100;
                            displayLabel1.setText(removeZeroDecimal(numDisplay));

                        }

                    } else { // digits or .
                        if (buttonValue == ".") {
                            if (!displayLabel1.getText().contains(buttonValue)) {
                                displayLabel1.setText(displayLabel1.getText() + buttonValue);
                            }

                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel1.getText() == "0") {
                                displayLabel1.setText(buttonValue);
                            } else {
                                displayLabel1.setText(displayLabel1.getText() + buttonValue);
                            }
                        }

                    }
                }

                void clearAll() {
                    A = "0";
                    operator = null;
                    B = null;
                }

                String removeZeroDecimal(double numDisplay) {
                    if (numDisplay % 1 == 0) {
                        return Integer.toString((int) numDisplay);
                    }
                    return Double.toString(numDisplay);
                }
            });
            frame.setVisible(true);

        }

    }
}
