package com.nourishnet.javafx_examples;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalculatorAppExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        // Create the grid pane for layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Create the text field for display
        TextField display = new TextField();
        display.setEditable(false);
        GridPane.setColumnSpan(display, 4);
        grid.getChildren().add(display);

        // Create numeric buttons
        int num = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button("" + num);
                button.setPrefSize(50, 50);
                button.setOnAction(e -> display.appendText(button.getText()));
                grid.add(button, j, i + 1);
                num++;
            }
        }

        // Create additional buttons
        Button zeroButton = new Button("0");
        zeroButton.setPrefSize(50, 50);
        zeroButton.setOnAction(e -> display.appendText("0"));
        grid.add(zeroButton, 0, 4);

        Button dotButton = new Button(".");
        dotButton.setPrefSize(50, 50);
        dotButton.setOnAction(e -> display.appendText("."));
        grid.add(dotButton, 1, 4);

        Button clearButton = new Button("C");
        clearButton.setPrefSize(50, 50);
        clearButton.setOnAction(e -> display.clear());
        grid.add(clearButton, 2, 4);

        Button equalsButton = new Button("=");
        equalsButton.setPrefSize(50, 50);
        equalsButton.setOnAction(e -> {
            String expression = display.getText();
            if (!expression.isEmpty()) {
                try {
                    // Evaluate the expression
                    // double result = evaluateExpression(expression);
                    // display.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            }
        });
        grid.add(equalsButton, 3, 4);

        // Create the scene
        Scene scene = new Scene(grid, 220, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Evaluate the mathematical expression
    // private double evaluateExpression(String expression) {
    //     return new javax.script.ScriptEngineManager()
    //             .getEngineByName("javascript")
    //             .eval(expression);
    // }

    public static void main(String[] args) {
        launch(args);
    }
}
