package com.example.c196.viewmodel;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import android.app.Application;

import com.example.c196.DAO.StubTermsDAO;
import com.example.c196.entities.Terms;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TermViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubTermsDAO stubTermsDAO;
    private TermViewModel termViewModel;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        stubTermsDAO = new StubTermsDAO();
        termViewModel = new TermViewModel(application);
        termViewModel.setTermsDAO(stubTermsDAO);
    }

    @Test
    public void testGetAllTerms() {
        //Add a term before observing
        Terms newTerm = new Terms();
        newTerm.setTermID(1);
        newTerm.setTitle("Spring 2024");
        newTerm.setStartDate("2024-01-01");
        newTerm.setEndDate("2024-06-01");
        stubTermsDAO.insert(newTerm);

        //Observe the terms
        termViewModel.getAllTerms().observeForever(new Observer<List<Terms>>() {
            @Override
            public void onChanged(List<Terms> terms) {
                assertNotNull(terms);
                assertEquals(1, terms.size()); //Should be 1 after adding the term
                assertEquals("Spring 2024", terms.get(0).getTitle());
            }
        });
    }


    @Test
    public void testGetTermById() {
        Terms newTerm = new Terms();
        newTerm.setTermID(1);
        newTerm.setTitle("Spring 2024");
        newTerm.setStartDate("2024-01-01");
        newTerm.setEndDate("2024-06-01");
        stubTermsDAO.insert(newTerm);

        termViewModel.getTermById(1).observeForever(term -> {
            assertNotNull(term);
            assertEquals("Spring 2024", term.getTitle());
        });
    }

    @Test
    public void testDeleteTerm() {
        Terms newTerm = new Terms();
        newTerm.setTermID(1);
        newTerm.setTitle("Spring 2024");
        newTerm.setStartDate("2024-01-01");
        newTerm.setEndDate("2024-06-01");
        stubTermsDAO.insert(newTerm);

        termViewModel.delete(newTerm);

        termViewModel.getAllTerms().observeForever(terms -> {
            assertNotNull(terms);
            assertEquals(0, terms.size());
        });
    }
}
