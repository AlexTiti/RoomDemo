package com.example.administrator.roomdemo.room

import android.arch.persistence.room.ColumnInfo

/**
 * @author  : Alex
 * @date    : 2018/08/07
 * @version : V 2.0.0
 */
class Address {
    public var street: String? = null
    public var state: String? = null
    public var city: String? = null
    @ColumnInfo(name = "post_code")
    public var postCode = 0
}