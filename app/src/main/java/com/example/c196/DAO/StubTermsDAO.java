package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196.entities.Terms;

import java.util.ArrayList;
import java.util.List;

public class StubTermsDAO implements TermsDAO {
    private final List<Terms> termList = new ArrayList<>();
    private final MutableLiveData<List<Terms>> liveDataTerms = new MutableLiveData<>();

    @Override
    public LiveData<List<Terms>> getAllTerms() {
        liveDataTerms.setValue(termList);
        return liveDataTerms;
    }

    @Override
    public LiveData<Terms> getTermById(int termId) {
        for (Terms term : termList) {
            if (term.getTermID() == termId) {
                MutableLiveData<Terms> liveData = new MutableLiveData<>();
                liveData.setValue(term);
                return liveData;
            }
        }
        return null;
    }

    @Override
    public boolean termExists(int termId) {
        return false;
    }

    @Override
    public void insert(Terms term) {
        termList.add(term);
        liveDataTerms.setValue(termList);
    }

    @Override
    public void update(Terms term) {

    }

    @Override
    public void delete(Terms term) {
        termList.remove(term);
        liveDataTerms.setValue(termList);
    }

}
