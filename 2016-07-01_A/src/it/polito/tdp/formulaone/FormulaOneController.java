package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;
	Integer anno = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnVittorie;

    @FXML
    private TextField textInputK;

    @FXML
    private Button btnDream;
    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	anno = boxAnno.getValue();
    	if (anno == null) {
    		txtResult.appendText("Selezionare un anno!");
    		return ;
    	}
    	
    	model.creaGrafo(anno);
    	txtResult.setText("Grafo creato!\n");
    	txtResult.appendText("Pilota migliore: "+ model.getPilotaMigliore().toString());

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	Integer dimensioneTeam = null;
    	
    	try {
    	dimensioneTeam = Integer.parseInt(textInputK.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un valore numerico! \n");
    		return;
    	}
    	
    	List<Driver> driversTeam = model.trovaDreamTeam(dimensioneTeam);
    	txtResult.clear();
    	txtResult.appendText("Driver del Dream Team di dimensione : " +dimensioneTeam+"\n");
    	for(Driver d : driversTeam) {
    		txtResult.appendText(d.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnVittorie != null : "fx:id=\"btnVittorie\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnDream != null : "fx:id=\"btnDream\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	boxAnno.getItems().addAll(model.getYears());
    }
}
