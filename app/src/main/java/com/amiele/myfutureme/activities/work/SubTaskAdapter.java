package com.amiele.myfutureme.activities.work;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;

import java.util.ArrayList;

public class SubTaskAdapter  extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {
    private ArrayList<Task.SubTask> subTaskList;
    private static TaskAdapter.OnItemClickListener listener= null;

//    public interface OnItemClickListener {
//        void onItemClick(Task task);
//    }
//
//    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
//        this.listener = listener;
//    }



    public static class SubTaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescription;
        public TextView tvDate;
        public TextView tvDOW;

        public TextView tvHour;
     //   public ImageView imageView;

        public  SubTaskViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.txt_sub_task_description);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDOW = itemView.findViewById(R.id.txt_dow);
            tvHour = itemView.findViewById(R.id.txt_hour);
            //imageView = itemView.findViewById((R.id.imageView1));

//            // Set onClick Event
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                            Log.i("info","here");
//                        }
//                    }
//                }
//            });



        }


    }

    public SubTaskAdapter(ArrayList<Task.SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    @NonNull
    @Override
    public SubTaskAdapter.SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_subtask, parent, false);



        return new SubTaskAdapter.SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        Task.SubTask currentSubTask = subTaskList.get(position);
        holder.tvDescription.setText(currentSubTask.getDescription());
//        holder.tvDate.setText(currentSubTask.getDate());

        holder.tvDOW.setText(currentSubTask.getDate_DOW());
        holder.tvDate.setText(currentSubTask.getDate_Date());
        holder.tvHour.setText(Integer.toString(currentSubTask.getHour()));

    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        return subTaskList.size();
    }

}

