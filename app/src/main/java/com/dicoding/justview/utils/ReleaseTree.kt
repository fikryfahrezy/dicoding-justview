package com.dicoding.justview.utils

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (t == null) {
//                Send to preferred analytics or do something
            } else {
//                Send to preferred analytics or do something
                Timber.d("THIS IS JUST TEMPORARY PLACEHOLDER, REMOVE IF NOT USED ANYMORE")
            }
        }
    }
}
