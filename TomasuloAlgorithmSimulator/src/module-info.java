/**
 * 
 */
/**
 * 
 */
module TomasuloAlgorithmSimulator {
	// 7oto ay library test3melooha hena
	requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    // Ma7desh leeeh da3wa bel lines deh wala ye7ot wala yezawed
    opens com.tomasulo.gui to javafx.fxml;
    exports com.tomasulo.gui;
    exports com.tomasulo.main;
    exports com.tomasulo.simulator;
}