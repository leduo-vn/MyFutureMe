package com.amiele.myfutureme.activities.work;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import java.util.Date;

public class SubTaskAdapter  extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {
    private ArrayList<SubTask> subTaskList;
    private static TaskAdapter.OnItemClickListener listener= null;
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
        public TextView tvDOW;
        public TextView tvHour;
        public ViewSwitcher viewSwitcherDescription;
        public EditText etDescription;
        public ImageButton btnUpdateSubTask;
        public EditText etHour;
        public ViewSwitcher viewSwitcherHour;
        public DatePickerDialog.OnDateSetListener mDateSetListener;

        public LinearLayout et_txt_date;
     //   public ImageView imageView;

        public  SubTaskViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.txt_sub_task_description);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDOW = itemView.findViewById(R.id.txt_dow);
            tvHour = itemView.findViewById(R.id.txt_sub_task_hour);
            viewSwitcherDescription = itemView.findViewById(R.id.description_sub_task_switch_view);
            etDescription = itemView.findViewById(R.id.et_sub_task_description);
            btnUpdateSubTask = itemView.findViewById(R.id.action_update_sub_task);
            etHour = itemView.findViewById(R.id.et_sub_task_hour);
            viewSwitcherHour = itemView.findViewById(R.id.hour_sub_task_switch_view);
            et_txt_date = itemView.findViewById(R.id.txt_et_sub_task_date);
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

    public SubTaskAdapter(Activity activity ,ArrayList<SubTask> subTaskList) {
        this.activity = activity;
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
        SubTask currentSubTask = subTaskList.get(position);
        holder.tvDescription.setText(currentSubTask.getDescription());
//        holder.tvDate.setText(currentSubTask.getDate());

        holder.tvDOW.setText(currentSubTask.getDate_DOW());
        holder.tvDate.setText(currentSubTask.getDate_Date());
        holder.tvHour.setText(Integer.toString(currentSubTask.getHour()));

        final boolean isEditModeEnabled = position==mEditModePosition;

        if (isEditModeEnabled){
            if (holder.viewSwitcherDescription.getCurrentView()!= holder.etDescription)
                holder.viewSwitcherDescription.showNext();
            if (holder.viewSwitcherHour.getCurrentView()!= holder.etHour)
                holder.viewSwitcherHour.showNext();
            holder.btnUpdateSubTask.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else{
            if (holder.viewSwitcherDescription.getCurrentView()!= holder.tvDescription)
                holder.viewSwitcherDescription.showPrevious();
            if (holder.viewSwitcherHour.getCurrentView()!= holder.tvHour)
                holder.viewSwitcherHour.showPrevious();
            holder.btnUpdateSubTask.setImageResource((android.R.drawable.ic_menu_edit));
        }

        holder.btnUpdateSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mEditModePosition = isEditModeEnabled ? -1 : position;
                notifyItemChanged(position);
            }
        });

        if (isEditModeEnabled) {
            holder.et_txt_date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            activity, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, holder.mDateSetListener, year, month, day);
                    //   datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });

             holder.mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String text= Integer.toString(year)+"-"+ Integer.toString(month+1)+"-"+Integer.toString(dayOfMonth);
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();

                    // Date date = ConvertFromStringToDate(text);
                    //AddSubTask();
                }
            };
        }
        else holder.et_txt_date.setOnClickListener(null);

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

