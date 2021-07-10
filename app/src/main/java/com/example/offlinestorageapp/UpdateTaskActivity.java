package com.example.offlinestorageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.offlinestorageapp.Room.DatabaseClient;
import com.example.offlinestorageapp.Room.Task;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishBy;
    private CheckBox checkBoxFinished;
    private Button btnUpdate, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);
        btnUpdate = findViewById(R.id.button_update);
        checkBoxFinished = findViewById(R.id.checkBoxFinished);
        btnDelete = findViewById(R.id.button_delete);
        final Task task= (Task) getIntent().getSerializableExtra("task");;

        loadTask(task);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask(task);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("You sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteTask(task);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void updateTask(final Task task1) {
        final String taskText = editTextTask.getText().toString().trim();
        final String textDesc = editTextDesc.getText().toString().trim();
        final String textFinish = editTextFinishBy.getText().toString().trim();
        if (taskText.isEmpty() || textDesc.isEmpty() || textFinish.isEmpty()){
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }
        class UpdateTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                task1.setTask(taskText);
                task1.setDesc(textDesc);
                task1.setFinishBy(textFinish);
                task1.setFinished(checkBoxFinished.isChecked());

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .updateData(task1);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();
    }

    private void deleteTask(final Task task2) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .deleteData(task2);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    private void loadTask(final Task task3) {
        editTextTask.setText(task3.getTask());
        editTextDesc.setText(task3.getDesc());
        editTextFinishBy.setText(task3.getFinishBy());
        checkBoxFinished.setChecked(task3.isFinished());
    }
}