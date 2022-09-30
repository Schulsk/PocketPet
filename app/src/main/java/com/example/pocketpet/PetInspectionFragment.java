package com.example.pocketpet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import basic.Animation;
import basic.Controller;
import basic.Saver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PetInspectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetInspectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private static final String PET_FILE_NAME = "param1";

    // Don't think I need these
    //private String mParam1;
    //private String mParam2;

    private String petFileName;

    SimulationViewModel viewModel;

    public PetInspectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PetInspectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetInspectionFragment newInstance(String param1, String param2) {
        PetInspectionFragment fragment = new PetInspectionFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            petFileName = getArguments().getString(PET_FILE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_inspection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // I will not need this ViewModel if I can send the pet name in the bundle.
        viewModel = new ViewModelProvider(requireActivity()).get(SimulationViewModel.class);

        // TextView setup
        TextView petNameView = view.findViewById(R.id.pet_name);
        TextView petTypeView = view.findViewById(R.id.pet_type);
        TextView petParentView = view.findViewById(R.id.pet_parent);
        TextView petBirthtimeView = view.findViewById(R.id.pet_birthtime);
        ImageView petImageView = view.findViewById(R.id.pet_image);

        /** Working here.*/
        // get the file of the pet that this thing was started with (petNameView that I am trying to send via the bundle)
        // Then extract the things I need for the different TextViews

        File file = new File(Saver.getPetSavefileDirectory() + petFileName);
        Scanner scanner = null;

        // Get the right pet savefile and extract the info that we need. Maybe save all the lines to
        // stats like we usually do before we load a pet

        try{
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }

        HashMap<String, String> stats = new HashMap<>();
        while (scanner != null && scanner.hasNext()){
            String[] parts = scanner.nextLine().split(" ");
            stats.put(parts[0], parts[1]);
        }

        petNameView.setText(stats.get("name"));
        petTypeView.setText(stats.get("type"));
        petParentView.setText("Parent: " + stats.get("parent"));
        petBirthtimeView.setText("Birth time: " + stats.get("birthtime"));
        petImageView.setBackgroundResource(Animation.getAnimation(stats.get("type")));


        // Button setup
        Button incubateButton = view.findViewById(R.id.button_incubate);
        Button sellButton = view.findViewById(R.id.button_sell);

        incubateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller controller = viewModel.getControllerLiveData().getValue();
                String eggName = controller.getInventory().readSlotContent(0);
                //controller.changePet(eggName);
                    /*

                    getParentFragmentManager().setFragmentResultListener("petName", (LifecycleOwner) v, new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                            String result = bundle.getString("petName");
                        }
                    });

                    Bundle bundle = new Bundle();
                    bundle.putString("param1", eggName);
                    getParentFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.fragmentContainerView, PetInspectionFragment.class, bundle)
                            .commit();
                    */

            }
        });
    }
}