package com.example.calllogger.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calllogger.MainActivity;
import com.example.calllogger.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {

    View generalView;

    EditText edtEmail,edtPassword;
    TextView txtLogin;

    public Context ctx;

    private FirebaseAuth mAuth;

    public String TAG = "FirebaseStatus";

    CallLogFragment callLogFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        View rootView = inflater.inflate(R.layout.login_fragment,container,false);

        ctx = getContext();

        generalView = rootView;

        edtEmail = generalView.findViewById(R.id.edtEmail);
        edtPassword = generalView.findViewById(R.id.edtPassword);
        txtLogin = generalView.findViewById(R.id.txtLogin);


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                Log.i("isValidEmail", " " + isValidEmailAddress(email));


                if(isValidEmailAddress(email))
                {
                    createUser(email,password);
                }

                else
                {
                    Toast.makeText(ctx,"You Entered Invalid E-mail Address", Toast.LENGTH_LONG).show();
                }

            }
        });

        return  rootView;
    }


    public void createUser(String email, String password) {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    callLogFragment = new CallLogFragment();

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragMain,callLogFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();



                    //updateUI(user);
                }
                else
                {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(ctx, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    //updateUI(null);

                }

            }
        });


    }

    public static boolean isValidEmailAddress(String email)
    {

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();

    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);

    }
}
