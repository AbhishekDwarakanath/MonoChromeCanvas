package com.innominds.kitemonochrome.network;

/**
 * Created by adwarakanath on 17/8/17.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CustomPhoneStateListener extends PhoneStateListener {
    Context mContext;
    public static String LOG_TAG = "CustomPhoneStateListener";

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        Log.i(LOG_TAG, "onCellInfoChanged: " + cellInfo);
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        switch (direction) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_IN");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                break;
            default:
                Log.w(LOG_TAG, "onDataActivity: UNKNOWN " + direction);
                break;
        }
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        Log.i(LOG_TAG, "onServiceStateChanged: " + serviceState.toString());
        Log.i(LOG_TAG, "onServiceStateChanged: getOperatorAlphaLong "
                + serviceState.getOperatorAlphaLong());
        Log.i(LOG_TAG, "onServiceStateChanged: getOperatorAlphaShort "
                + serviceState.getOperatorAlphaShort());
        Log.i(LOG_TAG, "onServiceStateChanged: getOperatorNumeric "
                + serviceState.getOperatorNumeric());
        Log.i(LOG_TAG, "onServiceStateChanged: getIsManualSelection "
                + serviceState.getIsManualSelection());
        Log.i(LOG_TAG,
                "onServiceStateChanged: getRoaming "
                        + serviceState.getRoaming());

        switch (serviceState.getState()) {
            case ServiceState.STATE_IN_SERVICE:
                Log.i(LOG_TAG, "onServiceStateChanged: STATE_IN_SERVICE");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                Log.i(LOG_TAG, "onServiceStateChanged: STATE_OUT_OF_SERVICE");
                break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                Log.i(LOG_TAG, "onServiceStateChanged: STATE_EMERGENCY_ONLY");
                break;
            case ServiceState.STATE_POWER_OFF:
                Log.i(LOG_TAG, "onServiceStateChanged: STATE_POWER_OFF");
                break;
        }
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_IDLE");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");
                break;
            default:
                Log.i(LOG_TAG, "UNKNOWN_STATE: " + state);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if (location instanceof GsmCellLocation) {
            GsmCellLocation gcLoc = (GsmCellLocation) location;
            Log.i(LOG_TAG,
                    "onCellLocationChanged: GsmCellLocation "
                            + gcLoc.toString());
            Log.i(LOG_TAG, "onCellLocationChanged: GsmCellLocation getCid "
                    + gcLoc.getCid());
            Log.i(LOG_TAG, "onCellLocationChanged: GsmCellLocation getLac "
                    + gcLoc.getLac());
            Log.i(LOG_TAG, "onCellLocationChanged: GsmCellLocation getPsc"
                    + gcLoc.getPsc()); // Requires min API 9
        } else if (location instanceof CdmaCellLocation) {
            CdmaCellLocation ccLoc = (CdmaCellLocation) location;
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation "
                            + ccLoc.toString());
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationId "
                            + ccLoc.getBaseStationId());
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
                            + ccLoc.getBaseStationLatitude());
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
                            + ccLoc.getBaseStationLongitude());
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation getNetworkId "
                            + ccLoc.getNetworkId());
            Log.i(LOG_TAG,
                    "onCellLocationChanged: CdmaCellLocation getSystemId "
                            + ccLoc.getSystemId());
        } else {
            Log.i(LOG_TAG, "onCellLocationChanged: " + location.toString());
        }
    }

    /**
     * In this method Java Reflection API is being used please see link before
     * using.
     *
     * @see <a
     *      href="http://docs.oracle.com/javase/tutorial/reflect/">http://docs.oracle.com/javase/tutorial/reflect/</a>
     *
     */
    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        Log.i(LOG_TAG, "onSignalStrengthsChanged: " + signalStrength);
        if (signalStrength.isGsm()) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                    + signalStrength.getGsmBitErrorRate());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                    + signalStrength.getGsmSignalStrength());
        } else if (signalStrength.getCdmaDbm() > 0) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaDbm "
                    + signalStrength.getCdmaDbm());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getCdmaEcio "
                    + signalStrength.getCdmaEcio());
        } else {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoDbm "
                    + signalStrength.getEvdoDbm());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoEcio "
                    + signalStrength.getEvdoEcio());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getEvdoSnr "
                    + signalStrength.getEvdoSnr());
        }

        // Reflection code starts from here
        try {
            Method[] methods = SignalStrength.class
                    .getMethods();
            for (Method mthd : methods) {
                if (mthd.getName().equals("getLteSignalStrength")
                        || mthd.getName().equals("getLteRsrp")
                        || mthd.getName().equals("getLteRsrq")
                        || mthd.getName().equals("getLteRssnr")
                        || mthd.getName().equals("getLteCqi")) {


                    Log.i(LOG_TAG,
                            "onSignalStrengthsChanged:LTE " + mthd.getName() + " "
                                    + mthd.invoke(signalStrength));

                }

                if(mthd.getName().equals("getLteSignalStrength")){
                    Intent i = new Intent("com.innominds.Monochrome.NETWORKSIGNALINFO");
                    int mSignalStrength = (int) mthd.invoke(signalStrength);
                    i.putExtra("mSignalStrength",mSignalStrength);
                    mContext.sendBroadcast(i);

                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // Reflection code ends here
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        super.onCallForwardingIndicatorChanged(cfi);
        Log.i(LOG_TAG, "onCallForwardingIndicatorChanged: " + cfi);
    }

    @Override
    public void onMessageWaitingIndicatorChanged(boolean mwi) {
        super.onMessageWaitingIndicatorChanged(mwi);
        Log.i(LOG_TAG, "onMessageWaitingIndicatorChanged: " + mwi);
    }
}
