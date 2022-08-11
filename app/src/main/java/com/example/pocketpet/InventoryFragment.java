package com.example.pocketpet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import basic.Inventory;

public class InventoryFragment extends Fragment {

    Inventory inventory;
    View rootView;

    // Constructor
    public InventoryFragment(){
        super(R.layout.fragment_inventory);
        System.out.println("The inventory fragment constructor");
        //this.inventory = inventory;
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

        rootView = getView();

        Button button01 = rootView.findViewById(R.id.button01);
        Button button02 = rootView.findViewById(R.id.button02);
        Button button03 = rootView.findViewById(R.id.button03);
        Button closeButton = rootView.findViewById((R.id.close_button));

        //button01.setText(inventory.readSlotContent(0));
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Fragment fragment = this;
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().setReorderingAllowed(true).remove(fragment).commit();
            }
        });

    }
}
