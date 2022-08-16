package com.example.pocketpet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import basic.Controller;
import basic.Inventory;
import basic.ViewModelUI;

public class InventoryFragment extends Fragment {

    Inventory inventory;
    View rootView;
    SimulationViewModel viewModel;
    ViewModelUI viewModelUI;

    // Constructor
    public InventoryFragment(){
        super(R.layout.fragment_inventory);
        System.out.println("The inventory fragment constructor");
        //this.inventory = inventory;
    }

    @Override
    public void onStart(){
        super.onStart();
        //viewModel = new ViewModelProvider(requireActivity()).get(SimulationViewModel.class);
        //System.out.println("InventoryFragment.onStart - viewModel: " + viewModel!=null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("The inventory fragment onCreate()");
    }

    // Have to do things here, because the root view is not created yet in onCreate()
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SimulationViewModel.class);
        inventory = viewModel.getControllerLiveData().getValue().getInventory();
        viewModelUI = new ViewModelProvider(requireActivity()).get(ViewModelUI.class);

        rootView = getView();

        Button button01 = rootView.findViewById(R.id.button01);
        Button button02 = rootView.findViewById(R.id.button02);
        Button button03 = rootView.findViewById(R.id.button03);
        Button closeButton = rootView.findViewById((R.id.close_button));

        setButtonText(button01, 0);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switching the pet for the egg in the inventory
                // todo: Make a method here that opens a window asking you if you wanna load it as a pet.
                // Temp solution
                if (!button01.getText().equals("--empty--")){
                    Controller controller = viewModel.getControllerLiveData().getValue();
                    String eggName = controller.withdraw(0);
                    controller.changePet(eggName);
                }
            }
        });

        setButtonText(button02, 1);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setButtonText(button03, 2);
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Fragment fragment = this;
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModelUI.closeInventory();
                getParentFragmentManager().beginTransaction().setReorderingAllowed(true).remove(fragment).commit();
            }
        });

    }

    private void setButtonText(Button button, int index){
        String slotContent = inventory.readSlotContent(index);

        if (slotContent.equals("null")){
            button.setText("--empty--");
        }
        else{
            button.setText(slotContent);
        }
    }
}
