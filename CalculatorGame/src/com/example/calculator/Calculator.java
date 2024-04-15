package com.example.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JFrame calculatorFrame;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        openCalculatorWindow(); // Call the method to open the calculator window directly

        // Center the calculator window on the screen
        setLocationRelativeTo(null);
        pack(); // Adjusts the size of the frame to fit its contents
        setVisible(true);
    }

    private void openCalculatorWindow() {
        setResizable(false); // Set frame resizable to false

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(36, 36, 36)); // Set background color to dark gray

        JTextField expressionField = new JTextField();
        expressionField.setPreferredSize(new Dimension(250, 60)); 
        expressionField.setFont(new Font("Segoe UI", Font.PLAIN, 22)); // Set font similar to Windows calculator
        expressionField.setForeground(Color.WHITE); // Set text color to white
        expressionField.setBackground(new Color(45, 45, 45)); // Set background color to dark gray
        expressionField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); // Use GridLayout for button layout
        buttonPanel.setBackground(new Color(36, 36, 36)); // Set background color to dark gray

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            ".", "0", "=", "+",
            "C", "←", "+/-"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Set button font similar to Windows calculator
            button.setForeground(Color.WHITE); // Set text color to white
            button.setBackground(new Color(54, 54, 54)); // Set background color to a darker shade of gray
            button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding

            // Customize '=' button
            if (label.equals("=")) {
                button.setBackground(new Color(0, 255, 127)); // Set background color to lime green
            }

            // Customize 'C' button
            if (label.equals("C")) {
                button.setForeground(Color.WHITE); // Set text color to white
                button.setBackground(Color.RED); // Set background color to red
            }

            // Customize '←' button
            if (label.equals("←")) {
                button.setForeground(Color.WHITE); // Set text color to white
                button.setBackground(Color.GRAY); // Set background color to gray
            }

            // Customize '+/-' button
            if (label.equals("+/-")) {
                button.setForeground(Color.WHITE); // Set text color to white
                button.setBackground(Color.GRAY); // Set background color to gray
            }

            button.addActionListener(e -> {
                String buttonText = button.getText();
                if (buttonText.equals("=")) {
                    String expression = expressionField.getText();
                    try {
                        double result = evaluateExpression(expression);
                        expressionField.setText(String.valueOf(result));
                    } catch (Exception ex) {
                        expressionField.setText("Error");
                    }
                } else if (buttonText.equals("C")) {
                    expressionField.setText("");
                } else if (buttonText.equals("+/-")) {
                    if (expressionField.getText().startsWith("-")) {
                        expressionField.setText(expressionField.getText().substring(1));
                    } else {
                        expressionField.setText("-" + expressionField.getText());
                    }
                } else if (buttonText.equals("←")) {
                    String text = expressionField.getText();
                    if (!text.isEmpty()) {
                        expressionField.setText(text.substring(0, text.length() - 1));
                    }
                } else {
                    expressionField.setText(expressionField.getText() + buttonText);
                }
            });
            buttonPanel.add(button);
        }

        panel.add(expressionField, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel); // Add the panel directly to the MainGUI frame

        pack(); // Adjusts the size of the frame to fit its contents
        setLocationRelativeTo(null); // Center the calculator window on the screen
    }
    
    private double evaluateExpression(String expression) {
        try {
            // Split the expression by arithmetic operators while preserving unary minus
            String[] parts = expression.split("(?=[-+*/])|(?<=[-+*/])");
            double result = 0;
            char lastOperator = '+';
            for (String part : parts) {
                if (part.isEmpty()) {
                    // Ignore empty parts
                    continue;
                }
                if (part.equals("+") || part.equals("-") || part.equals("*") || part.equals("/")) {
                    // Update lastOperator if the current part is an operator
                    lastOperator = part.charAt(0);
                } else {
                    // Convert the part to a number and apply the appropriate operation
                    double number = Double.parseDouble(part);
                    switch (lastOperator) {
                        case '+':
                            result += number;
                            break;
                        case '-':
                            result -= number;
                            break;
                        case '*':
                            result *= number;
                            break;
                        case '/':
                            if (number == 0) {
                                throw new ArithmeticException("Division by zero.");
                            }
                            result /= number;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid operator: " + lastOperator);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid expression.", ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
