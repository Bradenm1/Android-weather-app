package bit.mckebk2.mobileprojectweatherapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------------
-- A login screen that offers login via email/password.
----------------------------------------------------*/
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    // User
    private FirebaseAuth mAuth;

    //Fields
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mHeader;
    private View mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();

        mLoginFormView = findViewById(R.id.email_login_form);
        mProgressView = findViewById(R.id.login_progress);
        mHeader = findViewById(R.id.header);
        mDescription = findViewById(R.id.description);

        // Create event listeners
        CreateOnEventListeners();
    }

    /*----------------------------------------------------
    -- HideMenu()
    -- Hides the login menu and shows connection error text
    -- @param form View: the login form
    -- @param header View: App title name
    ----------------------------------------------------*/
    public void HideMenu(View form, View header) {
        form.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);
        findViewById(R.id.noConnection).setVisibility(View.VISIBLE);
    }

    /*----------------------------------------------------
    -- CreateOnClickListeners()
    -- Creates listeners for the fragment
    ----------------------------------------------------*/
    public void CreateOnEventListeners() {
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            } // End onEditorAction
        }); // End OnEditorActionListener

        // Add sign-in button event handler
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            } // End onClick
        }); // End OnClickListener

        // Add register button event handler
        Button mRegisterButton = findViewById(R.id.buttonRegister);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateAccount();
            } // End onClick
        }); // End OnClickListener
    } // End CreateOnClickListeners

    /*----------------------------------------------------
    -- checkFields()
    -- Check if given fields are correct
    ----------------------------------------------------*/
    private Boolean checkFields(String email, String password) {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {focusView.requestFocus();}
        return cancel;
    } // End checkFields

    /*----------------------------------------------------
    -- attemptLogin()
    -- Attempts to sign-in
    ----------------------------------------------------*/
    private void attemptLogin() {
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = checkFields(email, password);

        if (!cancel) {
            showProgress(true);
            userLogin(email, password);
        }
    } // End attemptLogin

    /*----------------------------------------------------
    -- attemptCreateAccount()
    -- Attempts to create a account
    ----------------------------------------------------*/
    private void attemptCreateAccount() {
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = checkFields(email, password);

        if (!cancel) {
            showProgress(true);
            createUser(email, password);
        }
    } // end attemptCreateAccount

    /*----------------------------------------------------
    -- userLogin()
    -- Attempts to login the given email and password
    ----------------------------------------------------*/
    public void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(LoginActivity.this, R.string.sign_in_toast, Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(myIntent);
                            finish();
                        } else {
                            showProgress(false);
                            // If sign in fails
                            Toast.makeText(LoginActivity.this, R.string.sign_in_failed_toast, Toast.LENGTH_SHORT).show();
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                        }
                    } // End onComplete
                }); // End OnCompleteListener
    } // End userLogin

    /*----------------------------------------------------
    -- createUser()
    -- Attempts to create a account given the given email and password
    ----------------------------------------------------*/
    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Create was a success
                            Toast.makeText(LoginActivity.this, R.string.user_creation_toast, Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If creation fails
                            Toast.makeText(LoginActivity.this, R.string.user_creation_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                        showProgress(false);
                    } // End onComplete
                }); // End OnCompleteListener
    } // End createUser

    /*----------------------------------------------------
    -- isEmailValid()
    -- Characters email should contain
    ----------------------------------------------------*/
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /*----------------------------------------------------
    -- isPasswordValid()
    -- Checks password length
    ----------------------------------------------------*/
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /*----------------------------------------------------
    -- showProgress()
    -- Shows the progress UI and hides the login form
    ----------------------------------------------------*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mHeader.setVisibility(show ? View.GONE : View.VISIBLE);
        mHeader.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mHeader.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mDescription.setVisibility(show ? View.GONE : View.VISIBLE);
        mDescription.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDescription.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    } // End onCreateLoader

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    } // End onLoadFinished

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    /*----------------------------------------------------
    -- addEmailsToAutoComplete()
    -- Shows the progress UI and hides the login form
    ----------------------------------------------------*/
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    } // End addEmailsToAutoComplete


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    } // End ProfileQuery
} // End Class

