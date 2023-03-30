package com.example.fyproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fyproject.DataAccess.MED_DataAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlarmReceiver extends BroadcastReceiver {
    private MED_DataAccess MEDDataAccess;
    private DatabaseReference reference;
    private String currentUserId;

    @Override
    public void onReceive(Context context, Intent intent) {

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //accesses the firebase to get the name of the medicine for the current user
        reference = FirebaseDatabase.getInstance().getReference("MEDHandler").child(currentUserId).child("Medicine");
        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String med = snapshot.child("Medicine").getValue(String.class);

                Intent i = new Intent(context, Alarm.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

                //styling the notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "wellbeing")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Well-being Alarm Manager")
                        .setContentText("Medicine Reminder: " + med)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);

                //used to display the notification
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "wellbeing";
                    NotificationChannel channel = new NotificationChannel(channelId, "wellbeing", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }

                notificationManager.notify(123, builder.build());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(AlarmReceiver.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

    }
}
