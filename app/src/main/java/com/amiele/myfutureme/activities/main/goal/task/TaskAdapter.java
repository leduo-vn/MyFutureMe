package com.amiele.myfutureme.activities.main.goal.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.main.goal.GoalAdapter;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> mTaskList;
    private TaskAdapter.OnItemClickListener mListener;
    private GoalAdapter.OnItemClickListener mGoalListener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton iBtnEdit;
        ProgressBar pbProgress;

        TaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.recycle_view_task_tv_description);
            iBtnEdit = itemView.findViewById((R.id.recycle_view_task_ibtn_edit));
            pbProgress = itemView.findViewById(R.id.recycle_view_pb_progress);
        }
    }

    public TaskAdapter(ArrayList<Task> taskList) {
        mTaskList = taskList;
        mListener = null;
        mGoalListener =null;
    }

    public TaskAdapter(ArrayList<Task> taskList, GoalAdapter.OnItemClickListener goalListener)
    {
        mListener = null;
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
        holder.pbProgress.setSecondaryProgress(currentTask.getProgress());

        // Set onClick Event
        holder.iBtnEdit.setOnClickListener(v -> {
            if (mGoalListener != null)
                    mGoalListener.onItemClick(currentTask);
            else
                if (mListener != null)
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
