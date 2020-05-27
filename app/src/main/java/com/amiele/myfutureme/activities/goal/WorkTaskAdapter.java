package com.amiele.myfutureme.activities.goal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class WorkTaskAdapter extends RecyclerView.Adapter<WorkTaskAdapter.WorkTaskViewHolder> {
    private ArrayList<WorkTask> workTaskList;
    private static OnItemClickListener listener;
    private Activity activity;
    private int mExpandedPosition = -1;
    private int mAddTaskExpandedPosition = -1;


    public interface OnItemClickListener {
        void onItemClick(Task position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    public static class WorkTaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvOverview;
        public LinearLayout llDetail;
        public ImageButton expandedButton;
        public RecyclerView taskRecycleView;
        public ImageButton addTaskButton;
        public LinearLayout addTaskLayout;
        public ImageButton cancelAddTaskButton;
        public ImageButton finishAddTaskButton;

        public  WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.txt_work_name);
            tvDescription = itemView.findViewById(R.id.txt_work_description);
            tvOverview = itemView.findViewById(R.id.txt_work_overview);
            expandedButton = itemView.findViewById(R.id.expanded_icon);
            llDetail = itemView.findViewById(R.id.detail_layout);
            taskRecycleView = itemView. findViewById(R.id.task_recycler_view);
            addTaskButton = itemView.findViewById(R.id.action_add_task);
            addTaskLayout = itemView.findViewById(R.id.add_task_layout);
            cancelAddTaskButton = itemView.findViewById(R.id.action_cancel_add_task);
            finishAddTaskButton = itemView.findViewById(R.id.action_finish_add_task);
        }


    }

    public WorkTaskAdapter(Activity activity,ArrayList<WorkTask> workTaskList) {
        this.workTaskList = workTaskList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public WorkTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_work, parent, false);
        return new WorkTaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final WorkTaskViewHolder holder, final int position) {
        WorkTask currentWorkTask = workTaskList.get(position);

        // Assign values for image resource and text of layout
        holder.taskRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        final TaskAdapter adapter = new TaskAdapter(currentWorkTask.getTaskList(),listener);
        holder.taskRecycleView.setLayoutManager(layoutManager);
        holder.taskRecycleView.setAdapter(adapter);

//        adapter.setOnItemClickListener(
//                new TaskAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        adapter.notifyItemChanged(position);
//                        Log.i("info","here here");
//                        holder.itemView.performClick();
//                    }
//                }
//        );


        holder.taskRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        holder.tvName.setText(currentWorkTask.getName());
        holder.tvDescription.setText(currentWorkTask.getDescription());
        final boolean isGoalDetailsExpanded = position==mExpandedPosition;
        if (isGoalDetailsExpanded)
        {
            holder.llDetail.setVisibility(View.VISIBLE);
            holder.expandedButton.setImageResource(android.R.drawable.arrow_up_float);
        }
        else
        {
            holder.llDetail.setVisibility(View.GONE);
            holder.expandedButton.setImageResource(android.R.drawable.arrow_down_float);

        }

        final boolean isAddTaskLayoutExpanded = position==mAddTaskExpandedPosition;
        if (isAddTaskLayoutExpanded)
        {
            holder.addTaskLayout.setVisibility(View.VISIBLE);
            holder.addTaskButton.setVisibility(View.GONE);
        }
        else
        {
            holder.addTaskLayout.setVisibility(View.GONE);
            holder.addTaskButton.setVisibility(View.VISIBLE);
        }

        holder. itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isGoalDetailsExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });

        holder.addTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAddTaskExpandedPosition = position;
                notifyItemChanged(position);
            }
        });

        holder.cancelAddTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAddTaskExpandedPosition = -1;
                notifyItemChanged(position);
            }
        });

        holder.finishAddTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                workTaskList.get(position).addTask("new Task");
                mAddTaskExpandedPosition = -1;
                notifyItemChanged(position);
            }
        });


    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        return workTaskList.size();
    }


}
