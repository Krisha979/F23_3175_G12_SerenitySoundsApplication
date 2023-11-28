package com.example.f23_3175_g12_serenitysoundsapplication.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM user_table WHERE email = :email")
    User getUserByEmail(String email);
    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Query("SELECT * FROM user_table")
    List<User> GetAllUsers();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertUserFromList(List<User> Students);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneUser(User user);
}