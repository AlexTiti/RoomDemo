package com.example.administrator.roomdemo.room

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * @author  : Alex
 * @date    : 2018/08/07
 * @version : V 2.0.0
 */

@Entity(tableName = "user")
open class User {
    @PrimaryKey(autoGenerate = true)
    public var id = 0

    public var strength = 0

    public var name: String? = null

    @Embedded
    public var address: Address? = null

}