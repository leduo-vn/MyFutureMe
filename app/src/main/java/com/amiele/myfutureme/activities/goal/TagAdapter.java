package com.amiele.myfutureme.activities.goal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter  extends RecyclerView.Adapter<TagAdapter.TagViewHolder> implements Filterable {
    private ArrayList<Tag> mTagListOriginal;
    private ArrayList<Tag> mTagList;
//    private static TagAdapter.OnItemClickListener listener= null;

    @Override
    public Filter getFilter() {
        return tagListFilter;
    }

    private Filter tagListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Tag> filteredTagList = new ArrayList<>();
            FilterResults filterResults = new FilterResults();

            if (constraint==null|| constraint.length()==0)
            {
                filteredTagList.addAll(mTagListOriginal);
            }
            else
            {
                String pattern = constraint.toString();
                pattern.toLowerCase().trim();

                for (Tag tag: mTagListOriginal)
                {
                    if (tag.getName().toLowerCase().trim().contains(pattern)) filteredTagList.add(tag);
                }
            }

            filterResults.values = filteredTagList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mTagList.clear();
            mTagList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

//    public interface OnItemClickListener {
//        void onItemClick(Tag tag);
//    }

//    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
//        this.listener = listener;
//    }



    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton iBtnTagDelete;

        public  TagViewHolder(View view) {
            super(view);

            tvName = itemView.findViewById(R.id.tag_recycle_view_name);
            iBtnTagDelete = itemView.findViewById(R.id.tag_recycle_view_delete);
        }
    }

    public TagAdapter(ArrayList<Tag> tagList) {
        mTagListOriginal = tagList;
        mTagList = new ArrayList<>(tagList);
    }

    public void setTagList(ArrayList<Tag> tagList)
    {
        mTagListOriginal = tagList;
        mTagList = new ArrayList<>(tagList);
    }

    @NonNull
    @Override
    public TagAdapter.TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_tag, parent, false);
        return new TagAdapter.TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag currentTag = mTagList.get(position);

       //set value
        holder.tvName.setText(currentTag.getName());
        holder.tvName.setBackgroundColor(currentTag.getColor());
    }

    /**
     * Get the number of item of Category List
     * @return list size
     */
    @Override
    public int getItemCount() {
        return mTagList.size();
    }

}

