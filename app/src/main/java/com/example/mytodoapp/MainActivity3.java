package com.example.mytodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private EditText taskInput;
    private TaskAdapter taskAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        taskInput = findViewById(R.id.taskInput);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button addButton = findViewById(R.id.addButton);

        // SharedPreference setup
        sharedPreferencesManager = new SharedPreferencesManager(this);
        taskList = sharedPreferencesManager.loadTasks();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(taskAdapter);
        // Set the CheckBox to checked
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            TaskAdapter.TaskViewHolder holder = (TaskAdapter.TaskViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                holder.taskCheckBox.setChecked(task.isCompleted());
            }
        }

        // Handle adding new tasks
        addButton.setOnClickListener(view -> {
            String taskDescription = taskInput.getText().toString().trim();
            if (!taskDescription.isEmpty()) {
                Task newTask = new Task(taskDescription);
                taskList.add(newTask); // Add task to list
                taskAdapter.notifyItemInserted(taskList.size() - 1); // Notify adapter
                taskInput.setText(""); // Clear input field
                sharedPreferencesManager.saveTasks(taskList); // Save updated task list to SharedPreferences
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload tasks when activity resumes
        List<Task> newTaskList = sharedPreferencesManager.loadTasks();
        if (newTaskList.size() > taskList.size()) {
            // Items were added
            taskAdapter.notifyItemRangeInserted(taskList.size(), newTaskList.size() - taskList.size());
        } else if (newTaskList.size() < taskList.size()) {
            // Items were removed
            taskAdapter.notifyItemRangeRemoved(newTaskList.size(), taskList.size() - newTaskList.size());
        } else {
            // Items were changed
            for (int i = 0; i < newTaskList.size(); i++) {
                if (!newTaskList.get(i).equals(taskList.get(i))) {
                    taskAdapter.notifyItemChanged(i);
                }
            }
        }
        taskList.clear();
        taskList.addAll(newTaskList);
    }
}