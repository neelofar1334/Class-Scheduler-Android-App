package com.karimi.c196.ui.login;
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

import com.karimi.c196.R;
import com.karimi.c196.Stubs.StubUsersDAO;
import com.karimi.c196.entities.User;
import com.karimi.c196.database.loginData.LModel.LoginRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class LoginViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubUsersDAO stubUsersDAO;
    private LoginRepository loginRepository;
    private LoginViewModel loginViewModel;
    private ExecutorService executorService;

    @Before
    public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
        Application application = ApplicationProvider.getApplicationContext();
        loginRepository = LoginRepository.getInstance(application);
        loginViewModel = new LoginViewModel(application);
        stubUsersDAO = new StubUsersDAO();
        loginViewModel.setUsersDAO(stubUsersDAO);
        loginViewModel.setLoginRepository(loginRepository);
    }

    @Test
    public void testLoginSuccess() throws InterruptedException {
        User user = new User("1", "test_user", "password", "regular");
        stubUsersDAO.addUser(user);

        Observer<LoginResult> observer = new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                assertNotNull(loginResult);
                assertNotNull(loginResult.getSuccess());
                assertNull(loginResult.getError());
            }
        };

        loginViewModel.getLoginResult().observeForever(observer);

        loginViewModel.login("test_user", "password");

        Thread.sleep(1000); //Wait for async task to complete
    }

    @Test
    public void testLoginFailureUserNotFound() throws InterruptedException {
        Observer<LoginResult> observer = new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                assertNotNull(loginResult);
                assertNotNull(loginResult.getError());
                assertNull(loginResult.getSuccess());
                assertEquals((int) loginResult.getError(), R.string.login_failed);
            }
        };

        loginViewModel.getLoginResult().observeForever(observer);

        loginViewModel.login("test_user", "password");

        Thread.sleep(1000);
    }

    @Test
    public void testLoginFailureInvalidPassword() throws InterruptedException {
        User user = new User("1", "test_user", "password", "regular");
        stubUsersDAO.addUser(user);

        Observer<LoginResult> observer = new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                assertNotNull(loginResult);
                assertNotNull(loginResult.getError());
                assertNull(loginResult.getSuccess());
                assertEquals((int) loginResult.getError(), R.string.login_failed);
            }
        };

        loginViewModel.getLoginResult().observeForever(observer);

        loginViewModel.login("test_user", "wrong_password");

        Thread.sleep(1000);
    }
}
