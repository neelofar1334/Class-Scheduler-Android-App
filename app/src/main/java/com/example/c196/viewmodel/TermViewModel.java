package com.example.c196.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.c196.DAO.Relationships.TermsWithCourses;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.Repository;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private final LiveData<List<Terms>> mAllTerms;
    private final Repository repository;

    public TermViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        mAllTerms = repository.getAllTerms();
    }

   public LiveData<List<Terms>> getAllTerms() {
        return mAllTerms;
    }
    public LiveData<Terms> getTermById(int termId) {
        return repository.getTermById(termId);
    }
    public void delete(Terms terms) {
        repository.delete(terms);
    }
}
