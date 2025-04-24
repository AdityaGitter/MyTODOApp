package com.example.mytodoapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "todo_prefs";
    private static final String TASKS_KEY = "tasks";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;


    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTasks(List<Task> taskList){
        String json = gson.toJson(taskList);
        sharedPreferences.edit().putString(TASKS_KEY, json).apply();
    }

    public List<Task> loadTasks(){
        String json = sharedPreferences.getString(TASKS_KEY,"");
        Type type = new TypeToken<ArrayList<Task>>(){}.getType();
        List<Task> taskList = gson.fromJson(json,type);

        return taskList!=null ? taskList : new ArrayList<>();
    }
}