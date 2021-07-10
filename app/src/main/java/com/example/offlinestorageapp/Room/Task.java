package com.example.offlinestorageapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "task_details")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String task;
    private String desc;
    private String finishBy;
    private boolean finished;

    public Task(){}

    public Task(String task, String desc, String finishBy, boolean finished) {
        this.task = task;
        this.desc = desc;
        this.finishBy = finishBy;
        this.finished = finished;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(String finishBy) {
        this.finishBy = finishBy;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
