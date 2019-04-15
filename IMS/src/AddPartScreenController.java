
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
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
public class AddPartScreenController implements Initializable {
    
    //Instance Variables
    @FXML private TextField partIDtf;
    @FXML private TextField partNametf;
    @FXML private TextField partInvLeveltf;
    @FXML private TextField partPricetf;
    @FXML private TextField partMinValtf; 
    @FXML private TextField partMaxValtf;
    @FXML private TextField partInVsOuttf;
   
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outSourcedButton;
    
    @FXML private ToggleGroup inAndOutTG;
    @FXML private Label inVsOutsourced;
    @FXML private Boolean isOutsourced = false;
    @FXML private AnchorPane rootPane;
    private boolean error = false;
    private int partId;
    
    
    
    @FXML public void outSourcedSwitch(ActionEvent event){
        inVsOutsourced.setText("Company Name:"); 
        isOutsourced = true;
    }
    
    @FXML public void inHouseSwitch(ActionEvent event){
        inVsOutsourced.setText("Machine ID:"); 
        isOutsourced = false;
    }
//Cancel button takes back to main page
    @FXML
    private void cancelButton (ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("Are you sure you don't want to save this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Parent addScene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
        Inventory.setPartIdCount();
        }
    }
 
    //Save button adds new part
    public void savePartButton(ActionEvent event) throws IOException {
        addPartsToInv();
        if (!error){
        Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
        }
}
    
    private void addPartsToInv() throws IOException{
        String partName = partNametf.getText();
        int partInvLevel = Integer.parseInt(partInvLeveltf.getText());
        double partPrice = Double.parseDouble(partPricetf.getText());
        int partMinVal = Integer.parseInt(partMinValtf.getText());
        int partMaxVal = Integer.parseInt(partMaxValtf.getText());
        String companyName = partInVsOuttf.getText();
        
        if(!validateInventory(partInvLevel, partMinVal, partMaxVal)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error: Inventory Level");
        alert.setHeaderText("Please set an inventory level between the minimum and maximum levels");
        alert.showAndWait();
        error = true;
        }
        else{
        if(isOutsourced){
            Outsourced oPart = new Outsourced();
            
            oPart.setPartID(partId);
            oPart.setPartName(partName);
            oPart.setPartInStock(partInvLevel);
            oPart.setPartPrice(partPrice);
            oPart.setPartMin(partMinVal);
            oPart.setPartMax(partMaxVal);
            oPart.setCompanyName(companyName);
            
            Inventory.addPart(oPart);
        }
        else{
            InHouse iPart = new InHouse();
            
            iPart.setPartID(partId);
            iPart.setPartName(partName);
            iPart.setPartInStock(partInvLevel);
            iPart.setPartPrice(partPrice);
            iPart.setPartMin(partMinVal);
            iPart.setPartMax(partMaxVal);
            int machineID = Integer.parseInt(partInVsOuttf.getText());
            iPart.setMachineID(machineID);
            
            Inventory.addPart(iPart);
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
        inAndOutTG = new ToggleGroup();
        this.inHouseButton.setToggleGroup(inAndOutTG);
        this.outSourcedButton.setToggleGroup(inAndOutTG);
        
        partIDtf.setEditable(false);
        
        partId = Inventory.getPartIdCount();
        partIDtf.setText("Sys Gen ID: " + Integer.toString(partId));
    }    
    
}
