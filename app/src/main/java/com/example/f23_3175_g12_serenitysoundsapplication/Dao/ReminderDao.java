package com.example.f23_3175_g12_serenitysoundsapplication.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    void insert(Reminder reminder);

    @Query("SELECT * FROM reminder WHERE timestamp >= :currentTimeMillis")
    List<Reminder> getFutureReminders(long currentTimeMillis);
}
