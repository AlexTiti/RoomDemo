package com.example.administrator.roomdemo.room

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.administrator.roomdemo.R
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private var liveData: LiveData<Array<UserTuple>> = MutableLiveData()
        get() {
            return userDao.loadUserInCityLive(arrayOf("常山"))
        }

    private val stringName = "Name"
    private var number = 0
    private val fragmentManger: FragmentManager by lazy {
        supportFragmentManager
    }

    val builder: StringBuilder by lazy {
        StringBuilder()
    }

    private val roomTestData: RoomTestData by lazy {
        RoomTestData.getInstance(this)
    }

    private val userDao: UserDao by lazy {
        roomTestData.userDao()
    }

    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            tv_main_show.text = msg?.obj as String
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd.setOnClickListener { addUser() }
        btnQuery.setOnClickListener { query() }
        btnUpdate.setOnClickListener { update() }
        btnDelete.setOnClickListener { delete() }


        val observer = Observer<Array<UserTuple>> {
            val stringBuilder = StringBuilder()
            for (index in it!!.indices) {
                val userTuple = it[index]
                stringBuilder.append(userTuple.name)
                        .append("   ")
                        .append(userTuple.city)
                        .append("   \n")
            }
            tv_main_show.text = stringBuilder.toString()
        }
        liveData?.observe(this, observer)
    }

    private fun addUser() {
        Thread(Runnable {
            val user = User()
            user.name = "罗平安 编号 = $number"
            user.strength = 100
            val address = Address()
            address.street = "成都"
            address.state = "蜀汉"
            address.city = "常山"
            address.postCode = 10010 + number++
            user.address = address
            userDao.inertUser(user)
        }).start()
    }

    private fun update() {
        Thread(Runnable {
            val user = User()
            user.id = 4
            user.strength = 100
            userDao.update(user)
        }).start()
    }

    private fun delete() {
        Thread(Runnable {
            val user = User()
            user.id = 1
            userDao.delete(user)
        }).start()
    }

    private fun query() {
        val stringBuilder = StringBuilder()
        Thread(Runnable {
            val userList = userDao.selectAll()

            for (user in userList) {
                stringBuilder.append(user.id)
                        .append("   ")
                        .append(user.name)
                        .append("   ")
                        .append(user.strength)
                        .append("   \n")
                        .append(user.address?.state)
                        .append("   ")
                        .append(user.address?.city)
                        .append("   ")
                        .append(user.address?.street)
                        .append("   ")
                        .append(user.address?.postCode)
                        .append("\n")
            }
            val message = Message.obtain(handler)
            message.obj = stringBuilder.toString()
            handler.sendMessage(message)
        }).start()
    }

    private fun queryUserAndCity() {

        Thread(Runnable {
            liveData = userDao.loadUserInCityLive(arrayOf("常山"))
        }).start()
    }

    private fun queyRxJava() {
        userDao.loadUserRxJava(4)
                .subscribe(Consumer {
                    val stringBuilder = StringBuilder()
                    stringBuilder.append(it.id)
                            .append("   ")
                            .append(it.name)
                            .append("   ")
                            .append(it.strength)
                            .append("   \n")
                    tv_main_show.text = stringBuilder.toString()
                })
    }

}
