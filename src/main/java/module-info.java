module com.example.acg_labs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.acg_labs to javafx.fxml;
    exports com.example.acg_labs;
    exports com.example.acg_labs.entity;
    opens com.example.acg_labs.entity to javafx.fxml;
    exports com.example.acg_labs.entity.impl;
    opens com.example.acg_labs.entity.impl to javafx.fxml;
}