package com.visionsoft.raezproducciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText fullName, emailId, escuela, carrera, grupo,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;

	public SignUp_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize all views
	private void initViews() {
		fullName = (EditText) view.findViewById(R.id.fullName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		escuela = (EditText) view.findViewById(R.id.escuela);
		carrera = (EditText) view.findViewById(R.id.carrera);
		grupo = (EditText) view.findViewById(R.id.grupo);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

		/* Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}*/
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new MainActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		String getEmailId = emailId.getText().toString();
		String getEscuela = escuela.getText().toString();
		String getCarrera = carrera.getText().toString();
		String getGrupo = grupo.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getEscuela.equals("") || getEmailId.length() == 0
				|| getCarrera.equals("") || getEmailId.length() == 0
				|| getGrupo.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"Todos los campos son requeridos.");

		// Check if email id valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Correo inválido.");

		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					"Las contraseñas no coinciden.");

		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					"Please select Terms and Conditions.");

		// Else do signup or do your stuff
		else {
			new RegistroActivity(getContext()).execute(getFullName, getEscuela, getCarrera, getGrupo, getEmailId, getPassword);
		}


	}

    public class RegistroActivity extends AsyncTask<String, Void, String> {
        private Context context;
        ProgressDialog dialog;

        public RegistroActivity(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Registrando Usuario...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            String nombres = arg0[0];
            String escuela = arg0[1];
            String carrera = arg0[2];
            String grupo = arg0[3];
            String email = arg0[4];
            String password = arg0[5];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {
                data = "?nombres=" + URLEncoder.encode(nombres, "UTF-8");
                data += "&escuela=" + URLEncoder.encode(escuela, "UTF-8");
                data += "&carrera=" + URLEncoder.encode(carrera, "UTF-8");
                data += "&grupo=" + URLEncoder.encode(grupo, "UTF-8");
                data += "&email=" + URLEncoder.encode(email, "UTF-8");
                data += "&password=" + URLEncoder.encode(password, "UTF-8");

                link = "http://raezproducciones.com/sistema/php/registro_android.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Se ha registrado correctamente.");
                        // Replace login fragment
                        new MainActivity().replaceLoginFragment();
                    } else if (query_result.equals("FAILURE")) {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Algo Falló en el registro.");
                    } else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "No se puede conectar a la base de datos.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
