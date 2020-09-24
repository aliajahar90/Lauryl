package versatile.project.lauryl.push_notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import versatile.project.lauryl.R
import versatile.project.lauryl.screens.SplashScreen

class FirebaseMessageService: FirebaseMessagingService() {

    var TAG: String = "FirebaseMessageService_"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Received_Successfully!")

        Log.d(TAG,""+remoteMessage!!.sentTime)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotifInOreoAll(remoteMessage)
        } else {
            sendNotification(remoteMessage)
        }

    }

    private fun showNotifInOreoAll(remoteMessage: RemoteMessage?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("Channel1", "Doctor channel", NotificationManager.IMPORTANCE_HIGH)
        }

        val intent = Intent(applicationContext, SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 11 , intent, PendingIntent.FLAG_UPDATE_CURRENT)


        var title = remoteMessage?.notification?.title

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "Channel1")
            .setContentTitle(remoteMessage?.notification?.title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(title))
            .setContentText(title)
            .setColor(resources.getColor(R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(false)
            .setSound(defaultSoundUri)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(108 ,notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotificationChannel(notifId: String, notifName: String, importanceFlag: Int) {
        var channel = NotificationChannel(notifId, notifName, importanceFlag)
        channel.setShowBadge(true)
        var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(remoteMessage: RemoteMessage?) {

        val intent = Intent(applicationContext, SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        var title = remoteMessage?.notification?.title

        val pendingIntent = PendingIntent.getActivity(applicationContext, 11 , intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setContentTitle(remoteMessage?.notification?.title)
            .setColor(resources.getColor(R.color.colorPrimary))
            .setStyle(NotificationCompat.BigTextStyle().bigText(title))
            .setContentText(title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(false)
            .setSound(defaultSoundUri)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(108 ,notificationBuilder.build())

    }


}