package com.example.calllogger.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calllogger.R;

public class LoginFragment extends Fragment {

    View generalView;

    EditText edtEmail,edtPassword;
    TextView txtLogin;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.login_fragment,container,false);

        generalView = rootView;

        edtEmail = generalView.findViewById(R.id.edtEmail);
        edtPassword = generalView.findViewById(R.id.edtPassword);
        txtLogin = generalView.findViewById(R.id.txtLogin);


        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser(email,password);

            }
        });

        return  rootView;
    }

    private void createUser(String email, String password) {




    }
}
