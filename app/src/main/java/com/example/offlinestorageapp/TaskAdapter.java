package com.example.offlinestorageapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlinestorageapp.Room.Task;

import java.io.Serializable;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    Context context;
    List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tasks_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTask.setText(task.getTask());
        holder.taskDesc.setText(task.getDesc());
        holder.finishBy.setText(task.getFinishBy());

        if (task.isFinished()){
            holder.taskStatus.setText("Completed");
        } else {
            holder.taskStatus.setText("Not Completed");
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskStatus, tvTask, taskDesc, finishBy;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskStatus = itemView.findViewById(R.id.textViewStatus);
            tvTask = itemView.findViewById(R.id.textViewTask);
            taskDesc = itemView.findViewById(R.id.textViewDesc);
            finishBy = itemView.findViewById(R.id.textViewFinishBy);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(context, UpdateTaskActivity.class);
            intent.putExtra("task", task);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}

