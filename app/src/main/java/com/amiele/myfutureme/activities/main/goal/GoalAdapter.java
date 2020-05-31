package com.amiele.myfutureme.activities.main.goal;

import android.app.Activity;
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
import androidx.room.Database;

import com.amiele.myfutureme.AppRepo;
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
    private static OnItemClickListener mListener;
    private Activity mActivity;
    private int mExpandedPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(Task position);

        void onItemClick(Goal goal);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }



    public static class WorkTaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvOverview;
        public LinearLayout llDetail;
        public ImageButton iBtnDetailExpand;
        public RecyclerView rvTask;
        public ImageButton iBtnEdit;
        public FlexboxLayout fblTag;
        public ProgressBar pbTime;
        public ProgressBar pbWorkLoad;

        public  WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.recycle_view_goal_tv_name);
            tvDescription = itemView.findViewById(R.id.recycle_view_goal_tv_description);
            tvOverview = itemView.findViewById(R.id.recycle_view_goal_tv_overview);
            iBtnDetailExpand = itemView.findViewById(R.id.recycle_view_goal_ibtn_detail_expand);
            llDetail = itemView.findViewById(R.id.recycle_view_goal_ll_detail);
            rvTask = itemView. findViewById(R.id.recycle_view_goal_rv_task);
            fblTag = itemView.findViewById(R.id.recycle_view_goal_fl_tag);
            iBtnEdit = itemView.findViewById(R.id.edit_icon);

            pbTime = itemView.findViewById(R.id.time_progress_bar);
            pbWorkLoad = itemView.findViewById(R.id.work_load_progress_bar);
        }
    }

    public GoalAdapter(Activity activity, ArrayList<Goal> goalList) {
        this.mGoalList = goalList;
        this.mActivity = activity;
    }

    public void setGoalList(List<Goal> goals)
    {
        mGoalList = (ArrayList<Goal>) goals;
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
        Goal currentGoal = mGoalList.get(position);

        holder.rvTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        final TaskAdapter adapter = new TaskAdapter(currentGoal.getTaskList(), mListener);
        holder.rvTask.setLayoutManager(layoutManager);
        holder.rvTask.setAdapter(adapter);

        holder.rvTask.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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

        Date d = new Date();
        String currentDate = DateConverter.ConvertFromDateToString(d);
        long createDiff = DateConverter.getDaysDifferentFromStringDate(currentGoal.getDueDate(),currentGoal.getCreatedDate());
        long currentDiff = DateConverter.getDaysDifferentFromStringDate(currentGoal.getDueDate(),currentDate);


        int timeProgress;
        if (createDiff==0) timeProgress =0;
        else timeProgress = 100 - (int) (currentDiff *100/createDiff);

        holder.pbTime.setSecondaryProgress(timeProgress);


        int workLoadProgress=100;
        if (currentGoal.getTaskList()!=null)
        {
            int workDone = 0;
            for (Task task:currentGoal.getTaskList())
                workDone+=task.getProgress();

            if (currentGoal.getTaskList().size()!=0) {
                int fullProgress = currentGoal.getTaskList().size() *100;
                workLoadProgress = (workDone *100 / fullProgress) ;
            }
        }

        holder.pbWorkLoad.setSecondaryProgress(workLoadProgress);

        if (holder.fblTag.getChildCount() > 0)
            holder.fblTag.removeAllViews();
        for (Tag tag: currentGoal.getTagList())
        {
           TextView tv = new TextView(mActivity);
           tv.setText(tag.getName());
           tv.setBackgroundColor(tag.getColor());
//           tv.setBackground(mActivity.getDrawable(R.drawable.rounded_purple_border));
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
           params.setMargins(10,10,10,10);
           tv.setLayoutParams(params);
           holder.fblTag.addView(tv);
        }

        holder.tvName.setText(currentGoal.getName());
        holder.tvDescription.setText(currentGoal.getDescription());
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
