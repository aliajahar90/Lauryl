package versatile.project.lauryl.utils

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import versatile.project.lauryl.R
import versatile.project.lauryl.interfaces.OnRegistrationCallback


class Globals {

    companion object{

        fun showToastMsg(context: Context, msg: String){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun showPopoUpDialog(context: Context, titlMsg: String, msg: String){

            val builder = AlertDialog.Builder(context)
            builder.setTitle(titlMsg)
            builder.setMessage(msg)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()

        }

        fun showPopoUpDialog(
            context: Context,
            titlMsg: String,
            msg: String,
            activityFinishSts: Boolean,
            onRegistrationCallback: OnRegistrationCallback
        ){

            val builder = AlertDialog.Builder(context)
            builder.setTitle(titlMsg)
            builder.setMessage(msg)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                dialog.dismiss()
                if(activityFinishSts)
                    onRegistrationCallback.userRegisteredSuccessfully()
            }
            builder.show()

        }

        fun showNotifInOreoAll(context: Context, otpMsg: String) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                makeNotificationChannel(
                    context,
                    "Channel1",
                    "Doctor channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            }

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, "Channel1")
                .setContentTitle("OTP")
                .setContentText("Your OTP: ${otpMsg}")
                .setColor(context.resources.getColor(R.color.colorPrimary))
                .setSmallIcon(R.drawable.laurylogo)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.laurylogo))
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(108 /* ID of notification */, notificationBuilder.build())


        }

        fun sendNotification(context: Context, otpMsg: String) {

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context)
                .setContentTitle("OTP")
                .setColor(context.resources.getColor(R.color.colorPrimary))
                .setContentText("Your OTP: ${otpMsg}")
                .setSmallIcon(R.drawable.laurylogo)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.laurylogo))
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(110 /* ID of notification */, notificationBuilder.build())

        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun makeNotificationChannel(
            context: Context,
            notifId: String,
            notifName: String,
            importanceFlag: Int
        ) {
            var channel = NotificationChannel(notifId, notifName, importanceFlag)
            channel.setShowBadge(true)

            var notificationManager = context.getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel)
            }else{
                Log.d("notification manager is", "_null")
            }
        }

        fun saveStringToPreferences(context: Context, key: String, value: String){
            val laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            val myEditor: SharedPreferences.Editor = laurylPreferences.edit()
            myEditor.putString(key,value)
            myEditor.commit()
        }

        fun saveBoolToPreferences(context: Context, key: String, value: Boolean){
            val laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            val myEditor: SharedPreferences.Editor = laurylPreferences.edit()
            myEditor.putBoolean(key,value)
            myEditor.commit()
        }

        fun getStringFromPreferences(context: Context, key: String):String{
            var result = ""
            val laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            result = laurylPreferences.getString(key,"")!!
            return result
        }

        fun getBoolFromPreferences(context: Context, key: String):Boolean{
            var result = false
            val laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            result = laurylPreferences.getBoolean(key,false)!!
            return result
        }

        fun clearLaurylPrefs(context: Context){
            var laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            val myEditor: SharedPreferences.Editor = laurylPreferences.edit()
            myEditor.clear()
            myEditor.commit()
        }

        fun checkBoolFromPreferences(context: Context, key: String):Boolean{
            var result = false
            val laurylPreferences: SharedPreferences = context.getSharedPreferences(Constants.LAURYL_PREFERENCES, MODE_PRIVATE)
            result = laurylPreferences.contains(key)!!
            return result
        }

    }

}