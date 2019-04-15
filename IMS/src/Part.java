
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author austinwise
 */
public abstract class Part {
    private StringProperty partName;
    private IntegerProperty partID, partInStock, partMin, partMax;
    private DoubleProperty partPrice;

    public Part() {
        this.partName = new SimpleStringProperty();
        this.partID = new SimpleIntegerProperty();
        this.partInStock = new SimpleIntegerProperty();
        this.partMin = new SimpleIntegerProperty();
        this.partMax = new SimpleIntegerProperty();
        this.partPrice = new SimpleDoubleProperty();
    }

    
    
    //Getters
    public String getPartName() {
        return partName.get();
    }

    public int getPartID() {
        return partID.get();
    }

    public int getPartInStock() {
        return partInStock.get();
    }

    public int getPartMin() {
        return partMin.get();
    }

    public int getPartMax() {
        return partMax.get();
    }

    public double getPartPrice() {
        return partPrice.get();
    }
    
    //Setters
    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartInStock(int partInStock) {
        this.partInStock.set(partInStock);
    }

    public void setPartMin(int partMin) {
        this.partMin.set(partMin);
    }

    public void setPartMax(int partMax) {
        this.partMax.set(partMax);
    }

    public void setPartPrice(double partPrice) {
        this.partPrice.set(partPrice);
    }
    
}
