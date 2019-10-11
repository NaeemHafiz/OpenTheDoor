package com.example.openthedoor.utils.rxBus.model

class Event {

    var type: Int = 0

    var data: Any? = null

    constructor()

    constructor(type: Int) {
        this.type = type
    }

    constructor(type: Int, data: Any) {
        this.type = type
        this.data = data
    }

    companion object {

        const val TYPE_REFRESH: Int = 100
        const val TYPE_INTERNET_STATE_CHANGED: Int = 101
        const val TYPE_VIDEO_FILE_READY: Int = 102
        const val TYPE_REMAKE_VIDEO: Int = 103
        const val TYPE_PROFILE_UPDATED: Int = 104
    }
}
