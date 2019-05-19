package com.example.calllogger.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calllogger.R;

public class OpeningFragment extends Fragment {

    View generalView;
    TextView txtStartLogging ;
    LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.opening_fragment,container,false);

        generalView = rootView;

        txtStartLogging = generalView.findViewById(R.id.txtStartLogging);


        txtStartLogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginFragment = new LoginFragment();

                FragmentManager fragManager = getFragmentManager();
                FragmentTransaction fragTransaction = fragManager.beginTransaction();
                fragTransaction.replace(R.id.fragMain,loginFragment);
                fragTransaction.addToBackStack(null);
                fragTransaction.commit();


            }
        });

        return  rootView;

    }
}
