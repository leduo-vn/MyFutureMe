package com.amiele.myfutureme.activities.goal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> mTaskList;
    private static TaskAdapter.OnItemClickListener mListener = null;
    private static GoalAdapter.OnItemClickListener mGoalListener = null;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton iBtnEdit;
        public ProgressBar pbProgress;

        public  TaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.recycle_view_task_tv_description);
            iBtnEdit = itemView.findViewById((R.id.recycle_view_task_ibtn_edit));
            pbProgress = itemView.findViewById(R.id.recycle_view_pb_progress);

        }

    }

    public TaskAdapter(ArrayList<Task> taskList) {
        mTaskList = taskList;
    }

    public TaskAdapter(ArrayList<Task> taskList, GoalAdapter.OnItemClickListener goalListener)
    {
        mTaskList = taskList;
        mGoalListener = goalListener;
    }

    public void setTaskList(List<Task> taskList)
    {
        this.mTaskList = (ArrayList<Task>) taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_task, parent, false);
        return new TaskAdapter.TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, final int position) {
        Task currentTask = mTaskList.get(position);
        holder.tvName.setText(currentTask.getName());

        // Set onClick Event
        holder.iBtnEdit.setOnClickListener(v -> {
            if (currentTask!=null && mGoalListener !=null)
                    mGoalListener.onItemClick(currentTask);

            else
                if (currentTask!=null && mListener !=null)
                   mListener.onItemClick(currentTask);
        });

    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        if (mTaskList == null) return 0;
        return mTaskList.size();
    }

}
