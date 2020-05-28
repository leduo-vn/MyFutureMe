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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.WorkTaskViewHolder> {
    private ArrayList<Goal> mGoalList;
    private static OnItemClickListener mListener;
    private Activity mActivity;
    private int mExpandedPosition = -1;
    private int mAddTaskExpandedPosition = -1;
    AppRepo appRepo;
    GoalViewModel goalViewModel;

    public interface OnItemClickListener {
        void onItemClick(Task position);
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
        public ImageButton iBtnTaskAdd;
        public LinearLayout llAddTask;
        public ImageButton iBtnAddTaskCancel;
        public ImageButton iBtnAddTaskDone;

        public  WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.recycle_view_goal_tv_name);
            tvDescription = itemView.findViewById(R.id.recycle_view_goal_tv_description);
            tvOverview = itemView.findViewById(R.id.recycle_view_goal_tv_overview);
            iBtnDetailExpand = itemView.findViewById(R.id.recycle_view_goal_ibtn_detail_expand);
            llDetail = itemView.findViewById(R.id.recycle_view_goal_ll_detail);
            rvTask = itemView. findViewById(R.id.recycle_view_goal_rv_task);
            iBtnTaskAdd = itemView.findViewById(R.id.recycle_view_goal_ibtn_task_add);
            llAddTask = itemView.findViewById(R.id.recycle_view_goal_ll_add_task);
            iBtnAddTaskCancel = itemView.findViewById(R.id.recycle_view_goal_ibtn_add_task_cancel);
            iBtnAddTaskDone = itemView.findViewById(R.id.recycle_view_goal_ibtn_add_task_done);
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


    @Override
    public void onBindViewHolder(@NonNull final WorkTaskViewHolder holder, final int position) {
        Goal currentGoal = mGoalList.get(position);

        // Assign values for image resource and text of layout
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

        final boolean isAddTaskLayoutExpanded = position==mAddTaskExpandedPosition;
        if (isAddTaskLayoutExpanded)
        {
            holder.llAddTask.setVisibility(View.VISIBLE);
            holder.iBtnTaskAdd.setVisibility(View.GONE);
        }
        else
        {
            holder.llAddTask.setVisibility(View.GONE);
            holder.iBtnTaskAdd.setVisibility(View.VISIBLE);
        }

        holder. itemView.setOnClickListener(v -> {
            mExpandedPosition = isGoalDetailsExpanded ? -1:position;
            notifyItemChanged(position);
        });

        holder.iBtnTaskAdd.setOnClickListener(v -> {
            mAddTaskExpandedPosition = position;
            notifyItemChanged(position);
        });

        holder.iBtnAddTaskCancel.setOnClickListener(v -> {
            mAddTaskExpandedPosition = -1;
            notifyItemChanged(position);
        });

        holder.iBtnAddTaskDone.setOnClickListener(v -> {

            Task task = new Task(currentGoal.getId(), "task name", 70);
          //  goalViewModel.addTask(task);
            mGoalList.get(position).addTask("new Task");
            mAddTaskExpandedPosition = -1;

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
