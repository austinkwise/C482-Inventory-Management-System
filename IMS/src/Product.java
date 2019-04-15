
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author austinwise
 */
public class Product {
    private StringProperty productName;
    private IntegerProperty productID, productInStock, productMin, productMax;
    private DoubleProperty productPrice;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    
    public Product() {
        this.productName = new SimpleStringProperty();
        this.productID = new SimpleIntegerProperty();
        this.productInStock = new SimpleIntegerProperty();
        this.productMin = new SimpleIntegerProperty();
        this.productMax = new SimpleIntegerProperty();
        this.productPrice = new SimpleDoubleProperty();
    }

    //getters
    public String getProductName() {
        return productName.get();
    }

    public int getProductID() {
        return productID.get();
    }

    public int getProductInStock() {
        return productInStock.get();
    }

    public int getProductMin() {
        return productMin.get();
    }

    public int getProductMax() {
        return productMax.get();
    }

    public double getProductPrice() {
        return productPrice.get();
    }

    //setters

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setProductInStock(int productInStock) {
        this.productInStock.set(productInStock);
    }

    public void setProductMin(int productMin) {
        this.productMin.set(productMin);
    }

    public void setProductMax(int productMax) {
        this.productMax.set(productMax);
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }
    
    //Associated Parts
    public ObservableList<Part> getPartsInventory(){
        return associatedParts;
    }
    
    public void setAssociatedParts(ObservableList<Part> p){
        associatedParts = p;
    }
    
    public void addAssociatedPart (Part p){
        associatedParts.add(p);
    }
    
    public boolean removeAssociatedPart(int i){
        associatedParts.remove(i);
        return true;
        
    }
    
    public void updateAssociatedPart (int i, Part p){
        associatedParts.set(i, p);
    }
    
    public Part lookupAssociatedPart(int i){
        Part p = associatedParts.get(i);
        return p;
    }

}
