package com.karimi.c196.Stubs;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.karimi.c196.DAO.TermsDAO;
import com.karimi.c196.entities.Courses;
import com.karimi.c196.entities.Terms;
import java.util.ArrayList;
import java.util.List;

public class StubTermsDAO implements TermsDAO {

    private static final String TAG = "StubTermsDAO";
    private final MutableLiveData<List<Terms>> termsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final List<Terms> termsList = new ArrayList<>();

    @Override
    public LiveData<Terms> getTermById(int termId) {
        for (Terms term : termsList) {
            if (term.getTermID() == termId) {
                MutableLiveData<Terms> termLiveData = new MutableLiveData<>();
                termLiveData.setValue(term);
                return termLiveData;
            }
        }
        return new MutableLiveData<>(null);
    }

    @Override
    public boolean termExists(int termId) {
        for (Terms term : termsList) {
            if (term.getTermID() == termId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LiveData<List<Terms>> getAllTerms() {
        termsLiveData.setValue(new ArrayList<>(termsList)); // Ensure a fresh copy is set
        return termsLiveData;
    }

    @Override
    public void insert(Terms terms) {
        termsList.add(terms);
        termsLiveData.setValue(new ArrayList<>(termsList)); // Ensure a fresh copy is set
        Log.d(TAG, "Inserted term: " + terms.getTitle());
    }

    @Override
    public void update(Terms terms) {
        for (int i = 0; i < termsList.size(); i++) {
            if (termsList.get(i).getTermID() == terms.getTermID()) {
                termsList.set(i, terms);
                break;
            }
        }
        termsLiveData.setValue(new ArrayList<>(termsList)); // Ensure a fresh copy is set
        Log.d(TAG, "Updated term: " + terms.getTitle());
    }

    @Override
    public void delete(Terms terms) {
        termsList.remove(terms);
        termsLiveData.setValue(new ArrayList<>(termsList)); // Ensure a fresh copy is set
        Log.d(TAG, "Deleted term: " + terms.getTitle());
    }

    @Override
    public LiveData<List<Courses>> getCoursesForTerm(int termId) {
        return null;
    }

    public void clearAll() {
        termsList.clear();
        termsLiveData.setValue(new ArrayList<>(termsList)); // Ensure a fresh copy is set
    }
}
