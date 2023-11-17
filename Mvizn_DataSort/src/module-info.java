module Mvizn_DataSort {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.desktop;
	requires javafx.swing;
	requires java.sql.rowset;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
