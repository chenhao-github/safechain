package com.code.safechain.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @Auther: hchen
 * @Date: 2020/7/30 0030
 * @Description:
 */
public class DeviceIdFactory {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static volatile UUID uuid;
    private static volatile DeviceIdFactory mInstance;

    private DeviceIdFactory(Context context) {
        if (uuid == null) {
            synchronized (DeviceIdFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        // Use the ids previously computed and stored in the
                        // prefs file
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                //如果没有授权
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                    String serial = null;
                                    try {
                                        serial = Build.class.getField("SERIAL").get(null).toString();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (NoSuchFieldException e) {
                                        e.printStackTrace();
                                    }
                                    String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

                                    uuid = new UUID(m_szDevIDShort.hashCode(), serial.hashCode());

                                } else {
                                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                    uuid = deviceId != null ? UUID
                                            .nameUUIDFromBytes(deviceId
                                                    .getBytes("utf8")) : UUID.randomUUID();
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        // Write the value out to the prefs file
                        prefs.edit()
                                .putString(PREFS_DEVICE_ID, uuid.toString())
                                .commit();
                    }
                }
            }
        }
    }

    public static DeviceIdFactory getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DeviceIdFactory.class) {
                if (mInstance == null) {
                    mInstance = new DeviceIdFactory(context);
                }
            }
        }
        return mInstance;
    }

    public String  getDeviceUuid() {
        LoggerUtil.logI("DeviceId","getDeviceUuid "+uuid.toString());
        return uuid.toString();
    }
}
