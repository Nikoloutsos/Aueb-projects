package com.example.studytracker;

import android.app.Application;
import android.content.Context;

import com.example.studytracker.dao.memorydao.MemoryInitializer;

/**
 * Used for running block of code in every cold boot.
 * e.g inject mock objects in DAOs
 */
public class App extends Application {
    public static MemoryInitializer memoryInitializer;
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();

        appContext = getApplicationContext();
    }
}
