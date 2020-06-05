package com.amiele.myfutureme.activities.main.goal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.main.goal.task.TaskAdapter;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
import com.amiele.myfutureme.helpers.DateConverter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.WorkTaskViewHolder> {
    private ArrayList<Goal> mGoalList;
    private OnItemClickListener mListener;
    private Activity mActivity;
    private int mExpandedPosition = -1;

    static class WorkTaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        TextView tvDueDate;
        LinearLayout llDetail;
        ImageButton iBtnDetailExpand;
        RecyclerView rvTask;
        ImageButton iBtnEdit;
        FlexboxLayout fblTag;
        ProgressBar pbTime;
        ProgressBar pbWorkLoad;

        WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.recycle_view_goal_tv_name);
            tvDescription = itemView.findViewById(R.id.recycle_view_goal_tv_description);
            tvDueDate = itemView.findViewById(R.id.recycle_view_goal_tv_due_date);
            iBtnDetailExpand = itemView.findViewById(R.id.recycle_view_goal_ibtn_detail_expand);
            llDetail = itemView.findViewById(R.id.recycle_view_goal_ll_detail);
            rvTask = itemView. findViewById(R.id.recycle_view_goal_rv_task);
            fblTag = itemView.findViewById(R.id.recycle_view_goal_fl_tag);
            iBtnEdit = itemView.findViewById(R.id.recycle_view_goal_iBtn_goal_update);
            pbTime = itemView.findViewById(R.id.recycle_view_goal_pb_time_progres);
            pbWorkLoad = itemView.findViewById(R.id.recycle_view_goal_pb_work_load_progres);
        }
    }

    GoalAdapter(Activity activity, ArrayList<Goal> goalList) {
        this.mGoalList = goalList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public WorkTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_goal, parent, false);
        return new WorkTaskViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final WorkTaskViewHolder holder, final int position) {
        // get current goal
        Goal currentGoal = mGoalList.get(position);

        // Set adapter and layout for task recycle view
        holder.rvTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        final TaskAdapter adapter = new TaskAdapter(currentGoal.getTaskList(), mListener);
        holder.rvTask.setLayoutManager(layoutManager);
        holder.rvTask.setAdapter(adapter);

        // Enable the scroll inside the task recycle view
        holder.rvTask.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                if (action == MotionEvent.ACTION_MOVE)
                    rv.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        // calculate the time left towards the due date and set for the progress bar
        int timeProgress = CalculateTimeProgress(currentGoal.getDueDate(),currentGoal.getCreatedDate());
        holder.pbTime.setSecondaryProgress(timeProgress);

        // Calculate the workload ad set for the progress bar
        int workLoadProgress=CalculateWorkLoadProgress(currentGoal.getTaskList());
        holder.pbWorkLoad.setSecondaryProgress(workLoadProgress);

        // Display the tag lists
        if (holder.fblTag.getChildCount() > 0)
            holder.fblTag.removeAllViews();
        for (Tag tag: currentGoal.getTagList())
        {
           TextView tv = new TextView(mActivity);
           tv.setText(tag.getName());
           tv.setBackgroundColor(tag.getColor());
           tv.setPadding(10,10,10,10);
           tv.setTextColor(Color.WHITE);
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
           params.setMargins(10,10,10,10);
           tv.setLayoutParams(params);
           holder.fblTag.addView(tv);
        }

        //Set name, description and due date for goal
        holder.tvName.setText(currentGoal.getName());
        holder.tvDescription.setText(currentGoal.getDescription());
        String date = currentGoal.getCreatedDate() + " -> " + currentGoal.getDueDate();
        holder.tvDueDate.setText(date);

        // If expandable mode is enabled then show detail of the goal
        // if not, disappear the detail of the goal
        final boolean isGoalDetailsExpanded = position==mExpandedPosition;
        if (isGoalDetailsExpanded)
        {
            holder.llDetail.setVisibility(View.VISIBLE);
            holder.iBtnDetailExpand.setImageResource(android.R.drawable.arrow_up_float);
        }
        else
        {
            holder.llDetail.setVisibility(View.GONE);
            holder.iBtnDetailExpand.setImageResource(android.R.drawable.arrow_down_float);
        }


        holder.itemView.setOnClickListener(v -> {
            mExpandedPosition = isGoalDetailsExpanded ? -1:position;
            notifyItemChanged(position);
        });

        holder.iBtnDetailExpand.setOnClickListener(v->{
            mExpandedPosition = isGoalDetailsExpanded ? -1:position;
            notifyItemChanged(position);
        });

        holder.iBtnEdit.setOnClickListener(v ->{
            if (mListener!=null)
                mListener.onItemClick(currentGoal);
        });

    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        if (mGoalList==null) return 0;
            return mGoalList.size();
    }

    void setGoalList(List<Goal> goals)
    {
        mGoalList = (ArrayList<Goal>) goals;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // Calculate the time progress
    private int CalculateTimeProgress(String dueDate,String createdDate)
    {
        Date d = new Date();
        String currentDate = DateConverter.ConvertFromDateToString(d);
        // the number of day between the created goal date and the due date
        long createDiff = DateConverter.getDaysDifferentFromStringDate(dueDate,createdDate);
        // the number of day between the current date and due date
        long currentDiff = DateConverter.getDaysDifferentFromStringDate(dueDate,currentDate);

        int timeProgress;
        // get the percentage to calculate the progress
        if (createDiff==0) timeProgress =100;
        else timeProgress = 100 - (int) (currentDiff *100/createDiff);

        return timeProgress;
    }

    // Calculate the workload by get all the workload from the task
    private int CalculateWorkLoadProgress(ArrayList<Task> tasks)
    {
        int workLoadProgress = 100;
        if (tasks!=null)
        {
            int workDone = 0;
            for (Task task:tasks)
                workDone+=task.getProgress();

            if (tasks.size()!=0) {
                int fullProgress = tasks.size() *100;
                workLoadProgress = (workDone *100 / fullProgress) ;
            }
        }

        return workLoadProgress;
    }

    public interface OnItemClickListener {
        void onItemClick(Task position);

        void onItemClick(Goal goal);
    }


}

//        final boolean isAddTaskLayoutExpanded = position==mAddTaskExpandedPosition;
//        if (isAddTaskLayoutExpanded)
//        {
//            holder.llAddTask.setVisibility(View.VISIBLE);
//            holder.iBtnTaskAdd.setVisibility(View.GONE);
//        }
//        else
//        {
//            holder.llAddTask.setVisibility(View.GONE);
//            holder.iBtnTaskAdd.setVisibility(View.VISIBLE);
//        }

//        holder.iBtnTaskAdd.setOnClickListener(v -> {
//                mAddTaskExpandedPosition = position;
//                notifyItemChanged(position);
//                });
//
//                holder.iBtnAddTaskCancel.setOnClickListener(v -> {
//                mAddTaskExpandedPosition = -1;
//                notifyItemChanged(position);
//                });
//
//                holder.iBtnAddTaskDone.setOnClickListener(v -> {
//
//                Task task = new Task(currentGoal.getId(), "task name", 70);
//                mGoalList.get(position).addTask("new Task");
//                mAddTaskExpandedPosition = -1;
//
//                });
