package com.amiele.myfutureme.activities.main.goal.tag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.TagLibrary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddTagActivity extends AppCompatActivity {

    private AddTagViewModel mAddTagViewModel;

    private TagAdapter mAdapter;
    private RecyclerView mRvTag;
    private TextView mTvTagName;
    private LinearLayout mLlAddTag;
    private LinearLayout mLlTagAdd;
    private ImageButton mIBtnColorPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        InitializeView();

        String goalId = getIntent().getStringExtra("goal_id");

        ArrayList<Tag> mTagList = new ArrayList<>();

        mRvTag.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new TagAdapter(mTagList);
        mRvTag.setLayoutManager(layoutManager);
        mRvTag.setAdapter(mAdapter);

        mIBtnColorPick.setOnClickListener(v -> OpenColorPicker());

        mLlTagAdd.setOnClickListener(v -> AddTag());

        mAddTagViewModel = new ViewModelProvider(this).get(AddTagViewModel.class);
        mAddTagViewModel.setGoalId(Integer.parseInt(goalId));

        mAddTagViewModel.getUserResult().observe(this, aBoolean -> {
            if (aBoolean)
                mAddTagViewModel.loadTags();
        });

        mAddTagViewModel.getAllTags().observe(this, tagList -> {
                ArrayList<Tag> tags = new ArrayList<>(tagList);
                mAdapter.setTagList(tags);
                mAdapter.notifyDataSetChanged();
        });

        mAdapter.setOnItemClickListener(tag -> {
            if (tag.isChosen())
            {
                mAddTagViewModel.removeTag(tag);
            }
            else
            {
                mAddTagViewModel.addTag(tag);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_tag_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem doneItem = menu.findItem(R.id.action_done);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String filteredText) {
                mAdapter.getFilter().filter(filteredText);
                return false;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mLlAddTag.setVisibility(View.GONE);
                doneItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mLlAddTag.setVisibility(View.VISIBLE);
                doneItem.setVisible(true);
                invalidateOptionsMenu();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_done)
        {
            Intent returnIntent= new Intent();
            setResult(RESULT_OK,returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void InitializeView()
    {
        mLlAddTag = findViewById(R.id.add_tag_ll_add_tag);
        mRvTag = findViewById(R.id.add_tag_rv_tag);
        mTvTagName = findViewById(R.id.add_tag_tv_tag_name);
        mIBtnColorPick = findViewById(R.id.add_tag_ibtn_color_pick);
        mLlTagAdd = findViewById(R.id.add_tag_ll_tag_add);
    }

    private void OpenColorPicker()
    {
        ColorPicker colorPicker = new ColorPicker(this);

        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("#EEDBAA");
        colorList.add("#BDEEAA");
        colorList.add("#86EED1");
        colorList.add("#8FAEFF");
        colorList.add("#D186EE");
        colorList.add("#FF8FAE");
        colorList.add("#B2A5CF");
        colorList.add("#D1C7E1");
        colorList.add("#FF8FAE");
        colorList.add("#ABD89A");

        colorPicker.setColors(colorList);
        colorPicker.setDefaultColorButton(Color.GRAY);
        colorPicker.setRoundColorButton(true);
        colorPicker.setColumns(5);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                mTvTagName.setBackgroundColor(color);
                DisplayToast(Integer.toString(color));
            }

            @Override
            public void onCancel(){
                // put code
            }
        }).show();

    }

    private void AddTag()
    {
        String name = mTvTagName.getText().toString();
        ColorDrawable color = (ColorDrawable) mTvTagName.getBackground();
        mAddTagViewModel.addLibraryTag(new TagLibrary(name, color.getColor(),mAddTagViewModel.getUserId()));
    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
