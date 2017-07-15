package beacon.example.com.beaconlibrary;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 3/4/17.
 * email: jmanuel_munoz@iecisa.com
 */


public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private RegionBootstrap regionBootstrap;

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("350296c5-5eec-4870-9766-d00153d45e6f"),
                        25508, 33190));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showBeaconNotification(
                        "Your gate closes in 47 minutes.",
                        "Current security wait time is 15 minutes, "
                                + "and it's a 5 minute walk from security to the gate. "
                                + "Looks like you've got plenty of time!");
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });
    }

    public void showBeaconNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);
    }

    /*@Override
    public void onCreate() {
        super.onCreate();
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));



        Region region = new Region("beacon.example.com.beaconlibrary",
                Identifier.parse("350296c5-5eec-4870-9766-d00153d45e6f"),
                Identifier.parse("6f6b29c3-6ec0-42b2-9ea3-ab70df92e54f"), null);
        regionBootstrap = new RegionBootstrap(this, region);

    }

    @Override
    public void didEnterRegion(Region region) {
        Log.e(TAG, "did enter region.");
        Toast.makeText(this, "did enter region.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void didExitRegion(Region region) {
        Log.e(TAG, "did exit region.");
        Toast.makeText(this, "did exit region.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void didDetermineStateForRegion(int var1, Region region) {
        Log.e(TAG, "did determine state for region.");
        Toast.makeText(this, "did determine state for region.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.e(TAG, "on Service Connect");
        Toast.makeText(this, "on service connect", Toast.LENGTH_LONG).show();
    }*/
}
