package com.example.c196.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196.DAO.TermsDAO;
import com.example.c196.database.Repository;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private LiveData<List<Terms>> mAllTerms;
    private Repository repository;
    private TermsDAO termsDAO;

    public TermViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        termsDAO = repository.getTermsDAO();
        mAllTerms = termsDAO.getAllTerms();
    }

    // Setter method for testing purposes
    public void setTermsDAO(TermsDAO termsDAO) {
        this.termsDAO = termsDAO;
        mAllTerms = termsDAO.getAllTerms();
    }

    public LiveData<List<Terms>> getAllTerms() {
        return mAllTerms;
    }

    public LiveData<Terms> getTermById(int termId) {
        return termsDAO.getTermById(termId);
    }

    public void delete(Terms terms) {
        repository.delete(terms);
    }

    public LiveData<List<Courses>> getCoursesForTerm(int termId) {
        return repository.getCoursesForTerm(termId);
    }
}
