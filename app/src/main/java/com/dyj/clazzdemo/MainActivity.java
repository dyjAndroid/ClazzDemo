package com.dyj.clazzdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Looper;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hide:
                String[] strings = clazzHideThree();
                if (strings != null && strings.length > 0) {
                    for (int i = 0; i < strings.length; i++) {
                        Log.d("dyj", "path:" + i + ":" + strings[i]);
                    }
                } else {
                    Log.d("dyj", "string == null !");
                }
                break;
            default:
                Log.d("dyj", "default**********");
        }
    }

    private String[] clazzHide() {
        try {
            //1.获取对象
            StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
            //2.通过对象，获取Class
            Class clazz = storageManager.getClass();
            //3.获取方法
            Method method = clazz.getDeclaredMethod("getVolumePaths");
            //4.对于hide以及私有方法，需要设置可访问
            method.setAccessible(true);
            //5.执行方法
            return (String[]) method.invoke(storageManager, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] clazzHideTwo() {

        try {
            //1.获取Class
            Class clazz = StorageManager.class;
            //2.创建对象
            Constructor constructor = clazz.getConstructor(ContentResolver.class, Looper.class);
            Object obj = constructor.newInstance(getContentResolver(), getMainLooper());
            return clazzCore(clazz, obj);
        } catch (Exception e) {
            Log.d("dyj", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    private String[] clazzHideThree() {
        try {
            Class clazz = Class.forName("android.os.storage.StorageManager");
            Class clazz1 = getClassLoader().loadClass("android.os.storage.StorageManager");
            Constructor constructor = clazz1.getConstructor(ContentResolver.class, Looper.class);
            Object obj = constructor.newInstance(getContentResolver(), getMainLooper());
            return clazzCore(clazz1, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] clazzCore(Class clazz, Object object) {
        try {
            Method method = clazz.getMethod("getVolumePaths");
            //4.对于hide以及私有方法，需要设置可访问
            method.setAccessible(true);
            //5.执行方法
            return (String[]) method.invoke(object, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
