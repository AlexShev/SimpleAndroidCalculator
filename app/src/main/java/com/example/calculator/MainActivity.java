package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView expression;

    Button[] numbers;

    Button dot;
    boolean isDot = false;

    Button calc;

    Button[] operations;
    char operation = ' ';

    BigDecimal firstValue = new BigDecimal(0);
    boolean thereIsFirst = false;

    boolean isInputingSecond = false;
    BigDecimal secondValue = new BigDecimal(0);
    boolean thereIsSecond = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.numberField);
        expression = findViewById(R.id.expressionField);

        numbers = new Button[] {
                findViewById(R.id.buttonZero),
                findViewById(R.id.buttonOne),
                findViewById(R.id.buttonTwo),
                findViewById(R.id.buttonThree),
                findViewById(R.id.buttonFour),
                findViewById(R.id.buttonFive),
                findViewById(R.id.buttonSix),
                findViewById(R.id.buttonSeven),
                findViewById(R.id.buttonEight),
                findViewById(R.id.buttonNine),
        };

        dot = findViewById(R.id.buttonDot);

        calc = findViewById(R.id.buttonCalculate);

        operations = new Button[] {
                findViewById(R.id.buttonAdd),
                findViewById(R.id.buttonSubtract),
                findViewById(R.id.buttonMultiply),
                findViewById(R.id.buttonDivide),
        };

        Arrays.stream(numbers).forEach(num -> num.setOnClickListener(view -> {
            CharSequence number = ((Button) view).getText();

            if (thereIsFirst && thereIsSecond) {
                thereIsFirst = false;
                thereIsSecond = false;
                isInputingSecond = false;
                expression.setText("");
                input.setText(number);
            } else if (thereIsFirst && !isInputingSecond) {
                input.setText(number);
                isInputingSecond = true;
            } else if (input.getText().length() == 1 && input.getText().charAt(0) == '0') {
                input.setText(number);
            } else {
                input.getText().append(number);
            }
        }));

        dot.setOnClickListener(view -> {
            if (!isDot) {
                isDot = true;
                input.getText().append('.');
            }
        });

        Arrays.stream(operations).forEach(button -> button.setOnClickListener(view -> {
            operation = ((Button) view).getText().charAt(0);
            expression.setText(input.getText().toString() + ' ' + operation);
            isDot = false;
            firstValue = new BigDecimal(input.getText().toString());
            thereIsFirst = true;
            thereIsSecond = false;
            isInputingSecond = false;
        }));

        calc.setOnClickListener(view -> {

            if (!thereIsSecond)
            {
                secondValue = new BigDecimal(input.getText().toString());
                thereIsSecond = true;
            }

            expression.setText(String.format("%s %s %s", firstValue.toString(), operation, secondValue.toString()));

            switch (operation)
            {
                case '+':
                    firstValue = firstValue.add(secondValue);
                    break;
                case '-':
                    firstValue = firstValue.subtract(secondValue);
                    break;
                case '/':
                    if (!secondValue.equals(new BigDecimal(0)))
                    {
                        firstValue = firstValue.divide(secondValue);
                    }
                    break;
                case '*':
                    firstValue = firstValue.multiply(secondValue);
                    break;
            }

            input.setText(firstValue.toString());
        });
    }
}