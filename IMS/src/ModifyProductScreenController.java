/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author austinwise
 */
public class ModifyProductScreenController implements Initializable {
    //TextFields on page
    @FXML private TextField modProductIDtf;
    @FXML private TextField modProductNametf;
    @FXML private TextField modProductInvLeveltf;
    @FXML private TextField modProductPricetf;
    @FXML private TextField modProductMinValtf; 
    @FXML private TextField modProductMaxValtf;
    
    //Top table variables
    @FXML TableView<Part> modPartTV;
    @FXML TableColumn<Part, Integer> modPartIdColumn;
    @FXML TableColumn<Part, String> modPartNameColumn;
    @FXML TableColumn<Part, Integer> modPartStockColumn;
    @FXML TableColumn<Part, Integer> modPartPriceColumn;
    
    @FXML TextField searchModPartsTF;
    
    //Bottom Table variables
    @FXML TableView<Part> addedModPartTV;
    @FXML TableColumn<Part, Integer> addedModPartIdColumn;
    @FXML TableColumn<Part, String> addedModPartNameColumn;
    @FXML TableColumn<Part, Integer> addedModPartStockColumn;
    @FXML TableColumn<Part, Integer> addedModPartPriceColumn;
    
    @FXML TextField searchAddedPartsTF;
    
    //Other variables
    private int modIdx;
    private int productId;
    private Product modProduct;
    private Product partOfProduct = new Product();
    private int removablePart;
    private boolean error = false;
    
    //Cancel button takes back to main page
    @FXML
    private void cancelButton (ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Product");
        alert.setHeaderText("Are you sure you don't want to save this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Parent addScene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        }
    }
    
    public void saveModProductButton(ActionEvent event) throws IOException {
        modProductsToInv();
        if (!error){
        Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
        }
    }
    
    private void modProductsToInv() throws IOException {
        int productID = Integer.parseInt(modProductIDtf.getText());
        String productName = modProductNametf.getText();
        int productInvLevel = Integer.parseInt(modProductInvLeveltf.getText());
        double productPrice = Double.parseDouble(modProductPricetf.getText());
        int productMinVal = Integer.parseInt(modProductMinValtf.getText());
        int productMaxVal = Integer.parseInt(modProductMaxValtf.getText());

        if(!validateInventory(productInvLevel, productMinVal, productMaxVal)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error: Inventory Level");
        alert.setHeaderText("Please set an inventory level between the minimum and maximum levels");
        alert.showAndWait();
        error = true;
        }
        else{
        Product newProduct = new Product();
            
            newProduct.setProductID(productID);
            newProduct.setProductName(productName);
            newProduct.setProductInStock(productInvLevel);
            newProduct.setProductPrice(productPrice);
            newProduct.setProductMin(productMinVal);
            newProduct.setProductMax(productMaxVal);
            
            Inventory.updateProduct(modIdx, newProduct);
            newProduct.setAssociatedParts(addedModPartTV.getItems());
        error = false;
        }
    }
    
    @FXML
    public void addAssociatedPartButton(ActionEvent event){
        Part selectedPart = modPartTV.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            modProduct.addAssociatedPart(selectedPart);
            updateAddedPartsTv();
        }
    }
    
    @FXML 
    public void deleteAssociatedPartButton (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Part selectedPart = addedModPartTV.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            removablePart = modProduct.getPartsInventory().indexOf(selectedPart);
            modProduct.removeAssociatedPart(removablePart);
            updateAddedPartsTv();
        }
        }
    }
    
    public void updateAddedPartsTv(){
        addedModPartTV.setItems(modProduct.getPartsInventory());
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
        modIdx = MainScreenController.getModProductIdx();
        modProduct = Inventory.getProductsInventory().get(modIdx);
        productId = modProduct.getProductID(); 
        
        modProductIDtf.setText(Integer.toString(productId));
        modProductNametf.setText(modProduct.getProductName());
        modProductInvLeveltf.setText(Integer.toString(modProduct.getProductInStock()));
        modProductPricetf.setText(Double.toString(modProduct.getProductPrice()));
        modProductMinValtf.setText(Integer.toString(modProduct.getProductMin()));
        modProductMaxValtf.setText(Integer.toString(modProduct.getProductMax()));
        
        updateAddedPartsTv();
        
        modPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        modPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        addedModPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addedModPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addedModPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        addedModPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        modPartTV.setItems(Inventory.getPartsInventory());
        
        modProductIDtf.setEditable(false);
    }    
    
}
