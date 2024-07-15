package com.example.c196.registration;

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
import com.example.c196.R;
import com.example.c196.Stubs.StubAdminDAO;
import com.example.c196.Stubs.StubStudentDAO;
import com.example.c196.Stubs.StubUsersDAO;
import com.example.c196.database.rModel.RegisteredUser;
import com.example.c196.database.rModel.RegistrationRepository;
import com.example.c196.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class RegistrationViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubUsersDAO stubUsersDAO;
    private StubAdminDAO stubAdminDAO;
    private StubStudentDAO stubStudentDAO;
    private RegistrationRepository registrationRepository;
    private RegistrationViewModel registrationViewModel;
    private ExecutorService executorService;

    @Before
    public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
        Application application = ApplicationProvider.getApplicationContext();
        registrationRepository = new RegistrationRepository(application);
        stubUsersDAO = new StubUsersDAO();
        stubAdminDAO = new StubAdminDAO();
        stubStudentDAO = new StubStudentDAO();
        registrationViewModel = new RegistrationViewModel(application);
        registrationViewModel.setUsersDAO(stubUsersDAO);
        registrationViewModel.setAdminDAO(stubAdminDAO);
        registrationViewModel.setStudentDAO(stubStudentDAO);
    }

    @Test
    public void testRegisterUserSuccess() throws InterruptedException {
        Observer<UserRegistrationResult> observer = new Observer<UserRegistrationResult>() {
            @Override
            public void onChanged(UserRegistrationResult registrationResult) {
                assertNotNull(registrationResult);
                assertNotNull(registrationResult.getSuccess());
                assertNull(registrationResult.getError());
            }
        };

        registrationViewModel.getRegisterResult().observeForever(observer);

        registrationViewModel.register("new_user", "password123", "student", null, new RegistrationRepository.RegistrationCallback() {
            @Override
            public void onSuccess(RegisteredUser registeredUser) {
                assertNotNull(registeredUser);
                assertEquals("new_user", registeredUser.getDisplayName());
            }

            @Override
            public void onFailure(Exception e) {
                fail("Registration should succeed");
            }
        });

        Thread.sleep(1000); //Wait for async task to complete
    }

    @Test
    public void testRegisterUserExists() throws InterruptedException {
        User existingUser = new User("1", "existing_user", "password123", "student");
        stubUsersDAO.addUser(existingUser);

        Observer<UserRegistrationResult> observer = new Observer<UserRegistrationResult>() {
            @Override
            public void onChanged(UserRegistrationResult registrationResult) {
                assertNotNull(registrationResult);
                assertNotNull(registrationResult.getError());
                assertNull(registrationResult.getSuccess());
                assertEquals((int) registrationResult.getError(), R.string.user_exists);
            }
        };

        registrationViewModel.getRegisterResult().observeForever(observer);

        registrationViewModel.register("existing_user", "password123", "student", null, new RegistrationRepository.RegistrationCallback() {
            @Override
            public void onSuccess(RegisteredUser registeredUser) {
                fail("Registration should fail because the user already exists");
            }

            @Override
            public void onFailure(Exception e) {
                assertNotNull(e);
                assertTrue(e.getMessage().contains("Error registering"));
            }
        });

        Thread.sleep(1000);
    }
}

