package com.nourishnet;
module test {

    requires javafx.controls;
    requires javafx.fxml;

    opens javafx_examples to javafx.fxml;
    exports javafx_examples;
}
