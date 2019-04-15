/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author austinwise
 */
public class ModifyPartScreenController implements Initializable {
    @FXML
    private TextField modPartIDtf;
    @FXML
    private TextField modPartNametf;
    @FXML
    private TextField modPartInvLeveltf;
    @FXML
    private TextField modPartPricetf;
    @FXML
    private TextField modPartMinValtf;
    @FXML
    private TextField modPartMaxValtf;
    @FXML
    private TextField modPartInVsOuttf;

    @FXML
    private RadioButton modinHouseButton;
    @FXML
    private RadioButton modoutSourcedButton;

    @FXML
    private ToggleGroup modinAndOutTG;
    @FXML
    private Label modinVsOutsourced;
    @FXML
    private Boolean modisOutsourced = false;
    @FXML
    private AnchorPane modrootPane;

    private int modIdx;
    private int partId;
    private Part modPart;
    private boolean error = false;
    
    
    
    //invsoutsource switch text
    @FXML public void outSourcedSwitch(ActionEvent event){
        modinVsOutsourced.setText("Company Name:"); 
        modisOutsourced = true;
    }
    
    @FXML public void inHouseSwitch(ActionEvent event){
        modinVsOutsourced.setText("Machine ID:"); 
        modisOutsourced = false;
    }
    
    //Cancel button takes back to main page
    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("Are you sure you don't want to save this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Parent addScene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        }
    }

    //Save button saves modified part and takes back to main page
    public void saveModPartButton(ActionEvent event) throws IOException {
        modPartsToInv();
        if (!error){
        Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
        }
    }

    private void modPartsToInv() throws IOException {
        int partID = Integer.parseInt(modPartIDtf.getText());
        String partName = modPartNametf.getText();
        int partInvLevel = Integer.parseInt(modPartInvLeveltf.getText());
        double partPrice = Double.parseDouble(modPartPricetf.getText());
        int partMinVal = Integer.parseInt(modPartMinValtf.getText());
        int partMaxVal = Integer.parseInt(modPartMaxValtf.getText());
        String companyName = modPartInVsOuttf.getText();
        
        if(!validateInventory(partInvLevel, partMinVal, partMaxVal)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error: Inventory Level");
        alert.setHeaderText("Please set an inventory level between the minimum and maximum levels");
        alert.showAndWait();
        error = true;
        }
        else{
        if (modisOutsourced) {
            Outsourced ModOPart = new Outsourced();

            ModOPart.setPartID(partID);
            ModOPart.setPartName(partName);
            ModOPart.setPartInStock(partInvLevel);
            ModOPart.setPartPrice(partPrice);
            ModOPart.setPartMin(partMinVal);
            ModOPart.setPartMax(partMaxVal);
            ModOPart.setCompanyName(companyName);

            Inventory.replacePart(ModOPart);
        } else {
            InHouse ModiPart = new InHouse();

            ModiPart.setPartID(partID);
            ModiPart.setPartName(partName);
            ModiPart.setPartInStock(partInvLevel);
            ModiPart.setPartPrice(partPrice);
            ModiPart.setPartMin(partMinVal);
            ModiPart.setPartMax(partMaxVal);
            int machineID = Integer.parseInt(modPartInVsOuttf.getText());
            ModiPart.setMachineID(machineID);

            Inventory.replacePart(ModiPart);
        }
        error = false;
        }
    }
    
    public boolean validateInventory(int lvl, int min, int max){
        if (lvl>min && lvl<max){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        modinAndOutTG = new ToggleGroup();
        this.modinHouseButton.setToggleGroup(modinAndOutTG);
        this.modoutSourcedButton.setToggleGroup(modinAndOutTG);
        
        modPartIDtf.setEditable(false);
        
        modIdx = MainScreenController.getModPartIdx();
        modPart = Inventory.getPartsInventory().get(modIdx);
        partId = modPart.getPartID(); 
        
        modPartIDtf.setText(Integer.toString(partId));
        modPartNametf.setText(modPart.getPartName());
        modPartInvLeveltf.setText(Integer.toString(modPart.getPartInStock()));
        modPartPricetf.setText(Double.toString(modPart.getPartPrice()));
        modPartMinValtf.setText(Integer.toString(modPart.getPartMin()));
        modPartMaxValtf.setText(Integer.toString(modPart.getPartMax()));
        
        if (modPart instanceof InHouse){
        modPartInVsOuttf.setText(Integer.toString(((InHouse) modPart).getMachineID()));
        modinHouseButton.setSelected(true);
        modisOutsourced = false;
        }
        else{
        modPartInVsOuttf.setText(((Outsourced) modPart).getCompanyName());    
        modoutSourcedButton.setSelected(true);
        modisOutsourced = true;
        }
    
        
        
    }

}
