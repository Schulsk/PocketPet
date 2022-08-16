package com.example.pocketpet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import basic.Controller;

public class SimulationViewModel extends ViewModel {


    private MutableLiveData<Controller> controllerLiveData = new MutableLiveData<>();

    public SimulationViewModel(){
        super();
        Controller controller = new Controller();
        controllerLiveData.setValue(controller);
    }

    public LiveData<Controller> getControllerLiveData(){
        return controllerLiveData;
    }


}
