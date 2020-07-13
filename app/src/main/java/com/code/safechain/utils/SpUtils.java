package com.code.safechain.utils;
import android.content.Context;
import android.content.SharedPreferences;
import com.code.safechain.app.BaseApp;

public class SpUtils {
    private final String SP_NAME = "language_setting";
    private final String TAG_LANGUAGE = "language_select";

    private static SpUtils instance;
    private SharedPreferences sp;
    public SpUtils(Context context){
//        sp = BaseApp.baseApp.getSharedPreferences("safechain", Context.MODE_PRIVATE);
        sp = context.getSharedPreferences("safechain", Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance(Context context){
        if(instance == null){
            synchronized (SpUtils.class){
                if(instance == null){
                    instance = new SpUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置数据
     * @param key
     * @param value
     */
    public void setValue(String key,Object value){
        SharedPreferences.Editor editor = sp.edit();
        if(value instanceof String){
            editor.putString(key, (String) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if(value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if(value instanceof Long){
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public String getString(String key){
        return sp.getString(key,"");
    }

    public int getInt(String key){
        return sp.getInt(key,0);
    }

    public Boolean getBoolean(String key){
        return sp.getBoolean(key,false);
    }

    public float getFloat(String key){
        return sp.getFloat(key,0);
    }

    public Long getLong(String key){
        return sp.getLong(key,0);
    }

    /**
     * 记录设置的语言
     * @param select
     */
    public void saveLanguage(int select) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(TAG_LANGUAGE, select);
        edit.commit();
    }
    /**
     * 获得设置的语言
     * @return
     */
    public int getSelectLanguage() {
        return sp.getInt(TAG_LANGUAGE, 0);
    }
}
