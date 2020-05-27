package com.amiele.myfutureme.activities.goal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SubTaskAdapter  extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {
    private ArrayList<SubTask> subTaskList;
//    private static TaskAdapter.OnItemClickListener listener= null;
    Activity activity;
    private int mEditModePosition = -1;

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
        public TextView tvDow;
        public TextView tvMinute;
        public ViewSwitcher vsDescription;
        public EditText etDescription;
        public ImageButton iBtnSubTaskUpdate;
        public EditText etMinute;
        public ViewSwitcher vsHour;
        public DatePickerDialog.OnDateSetListener mDateSetListener;
        public LinearLayout llDate;

        public  SubTaskViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.sub_task_recycle_view_tv_description);
            tvDate = itemView.findViewById(R.id.sub_task_recycle_view_tv_date);
            tvDow = itemView.findViewById(R.id.sub_task_recycle_view_tv_dow);
            tvMinute = itemView.findViewById(R.id.sub_task_recycle_view_tv_minute);
            vsDescription = itemView.findViewById(R.id.description_sub_task_switch_view);
            etDescription = itemView.findViewById(R.id.et_sub_task_description);
            iBtnSubTaskUpdate = itemView.findViewById(R.id.action_update_sub_task);
            etMinute = itemView.findViewById(R.id.et_sub_task_hour);
            vsHour = itemView.findViewById(R.id.hour_sub_task_switch_view);
            llDate = itemView.findViewById(R.id.txt_et_sub_task_date);
        }
    }

    public SubTaskAdapter(Activity activity ,ArrayList<SubTask> subTaskList) {
        this.activity = activity;
        this.subTaskList = subTaskList;
    }

    @NonNull
    @Override
    public SubTaskAdapter.SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_sub_task, parent, false);
        return new SubTaskAdapter.SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        SubTask currentSubTask = subTaskList.get(position);
        holder.tvDescription.setText(currentSubTask.getDescription());

        holder.tvDow.setText(currentSubTask.getDate_DOW());
        holder.tvDate.setText(currentSubTask.getDate_Date());
        holder.tvMinute.setText(String.format(Locale.US,"%d",currentSubTask.getMinute()));

        final boolean isEditModeEnabled = position==mEditModePosition;

        if (isEditModeEnabled){
            if (holder.vsDescription.getCurrentView()!= holder.etDescription)
                holder.vsDescription.showNext();
            if (holder.vsHour.getCurrentView()!= holder.etMinute)
                holder.vsHour.showNext();
            holder.iBtnSubTaskUpdate.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else{
            if (holder.vsDescription.getCurrentView()!= holder.tvDescription)
                holder.vsDescription.showPrevious();
            if (holder.vsHour.getCurrentView()!= holder.tvMinute)
                holder.vsHour.showPrevious();
            holder.iBtnSubTaskUpdate.setImageResource((android.R.drawable.ic_menu_edit));
        }

        holder.iBtnSubTaskUpdate.setOnClickListener(arg0 -> {
            mEditModePosition = isEditModeEnabled ? -1 : position;
            notifyItemChanged(position);
        });

        if (isEditModeEnabled) {
            holder.llDate.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, holder.mDateSetListener, year, month, day);
                datePickerDialog.show();
            });

             holder.mDateSetListener = (view, year, month, dayOfMonth) -> {
                 String text= year +"-"+ (month + 1) +"-"+ dayOfMonth;
                 Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
             };
        }
        else holder.llDate.setOnClickListener(null);

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

