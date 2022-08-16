package basic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelUI extends ViewModel {
    private MutableLiveData<Boolean> inventoryOpen;

    public ViewModelUI(){
        super();
        inventoryOpen = new MutableLiveData<>();
        inventoryOpen.setValue(false);
    }

    public void openInventory(){
        inventoryOpen.setValue(true);
    }

    public void closeInventory(){
        inventoryOpen.setValue(false);
    }

    public boolean isOpenInventory(){
        return inventoryOpen.getValue();
    }
}
