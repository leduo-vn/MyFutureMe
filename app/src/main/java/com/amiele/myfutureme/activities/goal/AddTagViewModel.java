package com.amiele.myfutureme.activities.goal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.TagLibrary;

import java.util.List;

public class AddTagViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;

    public AddTagViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);

    }

    public LiveData<List<TagLibrary>> getAllTags()
    {
        return mAppRepo.loadAllLibraryTag();
    }

    public void addLibraryTag(TagLibrary tag)
    {
        mAppRepo.addLibraryTag(tag);
    }
}
