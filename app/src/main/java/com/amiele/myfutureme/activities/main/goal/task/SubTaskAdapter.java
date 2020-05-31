package com.amiele.myfutureme.activities.main.goal.task;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.helpers.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SubTaskAdapter  extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {
    private ArrayList<SubTask> subTaskList;
    private static SubTaskAdapter.OnItemClickListener listener= null;
    Activity activity;
//    private int mEditModePosition = -1;

    public interface OnItemClickListener {
        void onItemClick(SubTask subTask, String action);
    }

    public void setOnItemClickListener(SubTaskAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public static class SubTaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescription;
        public TextView tvDate;
        public TextView tvDow;
        public TextView tvMinute;

        public DatePickerDialog.OnDateSetListener mDateSetListener;
        public LinearLayout llDate;
        public ImageButton iBtnSubTaskDelete;

        public  SubTaskViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.sub_task_recycle_view_tv_description);
            tvDate = itemView.findViewById(R.id.sub_task_recycle_view_tv_date);
            tvDow = itemView.findViewById(R.id.sub_task_recycle_view_tv_dow);
            tvMinute = itemView.findViewById(R.id.sub_task_recycle_view_tv_minute);

            llDate = itemView.findViewById(R.id.txt_et_sub_task_date);
            iBtnSubTaskDelete = itemView.findViewById(R.id.action_delete_sub_task);
        }
    }

    public SubTaskAdapter(Activity activity ,ArrayList<SubTask> subTaskList) {
        this.activity = activity;
        this.subTaskList = subTaskList;
    }

    public void setSubTaskList(ArrayList<SubTask> subTaskList)
    {
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


        holder.iBtnSubTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemClick(currentSubTask, "DELETE");
            }
        });


    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        if (subTaskList==null) return 0;
        return subTaskList.size();
    }

}

//        if (isEditModeEnabled){
//            if (holder.vsDescription.getCurrentView()!= holder.etDescription)
//                holder.vsDescription.showNext();
//            if (holder.vsHour.getCurrentView()!= holder.etMinute)
//                holder.vsHour.showNext();
//            holder.iBtnSubTaskUpdate.setImageResource(android.R.drawable.checkbox_on_background);
//        }
//        else{
//            currentSubTask.setDescription(holder.etDescription.getText().toString());
//            currentSubTask.setMinute(Integer.parseInt(holder.etMinute.getText().toString()));
//
//
//            if (holder.vsDescription.getCurrentView()!= holder.tvDescription)
//                holder.vsDescription.showPrevious();
//            if (holder.vsHour.getCurrentView()!= holder.tvMinute)
//                holder.vsHour.showPrevious();
//            holder.iBtnSubTaskUpdate.setImageResource((android.R.drawable.ic_menu_edit));
//
//            if (listener!=null)
//                listener.onItemClick(currentSubTask,"UPDATE");
//
//        }
//
//        holder.iBtnSubTaskUpdate.setOnClickListener(arg0 -> {
//
//            mEditModePosition = isEditModeEnabled ? -1 : position;
//            notifyItemChanged(position);
//
//        });
//        final boolean isEditModeEnabled = position==mEditModePosition;
//
//        if (isEditModeEnabled) {
//                holder.llDate.setOnClickListener(v -> {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                activity, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, holder.mDateSetListener, year, month, day);
//                datePickerDialog.show();
//                });
//
//                holder.mDateSetListener = (view, year, month, dayOfMonth) -> {
//                Date date = DateConverter.ConvertFromYearMonthDayToDate(year,month,dayOfMonth);
//                String stDate = DateConverter.ConvertFromDateToString(date);
//                currentSubTask.setDate(stDate);
//                notifyItemChanged(position);
//
//                };
//                }
//                else holder.llDate.setOnClickListener(null);
