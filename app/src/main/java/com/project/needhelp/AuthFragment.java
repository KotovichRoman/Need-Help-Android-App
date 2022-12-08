package com.project.needhelp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.needhelp.api.model.UserDTO;
import com.project.needhelp.classes.User;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthFragment extends Fragment {

    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        User loginInformation = new User("bigden-2003@gmail.com", "qwerty");

        disposable.add(MainActivity.appAuth.getNeedHelpService().getApi().getToken(loginInformation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Response<UserDTO>, Throwable>() {
                    @Override
                    public void accept(Response<UserDTO> responseBody, Throwable throwable) throws Exception {
                        if (throwable == null) {
                            if (responseBody.body() != null) {
                                Log.d("token", responseBody.body().getToken());
                            }
                        } else {
                            Log.d("token","pon");
                        }
                    }
                }));

        return inflater.inflate(R.layout.fragment_auth, container, false);
    }
}