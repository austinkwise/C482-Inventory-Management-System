/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author austinwise
 */
public class AddProductScreenController implements Initializable {
    //TextFields on page
    @FXML private TextField productIDtf;
    @FXML private TextField productNametf;
    @FXML private TextField productInvLeveltf;
    @FXML private TextField productPricetf;
    @FXML private TextField productMinValtf; 
    @FXML private TextField productMaxValtf;
    
    //Top table variables
    @FXML TableView<Part> partTV;
    @FXML TableColumn<Part, Integer> partIdColumn;
    @FXML TableColumn<Part, String> partNameColumn;
    @FXML TableColumn<Part, Integer> partStockColumn;
    @FXML TableColumn<Part, Integer> partPriceColumn;
    
    @FXML TextField searchPartsTF;
    
    //Bottom Table variables
    @FXML TableView<Part> addedPartTV;
    @FXML TableColumn<Part, Integer> addedPartIdColumn;
    @FXML TableColumn<Part, String> addedPartNameColumn;
    @FXML TableColumn<Part, Integer> addedPartStockColumn;
    @FXML TableColumn<Part, Integer> addedPartPriceColumn;
    
    @FXML TextField searchAddedPartsTF;
    
    private int productId;
    
    //other variables
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
        
        Inventory.setProductIdCount();
        }
    }
    
    @FXML
    public void saveProductButton(ActionEvent event) throws IOException {
        addProductsToInv();
        if (!error){
        Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
        }
}
 
    private void addProductsToInv() throws IOException{
        String productName = productNametf.getText();
        int productInvLevel = Integer.parseInt(productInvLeveltf.getText());
        double productPrice = Double.parseDouble(productPricetf.getText());
        int productMinVal = Integer.parseInt(productMinValtf.getText());
        int productMaxVal = Integer.parseInt(productMaxValtf.getText());
        
        if(!validateInventory(productInvLevel, productMinVal, productMaxVal)){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error: Inventory Level");
        alert.setHeaderText("Please set an inventory level between the minimum and maximum levels");
        alert.showAndWait();
        error = true;
        }
        else{
            Product newProduct = new Product();
            
            newProduct.setProductID(productId);
            newProduct.setProductName(productName);
            newProduct.setProductInStock(productInvLevel);
            newProduct.setProductPrice(productPrice);
            newProduct.setProductMin(productMinVal);
            newProduct.setProductMax(productMaxVal);
            
            Inventory.addProduct(newProduct);
            newProduct.setAssociatedParts(addedPartTV.getItems());
            error = false;
        }
    }
    
    //Search for parts on both tables
    @FXML
    public void searchAvailablePartsButton(ActionEvent event){
        String search = searchPartsTF.getText();
        Part part;
        
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        for (int i = 0; i < Inventory.getPartsInventory().size(); i++){
            if ((Inventory.getPartsInventory().get(i).getPartName().contains(search))){
                part = Inventory.lookupPart(i);
                searchParts.add(part);
            }
            partTV.setItems(searchParts);
        }
    }
    
    @FXML
    public void searchCurrentPartsButton(ActionEvent event){
        String search = searchAddedPartsTF.getText();
        Part part;
        
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        for (int i = 0; i < Inventory.getPartsInventory().size(); i++){
            if ((Inventory.getPartsInventory().get(i).getPartName().contains(search))){
                part = Inventory.lookupPart(i);
                searchParts.add(part);
            }
            addedPartTV.setItems(searchParts);
        }
    }
    
    //Add and delete parts from table buttons
    @FXML
    public void addAssociatedPartButton(ActionEvent event){
        Part selectedPart = partTV.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            partOfProduct.addAssociatedPart(selectedPart);
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
        Part selectedPart = addedPartTV.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            removablePart = partOfProduct.getPartsInventory().indexOf(selectedPart);
            partOfProduct.removeAssociatedPart(removablePart);
            updateAddedPartsTv();
        }
        }
    }
    
    
    public void updateAddedPartsTv(){
        addedPartTV.setItems(partOfProduct.getPartsInventory());
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
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        partTV.setItems(Inventory.getPartsInventory());
        
        addedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        addedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        productIDtf.setEditable(false);
        
        productId = Inventory.getProductIdCount();
        productIDtf.setText("Sys Gen ID: " + Integer.toString(productId));
    }    
    
}
