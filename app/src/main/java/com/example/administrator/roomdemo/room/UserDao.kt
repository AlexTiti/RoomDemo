package com.example.administrator.roomdemo.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.administrator.roomdemo.room.User
import io.reactivex.Flowable

/**
 * @author  : Alex
 * @date    : 2018/08/07
 * @version : V 2.0.0
 */
@Dao
interface UserDao {
    @Insert
    public fun inertUser(user: User)

    @Insert
    public fun insertUserList(array: Array<User>)

    @Update
    public fun update(user: User)

    @Delete
    public fun delete(user: User)


    @Query("SELECT * FROM user")
    public fun selectAll(): Array<User>

    @Query("SELECT * FROM user WHERE name = :name")
    public fun selectUser(name: String): Array<User>

    @Query("SELECT name ,city FROM user")
    fun loadUserCity(): List<UserTuple>

    @Query("SELECT name,street  FROM user WHERE city IN (:cityArray)")
    fun loadUserInCity(cityArray: Array<String>): List<UserTuple>

    @Query("SELECT name,street  FROM user WHERE city IN (:cityArray)")
    fun loadUserInCityLive(cityArray: Array<String>): LiveData<Array<UserTuple>>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun loadUserRxJava(id: Int): Flowable<User>

}