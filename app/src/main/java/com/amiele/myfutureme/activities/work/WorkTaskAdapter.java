package com.amiele.myfutureme.activities.work;

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
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class WorkTaskAdapter extends RecyclerView.Adapter<WorkTaskAdapter.WorkTaskViewHolder> {
    private ArrayList<WorkTask> workTaskList;
    private OnItemClickListener listener;
    private int mExpandedPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    public static class WorkTaskViewHolder extends RecyclerView.ViewHolder {
        private int DetailVisibility;
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvOverview;
        public LinearLayout llDetail;
        public ImageView imageView;

        public  WorkTaskViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.txt_work_name);
            tvDescription = itemView.findViewById(R.id.txt_work_description);
            tvOverview = itemView.findViewById(R.id.txt_work_overview);
            imageView = itemView.findViewById(R.id.imageView);
            llDetail = itemView.findViewById(R.id.detail_layout);


//            // Set onClick Event
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
////                            if (DetailVisibility == View.VISIBLE)
////                                    llDetail.setVisibility(View.GONE);
////                            else llDetail.setVisibility(View.VISIBLE);
//
//                        }
//                    }
//                }
//            });
        }


    }

    public WorkTaskAdapter(ArrayList<WorkTask> workTaskList) {
        this.workTaskList = workTaskList;
    }



    @NonNull
    @Override
    public WorkTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_work, parent, false);
        return new WorkTaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WorkTaskViewHolder holder, final int position) {
        WorkTask currentWorkTask = workTaskList.get(position);

        // Assign values for image resource and text of layout
        holder.tvName.setText(currentWorkTask.getName());
        holder.tvDescription.setText(currentWorkTask.getDescription());
        final boolean isExpanded = position==mExpandedPosition;
        holder.llDetail.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mExpandedPosition = isExpanded ? -1:position;
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
