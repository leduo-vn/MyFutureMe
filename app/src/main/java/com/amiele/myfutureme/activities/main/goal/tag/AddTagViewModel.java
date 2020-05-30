package com.amiele.myfutureme.activities.main.goal.tag;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.TagLibrary;
import com.amiele.myfutureme.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AddTagViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;
    private static User user;

    public AddTagViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        loadUser();

    }

    private static void loadUser() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                user = mAppRepo.getSignedInUser();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getUserResult.setValue(true);

            }
        }.execute();
    }




    public int getUserId(){return user.getId();}

    private static int goalId;
    private static List<Tag> tagList;
    private static List<TagLibrary> tagLibraryList;

    public void setGoalId(int goalId)
    {
        this.goalId = goalId;
    }

    private static MutableLiveData<Boolean> getUserResult = new MutableLiveData<>();
    public LiveData<Boolean> getUserResult() {
        return getUserResult;
    }

    static MediatorLiveData<List<Tag>> mediatorLiveData = new MediatorLiveData<>();

    public LiveData<List<Tag>> getAllTags()
    {
        return mediatorLiveData;
    }

    public static void loadTags()
    {
        LiveData<List<TagLibrary>> taskLibraryLiveDataList = mAppRepo.loadAllLibraryTag(user.getId());
        LiveData<List<Tag>> tagLiveDataList = mAppRepo.loadTags(goalId);

        mediatorLiveData.addSource(taskLibraryLiveDataList, new Observer<List<TagLibrary>>() {
            @Override
            public void onChanged(List<TagLibrary> tagLibraries) {
                tagLibraryList = tagLibraries;
                if (tagList != null)
                {
                        ArrayList<Tag> tags = new ArrayList<>();
                        for (TagLibrary tagLibrary:tagLibraryList)
                        {
                            tags.add(new Tag(tagLibrary));
                        }
                        for (Tag tag:tagList)
                        {
                            for (Tag tag1:tags)
                                if (tag.getTagLibraryId() == tag1.getTagLibraryId())
                                {
                                    tag1.setChosen(true);
                                    tag1.setGoalId(goalId);
                                    tag1.setId(tag.getId());
                                    break;
                                }
                        }
                        mediatorLiveData.setValue(tags);
                }
            }
        });

        mediatorLiveData.addSource(tagLiveDataList, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tagLists) {
                tagList = tagLists;
                if (tagLibraryList != null)
                {
                    ArrayList<Tag> tags = new ArrayList<>();
                    for (TagLibrary tagLibrary:tagLibraryList)
                    {
                        tags.add(new Tag(tagLibrary));
                    }
                    for (Tag tag:tagList)
                    {
                        for (Tag tag1:tags)
                            if (tag.getTagLibraryId() == tag1.getTagLibraryId())
                            {
                                tag1.setChosen(true);
                                tag1.setGoalId(goalId);
                                tag1.setId(tag.getId());
                                break;
                            }
                    }
                    mediatorLiveData.setValue(tags);
                }
            }
        });

    }

    public void addTag(Tag tag)
    {
        tag.setGoalId(goalId);
        mAppRepo.addTag(tag);
    }

    public void removeTag(Tag tag)
    {
        mAppRepo.deleteTag(tag.getId());
    }

    public void addLibraryTag(TagLibrary tag)
    {
        mAppRepo.addLibraryTag(tag);
    }
}
