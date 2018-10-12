package com.example.administrator.roomdemo.room

import android.arch.persistence.room.ColumnInfo

/**
 * @author  : Alex
 * @date    : 2018/08/07
 * @version : V 2.0.0
 */
class UserTuple {
    @ColumnInfo(name = "name")
    public var name: String? = null
    @ColumnInfo(name = "city")
    public var city: String? = null
    @ColumnInfo(name = "street")
    public var street: String? = null
}