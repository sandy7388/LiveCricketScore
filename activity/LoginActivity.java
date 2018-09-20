package com.sandy.wayzon_android.livecricketscore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.httpHelper.URLs;
import com.sandy.wayzon_android.livecricketscore.model.Login;
import com.sandy.wayzon_android.livecricketscore.session.SessionManager;
import com.sandy.wayzon_android.livecricketscore.util.TransparentProgressDialog;
import com.sandy.wayzon_android.livecricketscore.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextMobile,editTextPassword;
    private Button buttonLogin;
    private String stringMobile,stringPassword,
            stringOperatorId,stringOperatorName;
    private SessionManager session;
    private TransparentProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextMobile = findViewById(R.id.edt_userid);
        editTextPassword = findViewById(R.id.edt_password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);
        session = new SessionManager(this);
        progressDialog = new TransparentProgressDialog(this, R.drawable.spinner);

        if (SessionManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                loginTask();
                break;
        }
    }

    private void loginTask() {

        progressDialog.show();
        stringMobile = editTextMobile.getText().toString();
        stringPassword = editTextPassword.getText().toString();
        if (stringMobile.equals(""))
        {
            Toast.makeText(this, "This field can not be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (stringPassword.equals(""))
        {
            Toast.makeText(this, "This field can not be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1"))
                            {
                                stringOperatorName = jsonObject.getString("operator_name");
                                stringOperatorId = jsonObject.getString("operator_id");

                                Login login = new Login();
                                login.setMobile(stringMobile);
                                login.setPassword(stringPassword);
                                login.setOperatorId(stringOperatorId);
                                login.setOperatorName(stringOperatorName);
                                session.userLogin(login);
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",stringMobile);
                params.put("password",stringPassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
