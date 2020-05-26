package com.amiele.myfutureme.activities.work;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.media.Image;
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

public class TagAdapter  extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private ArrayList<Tag> tagList;
    private static TagAdapter.OnItemClickListener listener= null;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }
//
//    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
//        this.listener = listener;
//    }



    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton btnDeleteTag;

        public  TagViewHolder(View view) {
            super(view);

            tvName = itemView.findViewById(R.id.txt_tag_name);
            btnDeleteTag = itemView.findViewById(R.id.action_delete_tag);
           // tvDescription = itemView.findViewById(R.id.txt_sub_task_description);
        }
    }

    public TagAdapter(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagAdapter.TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_tag, parent, false);
        return new TagAdapter.TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag currentTag = tagList.get(position);

       //set value
        holder.tvName.setText(currentTag.getName());
        holder.tvName.setBackgroundColor(Color.parseColor(currentTag.getColor()));
    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        return tagList.size();
    }

}

