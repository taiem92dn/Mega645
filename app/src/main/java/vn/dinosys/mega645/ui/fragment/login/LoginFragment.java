package vn.dinosys.mega645.ui.fragment.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import vn.dinosys.mega645.R;
import vn.dinosys.mega645.app.Constants;
import vn.dinosys.mega645.app.Runtime;
import vn.dinosys.mega645.di.component.AppComponent;
import vn.dinosys.mega645.ui.activity.HomeActivity;
import vn.dinosys.mega645.ui.fragment.base.BaseFragment;
import vn.dinosys.mega645.ui.view.ILoginView;

/**
 * Created by huutai.
 * Since: 8/18/16 on 2:19 PM
 * Project: Mega645
 */
public class LoginFragment extends BaseFragment implements ILoginView {

    private static final String TAG = LoginFragment.class.getSimpleName();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @BindView(R.id.usernameEditText)
    EditText mEditUsername;

    @BindView(R.id.passwordEditText)
    EditText mEditPassword;

    @BindView(R.id.loginButton)
    Button mLoginButton;

    @BindView(R.id.imgVisiblePassword)
    ImageView mImgVisiblePassword;

    ProgressDialog mPdLoading;

    @Inject
    Runtime mRuntime;

    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void onScreenVisible() {
        super.onScreenVisible();

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult pLoginResult) {
                Log.i(TAG,"onSuccess");
                getUserInfoFromFacebook(pLoginResult.getAccessToken());

            }
            @Override
            public void onCancel() {
                Log.i(TAG,"onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG,"onError: " + error.getMessage());
                showError(error.getMessage());
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_google_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), pConnectionResult -> {
                    Log.d(TAG, pConnectionResult.getErrorMessage());
                    showError(pConnectionResult.getErrorMessage());
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        setUpUI();
    }

    private void setUpUI() {

        initialize();
    }

    private void initialize() {
        getComponent(AppComponent.class).inject(this);
    }

    @OnClick(R.id.imgVisiblePassword)
    public void onToggleVisiblePassword(View pView) {
        Log.d(TAG, "input type: " + mEditPassword.getInputType());
//        if (mEditPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
        if (mEditPassword.getTransformationMethod() == null) {
//            mEditPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditPassword.setTransformationMethod(new PasswordTransformationMethod());
            mEditPassword.setSelection(mEditPassword.length());
            mImgVisiblePassword.setImageResource(R.drawable.ic_visibility);
        }
        else {
//            mEditPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mEditPassword.setTransformationMethod(null);
            mEditPassword.setSelection(mEditPassword.length());
            mImgVisiblePassword.setImageResource(R.drawable.ic_visibility_off);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private boolean isInputValidate() {
        String username = mEditUsername.getText() + "";
        String password = mEditPassword.getText() + "";

        boolean isValidate = true;
        if (username.isEmpty()) {
            mEditUsername.setError(getString(R.string.input_username));
            isValidate = false;
        }
        if (password.isEmpty()) {
            mEditPassword.setError(getString(R.string.input_password));
            isValidate = false;
        }

        return isValidate;
    }

    @OnClick(R.id.loginButton)
    public void onLogin(View pView) {
        if (isInputValidate()) {
            showSignInSuccess();
        }
    }

    @OnClick(R.id.llLoginFacebook)
    public void onLoginFacebook(View pView) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
    }

    @OnClick(R.id.llLoginGoogle)
    public void onLoginGPlus(View pView) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN_GOOGLE);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
//                Log.d(TAG, acct.getDisplayName() + " " + acct.getEmail() + " " + acct.getId() + " " + acct);
                mRuntime.saveUser(acct.getEmail(), acct.getIdToken(), Constants.LoginType.GOOGLE.ordinal());
                /*saveUserDb(acct.getEmail(), Constants.LoginType.GOOGLE.toString());*/
                showSignUpSocialSuccess();
            }
            else {
                showError("Couldn't get info from google");
            }

        } else {
            // Signed out, show unauthenticated UI.
            showError("Sign in google false");
        }
    }

    public void getUserInfoFromFacebook(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    try {
                        Log.d(TAG, object.toString());
                        String email = object.getString("email");
//                        String birthday = object.getString("birthday");
                        mRuntime.saveUser(email, accessToken.getToken(), Constants.LoginType.FACEBOOK.ordinal());
                        /*saveUserDb(email, Constants.LoginType.FACEBOOK.toString());*/
                        showSignUpSocialSuccess();
                    } catch (JSONException pE) {
                        pE.printStackTrace();
                        showError("Couldn't get info from google");
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void showSignUpSocialSuccess() {
        showError("Success");
        showSignInSuccess();
    }

    @Override
    public void showSignInSuccess() {
        getActivity().finish();
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @Override
    public void showSignInFail() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
