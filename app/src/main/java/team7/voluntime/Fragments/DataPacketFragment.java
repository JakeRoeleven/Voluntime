package team7.voluntime.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team7.voluntime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataPacketFragment extends Fragment {


    public DataPacketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_packet, container, false);
    }

}