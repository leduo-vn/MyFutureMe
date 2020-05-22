package com.amiele.myfutureme.activities.work;

import android.app.Activity;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public interface OnItemClickListener {
        void onItemClick(int position);
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

        public  WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.txt_work_name);
            tvDescription = itemView.findViewById(R.id.txt_work_description);
            tvOverview = itemView.findViewById(R.id.txt_work_overview);
            expandedButton = itemView.findViewById(R.id.expanded_icon);
            llDetail = itemView.findViewById(R.id.detail_layout);
            taskRecycleView = itemView. findViewById(R.id.task_recycler_view);

            // Set onClick Event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
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
        final TaskAdapter adapter = new TaskAdapter(currentWorkTask.getTaskList());
        holder.taskRecycleView.setLayoutManager(layoutManager);
        holder.taskRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(
                new TaskAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        adapter.notifyItemChanged(position);
                        Log.i("info","here here");
                        holder.itemView.performClick();
                    }
                }
        );

        holder.tvName.setText(currentWorkTask.getName());
        holder.tvDescription.setText(currentWorkTask.getDescription());
        final boolean isExpanded = position==mExpandedPosition;
        if (isExpanded)
        {
            holder.llDetail.setVisibility(View.VISIBLE);
            holder.expandedButton.setImageResource(android.R.drawable.arrow_up_float);
        }
        else
        {
            holder.llDetail.setVisibility(View.GONE);
            holder.expandedButton.setImageResource(android.R.drawable.arrow_down_float);

        }
//        holder.llDetail.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder. itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                Log.i("info","clicked");
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
