package com.example.incomingoutcomingcalldetect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import java.text.SimpleDateFormat
import java.util.*

class MyService : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (incomingOutgoingCallDetect != null) {
            ctx?.let { incomingOrOutgoingCall(it, intent!!) }
                ?.let { incomingOutgoingCallDetect!!.onCallDetect(it) }
        }
    }

    private fun incomingOrOutgoingCall(ctx: Context, intent: Intent): String {
        var status = ""
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                .equals(TelephonyManager.EXTRA_STATE_OFFHOOK)
        ) {
            status = "callStarted"
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                .equals(TelephonyManager.EXTRA_STATE_IDLE)
        ) {
            status = "callEnded"
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                .equals(TelephonyManager.EXTRA_STATE_RINGING)
        ) {
            status = "ringing"
        }
        return status
    }

    interface IncomingOutgoingCallDetect {
        fun onCallDetect(call: String)
    }

    companion object {
        var incomingOutgoingCallDetect: IncomingOutgoingCallDetect? = null
    }


}