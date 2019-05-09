
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
public class Inventory {
    private static ObservableList<Part> allPartsInventory =FXCollections.observableArrayList();
    private static ObservableList<Product> allProductsInventory =FXCollections.observableArrayList();
    private static Integer modPartIdx;
    private static Integer modProductIdx;
    private static int partIdCount;
    private static int productIdCount;
    
    public Inventory(){}
    
    /*
    ==================
    Parts
    ==================
    */
    public static ObservableList<Part>getPartsInventory(){
        return allPartsInventory;
    }
    
    public static void addPart(Part p){
        allPartsInventory.add(p);
    }
   
    //Modify the part
    public static Integer getModPartIdx(){
        return modPartIdx;
    }
    
    public static void setModPartIdx(){
        Inventory.modPartIdx = modPartIdx;
    }
    
    public static void replacePart(Part part){
        int i = 0;
        for (Part item : allPartsInventory){
            if(item.getPartID() == part.getPartID()){
                allPartsInventory.set(i, part);
            }
            else{
                i++;
            }
        }
    }  
            
    public static void updatePart(int i, Part updatedPart){
        allPartsInventory.set(i, updatedPart);
    }

    //Delete a part
    public static boolean deleteSelectedPart (Part p){
        boolean removedSuccessful = allPartsInventory.remove(p);
        return removedSuccessful;
    }
    
    //finding parts
    public static Part findPartId(int id){
        for (Part part : allPartsInventory) {
            if (part.getPartID() == id){
                return part;
            }
        }
        return null;
    }

    //Look up part
    
    public static Part lookupPart (int iPart){
        /*
        Part targetPart = null;
        targetPart = allPartsInventory.get(iPart);
        return targetPart;
        */
        return allPartsInventory.get(iPart);
    }

    /*
    ============
    Products
    ============
    */
    public static ObservableList<Product>getProductsInventory(){
        return allProductsInventory;
    }

    public static void addProduct(Product p){
        allProductsInventory.add(p);
    }

    //Modify the product
    public static Integer getModProductIdx(){
        return modProductIdx;
    }
    
    public static void setModProductIdx(){
        Inventory.modProductIdx = modProductIdx;
    }

    //Delete a product
    public static boolean deleteSelectedProduct (Product p){
        boolean removedSuccessful = allProductsInventory.remove(p);
        return removedSuccessful;
    }
    
    public static void replaceProduct(Product product){
        int i = 0;
        for (Product item : allProductsInventory){
            if(item.getProductID() == product.getProductID()){
                allProductsInventory.set(i, product);
            }
            else{
                i++;
            }
        }
    }
    
    public static void updateProduct(int i, Product updatedProduct){
        allProductsInventory.set(i, updatedProduct);
    }

    //finding products
    public static Product findProductId(int id){
        for (Product product : allProductsInventory) {
            if (product.getProductID() == id){
                return product;
            }
        }
        return null;
    }

    //Look up part
    
    public static Product lookupProduct (int iProduct){
        Product targetProduct = null;
        targetProduct = allProductsInventory.get(iProduct);
        return targetProduct;
    }
    
    //Id's missed in initial submission
    public static Integer getPartIdCount(){
        partIdCount++;
        return partIdCount;
    }
    
    public static void setPartIdCount(){
        partIdCount--;
    }
    
    public static Integer getProductIdCount(){
        productIdCount++;
        return productIdCount;
    }

    public static void setProductIdCount(){
        productIdCount--;
    }


}   
