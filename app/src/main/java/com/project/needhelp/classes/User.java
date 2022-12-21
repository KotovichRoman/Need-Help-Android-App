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

    public static User currentUser = new User();

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

        disposable.add(AuthActivity.app.getNeedHelpService().getApi().getToken(currentUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<UserDTO, Throwable>() {
                    @Override
                    public void accept(UserDTO userDTO, Throwable throwable) throws Exception {
                        if (throwable == null) {
                            WriteUserInformationToFile(userDTO.getToken());
                        }
                    }
                })
        );
    }

    public void Authentication(String token) {
        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(MainActivity.app.getNeedHelpService().getApi().getCurrentUser(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BiConsumer<User, Throwable>() {
                    @Override
                    public void accept(User user, Throwable throwable) throws Exception {
                        if (throwable == null) {
                            currentUser = user;
                        }
                    }
                })
        );
    }

    private void WriteUserInformationToFile(String token) throws IOException {
        File file = new File(AuthActivity.app.getFilesDir(), "User.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
        bufferedWriter.write(token);
        bufferedWriter.flush();
    }
}
