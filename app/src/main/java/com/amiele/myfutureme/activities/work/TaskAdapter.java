package com.amiele.myfutureme.activities.work;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    private static TaskAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescription;
        public ImageView imageView;

        public  TaskViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.txt_task_description);
            imageView = itemView.findViewById((R.id.imageView1));

            // Set onClick Event
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            Log.i("info","here");
                        }
                    }
                }
            });

        }


    }

    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }



    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_task, parent, false);



        return new TaskAdapter.TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, final int position) {
        Task currentTask = taskList.get(position);
        holder.tvDescription.setText(currentTask.getDescription());



    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

}
