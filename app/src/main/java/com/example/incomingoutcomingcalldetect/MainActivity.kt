package com.example.incomingoutcomingcalldetect

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),MyService.IncomingOutgoingCallDetect {
    private  var intentCallDetect = MyService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callPermission()
        dateConv()
    }
    private fun dateConv(){
        val myDate = "11/12/2022 5:02:00 PM"
        val sdf = SimpleDateFormat("MM/dd/yyyy h:mm:ss a", Locale.getDefault())
        val date: Date? = sdf.parse(myDate)
        val millis: Long? = date?.time
        Log.d("Date in milli", "$millis")
        val myDateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
        val dateString = myDateFormat.format(millis)
        Log.d("dateString",dateString)
    }
    private fun callPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),1
            )
        }
    }

    override fun onCallDetect(callStatus: String) {
        when (callStatus) {
            "callStarted" -> Toast.makeText(this, "Call Started...", Toast.LENGTH_LONG).show()
            "callEnded" -> Toast.makeText(this, "Call ended..", Toast.LENGTH_LONG).show()
            "ringing" -> Toast.makeText(this, "Ringing...", Toast.LENGTH_LONG).show()
        }
    }
    override fun onPostResume() {
        super.onPostResume()
        registerReceiver(
            intentCallDetect,
            IntentFilter(TelephonyManager.EXTRA_STATE)
        )
        MyService.incomingOutgoingCallDetect = this
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(intentCallDetect)
    }
}