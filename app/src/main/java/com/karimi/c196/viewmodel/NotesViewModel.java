package com.karimi.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.karimi.c196.database.Repository;
import com.karimi.c196.entities.Notes;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private final Repository repository;

    public NotesViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Notes>> getNotesByCourseId(int courseId) {
        return repository.getNotesByCourseId(courseId);
    }
    public LiveData<Notes> getNoteById(int noteId) {
        return repository.getNoteById(noteId);
    }

    public void delete(Notes notes) {
        repository.delete(notes);
    }

    public void update(Notes note) {
        repository.update(note);
    }

}
