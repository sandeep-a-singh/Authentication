package digitalpaisa.android.sunny.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.Locale;

import Defaults.PreferenceSelection;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class Login extends AppCompatActivity {
    boolean verify_Theme_Color_Change=false;
    boolean verify_Language_Change=false;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    PreferenceSelection selector;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    public void reinitialize()
    {
         _emailText =(EditText) findViewById(R.id.input_email);

        _passwordText =(EditText) findViewById(R.id.input_password);


        _loginButton = (Button)findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        selector =new PreferenceSelection(getApplicationContext());
        Boolean colorSelected=selector.getColor();
        if(colorSelected)
        {
            setTheme(R.style.ChangeTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }

        setLanguage();
        setContentView(R.layout.login);

        ButterKnife.inject(this);

        Setting();


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void Setting() {
        ImageView icon = new ImageView(getApplicationContext()); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.setting));

        Log.d(TAG, "Login 1");

        final FloatingActionButton setting =new FloatingActionButton.Builder(this).setContentView(icon).build();

        Log.d(TAG, "Login 1.1");
        final SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        Log.d(TAG, "Login 1.2");

        ImageView colorset = new ImageView(this); // Create an icon
        colorset.setImageDrawable(getResources().getDrawable(R.drawable.change_color));

        ImageView language = new ImageView(this); // Create an icon
        language.setImageDrawable(getResources().getDrawable(R.drawable.change_language));

        final SubActionButton change_color = itemBuilder.setContentView(colorset).build();
        final SubActionButton change_language = itemBuilder.setContentView(language).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(change_color)
                .addSubActionView(change_language)
                .attachTo(setting)
                .build();

change_color.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        verify_Theme_Color_Change = true;
        Boolean colorSelected = selector.getColor();
        if (colorSelected) {
            selector.insertThemeColor(false);
        } else {
            selector.insertThemeColor(true);
        }

        ThemeChanger();
    }
});
        change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_Language_Change=true;
                String lang ;
                Boolean language=selector.getLanguage();
                if(language == false)
                {
                    lang = "de";
                selector.insertLanguage(true);
                }
                else
                {
                    lang = "en";
                selector.insertLanguage(false);
                }

                Toast.makeText(getBaseContext(), "Language Changed to " + lang, Toast.LENGTH_LONG).show();
                setLanguage();
                changeUI();



        /*_emailText.setHint(R.string.email);
        _passwordText.setHint(R.string.password);*/
            }
        });
    }

    public void changeUI()
    {
        setContentView(R.layout.login);
        reinitialize();
        Setting();
    }

public void setLanguage()
{

    String lang ;
    Boolean language=selector.getLanguage();
    if(language == false)
    {
        lang = "en";
    }
    else
    {
        lang = "de";
    }
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext().getResources().updateConfiguration(config,
            getBaseContext().getResources().getDisplayMetrics());
}

    public  void ThemeChanger()
    {
   Boolean colorSelected=selector.getColor();
        if(colorSelected)
        {
            setTheme(R.style.ChangeTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }
      changeUI();
    }
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(R.style.WalletFragmentDefaultStyle);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed


                        if (verify_Theme_Color_Change && verify_Language_Change) {
                            if (email.equals("admin@epaisa.com") && password.equals("123123")) {
                                onLoginSuccess();
                            } else {
                                onLoginFailed();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "change theme and language", Toast.LENGTH_SHORT).show();
                        }
                        _loginButton.setEnabled(true);
                        progressDialog.dismiss();
                    }
                }, 3000);


    }



    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
       Intent intent =new Intent(Login.this, Search.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
