module com.example.acg_labs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.acg_labs to javafx.fxml;
    exports com.example.acg_labs;
    exports com.example.acg_labs.utils;
    opens com.example.acg_labs.utils to javafx.fxml;
}