package com.amiele.myfutureme.activities.main.goal.tag;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.amiele.myfutureme.AppRepo;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.TagLibrary;
import com.amiele.myfutureme.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AddTagViewModel extends AndroidViewModel {
    private static AppRepo mAppRepo;

    private static User user;
    private static int goalId;
    private static List<Tag> tagList;
    private static List<TagLibrary> tagLibraryList;
    private static MediatorLiveData<List<Tag>> tagListMediatorLiveData = new MediatorLiveData<>();
    private static MutableLiveData<Boolean> userResultMutableLiveData = new MutableLiveData<>();

    public AddTagViewModel(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
        loadUser();
    }

    LiveData<List<Tag>> getAllTags()
    {
        return tagListMediatorLiveData;
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
                userResultMutableLiveData.setValue(true);

            }
        }.execute();
    }

    public int getUserId(){return user.getId();}

    public void setGoalId(int goalId)
    {
        this.goalId = goalId;
    }


    LiveData<Boolean> getUserResult() {
        return userResultMutableLiveData;
    }

    static void loadTags()
    {
        LiveData<List<TagLibrary>> taskLibraryLiveDataList = mAppRepo.loadAllLibraryTag(user.getId());
        LiveData<List<Tag>> tagLiveDataList = mAppRepo.loadTags(goalId);

        tagListMediatorLiveData.addSource(taskLibraryLiveDataList, tagLibraries -> {
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
                    tagListMediatorLiveData.setValue(tags);
            }
        });

        tagListMediatorLiveData.addSource(tagLiveDataList, tagLists -> {
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
                tagListMediatorLiveData.setValue(tags);
            }
        });

    }

    void addTag(Tag tag)
    {
        tag.setGoalId(goalId);
        mAppRepo.addTag(tag);
    }

    void removeTag(Tag tag)
    {
        mAppRepo.deleteTag(tag.getId());
    }

    void addLibraryTag(TagLibrary tag)
    {
        mAppRepo.addLibraryTag(tag);
    }
}
