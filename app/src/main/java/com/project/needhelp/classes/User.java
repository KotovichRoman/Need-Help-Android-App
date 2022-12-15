package com.project.needhelp.classes;

import android.util.Log;
import android.widget.Toast;

import androidx.collection.ArraySet;

import com.project.needhelp.AuthActivity;
import com.project.needhelp.AuthFragment;
import com.project.needhelp.MainActivity;
import com.project.needhelp.api.model.UserDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class User {
    public Integer id;
    public String login;
    public String email;
    public String password;

    public static User currentUser;

    public User() { }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public void Authorization() {
        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(AuthActivity.appAuth.getNeedHelpService().getApi().getToken(currentUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Response<UserDTO>, Throwable>() {
                    @Override
                    public void accept(Response<UserDTO> responseBody, Throwable throwable) throws Exception {
                        if (throwable == null) {
                            assert responseBody.body() != null;
                            WriteTokenToFile(responseBody.body().getToken());
                        } else {
                            assert responseBody.errorBody() != null;
                            Toast.makeText(MainActivity.context, responseBody.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
        );
    }

    private static void WriteTokenToFile(String token) throws IOException {
        File file = new File(AuthActivity.appAuth.getFilesDir(), "User.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write(currentUser.email + ";" + currentUser.password + ";" + token);
        bufferedWriter.flush();
    }
}
