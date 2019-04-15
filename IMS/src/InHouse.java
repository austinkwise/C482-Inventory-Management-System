
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;



/**
 *
 * @author austinwise
 */
public class InHouse extends Part {
    
private IntegerProperty machineID;

    public InHouse(){
    
        super();
        machineID = new SimpleIntegerProperty();

    }

    public int getMachineID() {
        return this.machineID.get();
    }

    public void setMachineID(int machineID) { 
        this.machineID.set(machineID);
        
    }

}