package de.iteratec.schnitzel.client.beacon;

import android.content.Context;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class BeaconFinder extends Observable {

    private Region region = new Region("rid", null, null, null);

    private final double rssiMin = 100;
    private final double rssiMax = 26;
    private final double normalizedMin = 0;
    private final double normalizedMax = 100;

    List<String> currentBeaconUUIDs = new ArrayList<String>();
    public BeaconManager beaconManager;
    public String compareUUID;

    private boolean userInRange = false;
    int tmpRSSI = -1;

    public void stopBeaconSearch(){
        userInRange = false;
        beaconManager.stopRanging(region);
        beaconManager.disconnect();
    }

    public void startBeaconSearch(Observer activity, String uuid, Context context){
        compareUUID = uuid;


        Log.e("TAG-Test", compareUUID + "");
        beaconManager = new BeaconManager(context);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });


        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {

                currentBeaconUUIDs = new ArrayList<String>();
                boolean beaconFound = false;

                for (Beacon b : beacons) {
                    Log.e("TAG-TEST", "Beacon Found:" + b.toString());
                    if (b.getProximityUUID().toString().toLowerCase().equals(compareUUID.toLowerCase())) {
                        Log.e("TAG-TEST", "Correct Beacon Found");

                        beaconFound = true;
                        userInRange = true;
                        tmpRSSI = b.getRssi();
                        break;
                    }
                }

                if (userInRange && !beaconFound){
                    userInRange = false;
                    setChanged();
                    notifyObservers(-1);
                }
                if (beaconFound){
                    Log.e("TAG-TEST", "RSSI: " + tmpRSSI);
                    setChanged();
                    notifyObservers(normalizeRssi(tmpRSSI));
                }

                if (!beaconFound){
                    Log.e("TAG-TEST", "Beacon not found");
                }
            }
        });
    }


    public double normalizeRssi(double currentRssi) {
        currentRssi *= -1;
        return (normalizedMax - normalizedMin) / (rssiMax - rssiMin)
                * (currentRssi - rssiMin) + normalizedMin;
    }

}
