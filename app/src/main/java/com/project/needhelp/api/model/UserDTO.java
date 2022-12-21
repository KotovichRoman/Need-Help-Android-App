package com.project.needhelp.api.model;

import com.project.needhelp.classes.User;

import java.util.ArrayList;

public class UserDTO {
   private ArrayList<User> users;
   private User user;
   private String token;

    public ArrayList<User> getUsers() {
       return users;
   }

   public String getToken() {
       return token;
   }

   public User getUser() {
       return user;
   }
}

