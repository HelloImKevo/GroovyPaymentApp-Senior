package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.UserEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<UserEntity> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(UserEntity... values);

    @Update
    void updateUsers(UserEntity... values);

    @Delete
    void deleteUsers(UserEntity... values);
}
