package com.project.needhelp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.needhelp.api.model.UserDTO;
import com.project.needhelp.classes.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthFragment extends Fragment {

    private View view;
    private Button logInButton, signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_auth, container, false);

        logInButton = view.findViewById(R.id.logInButton);
        signUpButton = view.findViewById(R.id.signUpButton);

        LogInButtonClick();

        return view;
    }

    private void LogInButtonClick() {
        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText email = view.findViewById(R.id.userEmailEdit);
                EditText password = view.findViewById(R.id.userPasswordEdit);

                User.currentUser.email = email.getText().toString();
                User.currentUser.password = password.getText().toString();

                User.currentUser.Authorization();
            }
        });
    }
}
