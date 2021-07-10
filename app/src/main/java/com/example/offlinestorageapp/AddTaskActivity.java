package com.example.offlinestorageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.offlinestorageapp.Room.DatabaseClient;
import com.example.offlinestorageapp.Room.Task;

public class AddTaskActivity extends AppCompatActivity {

    EditText enterTask, taskDesc, taskFinish;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        enterTask = findViewById(R.id.enterTask);
        taskDesc = findViewById(R.id.taskDesc);
        taskFinish = findViewById(R.id.finishBy);
        saveBtn = findViewById(R.id.btnSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String taskName = enterTask.getText().toString().trim();
        final String desc = taskDesc.getText().toString().trim();
        final String finishBy = taskFinish.getText().toString().trim();

        if (taskName.isEmpty() || desc.isEmpty() || finishBy.isEmpty()){
            enterTask.setError("Task Name required..!");
            enterTask.requestFocus();
            taskDesc.setError("Task Description required..!");
            taskDesc.requestFocus();
            taskFinish.setError("finish by time required..!");
            taskFinish.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setTask(taskName);
                task.setDesc(desc);
                task.setFinishBy(finishBy);
                task.setFinished(false);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .insertData(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask task = new SaveTask();
        task.execute();
    }
}