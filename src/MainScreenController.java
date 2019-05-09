/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author austinwise
 */
public class MainScreenController implements Initializable {
    
    @FXML TableView<Part> partTV;
    @FXML TableColumn<Part, Integer> partIdColumn;
    @FXML TableColumn<Part, String> partNameColumn;
    @FXML TableColumn<Part, Integer> partStockColumn;
    @FXML TableColumn<Part, Integer> partPriceColumn;
    
    @FXML TextField searchPartsTF;
    
    private Part modPartSelected;
    private static int modPartIdx = -1;
    private Product modProductSelected;
    private static int modProductIdx = -1;
    
    private Button exit;
    
    @FXML TableView<Product> productTV;
    @FXML TableColumn<Product, Integer> productIdColumn;
    @FXML TableColumn<Product, String> productNameColumn;
    @FXML TableColumn<Product, Integer> productStockColumn;
    @FXML TableColumn<Product, Integer> productPriceColumn;
    
    @FXML TextField searchProductsTF;

    //Add part button takes you to add part screen to add a part
    @FXML
    private void addPartButton (ActionEvent event) throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    //Modify button llows you to select a part from the table press the modify button and modify the part selected
    @FXML
    private void modifyPartButton (ActionEvent event) throws IOException {
        modPartSelected = partTV.getSelectionModel().getSelectedItem();
        modPartIdx = partTV.getSelectionModel().getSelectedIndex();
        /*
        if (modPartIdx == -1)
        {    
             System.out.println("nothing selected");
        }*/
        //else {
            Parent addScene = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
            Scene scene = new Scene(addScene);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();   
        //}
        
    }
    
    //Delete Part button deletes a part
    @FXML
    private void deletePartButton (ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
         modPartSelected = partTV.getSelectionModel().getSelectedItem();
         modPartIdx = partTV.getSelectionModel().getSelectedIndex();
         
         Inventory.deleteSelectedPart(modPartSelected);
         setModIdx();
        }
    }
    
    //Exit button takes you out of the programs
    public void exitButton(){
        Platform.exit();
    }
   
    //Search parts inventory by name
    @FXML
    public void searchPartsButton(ActionEvent event){
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
    private void addProductButton (ActionEvent event) throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void modifyProductButton (ActionEvent event) throws IOException {
        modProductSelected = productTV.getSelectionModel().getSelectedItem();
        modProductIdx = productTV.getSelectionModel().getSelectedIndex();
        
        Parent addScene = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
        Scene scene = new Scene(addScene);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    public void searchProductsButton(ActionEvent event){
        String search = searchProductsTF.getText();
        Product product;
        
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        for (int i = 0; i < Inventory.getProductsInventory().size(); i++){
            if ((Inventory.getProductsInventory().get(i).getProductName().contains(search))){
                product = Inventory.lookupProduct(i);
                searchProducts.add(product);
            }
            productTV.setItems(searchProducts);
        }
    }
    
    @FXML
    private void deleteProductButton (ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Delete Product");
         alert.setHeaderText("Are you sure you want to delete this product?");
         Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){
         modProductSelected = productTV.getSelectionModel().getSelectedItem();
         modProductIdx = productTV.getSelectionModel().getSelectedIndex();
         
         Inventory.deleteSelectedProduct(modProductSelected);
         setModIdx();
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
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productInStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productTV.setItems(Inventory.getProductsInventory());
    }    
    
    public static int getModPartIdx(){
        return modPartIdx;
    }
    
    public static void setModIdx(){
        modPartIdx = -1;
        modProductIdx = -1;
    }
    
    public static int getModProductIdx(){
        return modProductIdx;
    }
}
