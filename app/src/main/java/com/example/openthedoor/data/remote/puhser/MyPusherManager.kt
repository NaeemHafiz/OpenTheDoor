package com.example.openthedoor.data.remote.puhser

import android.util.Log
import com.example.openthedoor.MyApplication
import com.example.openthedoor.data.sharedPreferences.AppPreferences
import com.example.openthedoor.utils.rxBus.RxBus
import com.example.openthedoor.data.remote.puhser.model.PusherEvent
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.channel.PrivateChannel
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.util.HttpAuthorizer
import java.lang.Exception

const val EVENT_NAME_NOTIFICATION = "Notification"
const val EVENT_NAME_MESSAGE_REQUEST_NOTIFICATION = "MessageRequest"
const val EVENT_NAME_MESSAGE_REQUEST_ACCEPTED_NOTIFICATION = "MessageRequestAccepted"
const val EVENT_NAME_MESSAGE_REQUEST_DECLINED_NOTIFICATION = "MessageRequestDeclined"
const val EVENT_MESSAGE = "Message"
const val EVENT_NAME_MY_EVENT = "my-event"

class MyPusherManager private constructor() {

    //TODO : Channel format -> private-users.{userId}.notifications

    var pusher: Pusher
    var channelName = "private-location-update-channel"
    var myChannel: Channel? = null
    var privateChannelNotification: Channel? = null
    var privateChannelMessage: Channel? = null
    lateinit var privateChannel: PrivateChannel
    lateinit var privateChannelRiderLocationUpdates: PrivateChannel

    init {
        val authorizer = HttpAuthorizer("http://172.104.217.178/swapme/public/api/auth")
        val options = PusherOptions()
        options.setCluster("ap2")
        options.authorizer = authorizer
        pusher = Pusher("8332aaa9ad861498c559", options)
    }

    fun connect() {

        pusher.connect()

        subscribeToNotifications()
        //subscribeToMessages()
    }

    fun disconnectPusher() {

        unsubscribeToNotifications()
//        unsubscribeToMessages()

        pusher.disconnect()
    }

    private fun subscribeToNotifications() {

        privateChannelNotification = pusher.getPrivateChannel(
            "private-user." +
                    "${AppPreferences(MyApplication.instance.applicationContext).getUser().user.id}.notifications"
        )

        if (privateChannelNotification == null) privateChannelNotification = pusher.subscribePrivate(
            "private-user." +
                    "${AppPreferences(MyApplication.instance.applicationContext).getUser().user.id}.notifications"
        )

        privateChannelNotification!!.bind(
            EVENT_NAME_MESSAGE_REQUEST_NOTIFICATION,
            object : PrivateChannelEventListener {
                override fun onEvent(channelName: String, eventName: String, data: String) {
                    RxBus.defaultInstance().send(PusherEvent(channelName, eventName, data))
                }

                override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
                    Log.e(TAG, p0)
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e(TAG, p0)
                }
            })
        privateChannelNotification!!.bind(
            EVENT_NAME_MESSAGE_REQUEST_ACCEPTED_NOTIFICATION,
            object : PrivateChannelEventListener {
                override fun onEvent(channelName: String, eventName: String, data: String) {
                    RxBus.defaultInstance().send(PusherEvent(channelName, eventName, data))
                }

                override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
                    Log.e(TAG, p0)
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e(TAG, p0)
                }
            })
        privateChannelNotification!!.bind(
            EVENT_NAME_MESSAGE_REQUEST_DECLINED_NOTIFICATION,
            object : PrivateChannelEventListener {
                override fun onEvent(channelName: String, eventName: String, data: String) {
                    RxBus.defaultInstance().send(PusherEvent(channelName, eventName, data))
                }

                override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
                    Log.e(TAG, p0)
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e(TAG, p0)
                }
            })

        privateChannelNotification!!.bind(EVENT_NAME_NOTIFICATION, object : PrivateChannelEventListener {
            override fun onEvent(channelName: String, eventName: String, data: String) {
                RxBus.defaultInstance().send(PusherEvent(channelName, eventName, data))
            }

            override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
                Log.e(TAG, p0)
            }

            override fun onSubscriptionSucceeded(p0: String?) {
                Log.e(TAG, p0)
            }
        })
    }

    fun subscribeToMessages(senderId: String, receiverId: String) {

        privateChannelMessage = pusher.getPrivateChannel(
            "private-user.$senderId.$receiverId.messages"
        )
        if (privateChannelMessage == null) privateChannelMessage = pusher.subscribePrivate(
            "private-user.$senderId.$receiverId.messages"
        )

        privateChannelMessage!!.bind(
            EVENT_MESSAGE,
            object : PrivateChannelEventListener {
                override fun onEvent(channelName: String, eventName: String, data: String) {
                    RxBus.defaultInstance().send(PusherEvent(channelName, eventName, data))
                }

                override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
                    Log.e(TAG, p0)
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e(TAG, p0)
                }
            })
    }


    private fun unsubscribeToNotifications() {
        if (myChannel != null && myChannel!!.isSubscribed) pusher.unsubscribe(myChannel!!.name)
//        if(privateChannelNotification != null && privateChannelNotification!!.isSubscribed) pusher.unsubscribe(privateChannelNotification!!.name)
    }

     fun unsubscribeToMessages() {
        if (privateChannelMessage != null && privateChannelMessage!!.isSubscribed) pusher.unsubscribe(
            privateChannelMessage!!.name
        )
    }

    fun subscribeToPrivateLocationUpdateChannelPusher(
        driverId: String,
        riderId: String,
        privateChannelEventListener: PrivateChannelEventListener
    ) {
        if (privateChannel != null && privateChannel.isSubscribed) return

        privateChannel = pusher.subscribePrivate("private-channel-$driverId-$riderId")
        privateChannel.bind("client-driver-location", privateChannelEventListener)
    }

    companion object {
        @JvmStatic
        var TAG: String = MyPusherManager::class.java.simpleName
        private var ourInstance: MyPusherManager? = null

        val instance: MyPusherManager
            get() {

                if (ourInstance == null) ourInstance = MyPusherManager()

                return ourInstance as MyPusherManager
            }
    }
}
