package com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.f23_3175_g12_serenitysoundsapplication.Dao.ReminderDao;
import com.example.f23_3175_g12_serenitysoundsapplication.Dao.UserDao;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;

@Database(entities = {User.class, Reminder.class}, version = 2, exportSchema = false)
public abstract class SerenityDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ReminderDao reminderDao();

}
