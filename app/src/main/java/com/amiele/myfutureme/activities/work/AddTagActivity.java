package com.amiele.myfutureme.activities.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddTagActivity extends AppCompatActivity {
    ArrayList<Tag> tagList = new ArrayList<>();
    TagAdapter adapter;
    TextView tvTagName;
    LinearLayout llAddTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        llAddTag = findViewById(R.id.add_tag_layout);

        tagList.add(new Tag("Friend",Color.parseColor("#EEDBAA")));
        tagList.add(new Tag("LifeStyle",Color.parseColor("#BDEEAA")));
        tagList.add(new Tag("Job",Color.parseColor("#86EED1")));

        RecyclerView recyclerView = findViewById(R.id.tag_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new TagAdapter(tagList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ImageButton btnColorPicker = findViewById(R.id.action_choose_tag_color);

        tvTagName = findViewById(R.id.txt_tag_name);
        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenColorPicker();
            }
        });

        LinearLayout actionAddTag = findViewById(R.id.action_add_tag);
        actionAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTag();
            }
        });


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
                tvTagName.setBackgroundColor(color);
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
        String name = tvTagName.getText().toString();
        ColorDrawable color = (ColorDrawable) tvTagName.getBackground();
        tagList.add(new Tag(name,color.getColor()));
//        adapter.notifyItemInserted(tagList.size()-1);
        adapter.setTagList(tagList);
        adapter.notifyDataSetChanged();
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
                adapter.getFilter().filter(filteredText);
                return false;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                llAddTag.setVisibility(View.GONE);
                doneItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                llAddTag.setVisibility(View.VISIBLE);
                doneItem.setVisible(true);
                invalidateOptionsMenu();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Toast.makeText(this, "Done selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
