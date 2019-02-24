package android.fit.ba.hotelwien;

import android.content.DialogInterface;
import android.content.Intent;
import android.fit.ba.hotelwien.data.AutentifikacijaLoginPostVM;
import android.fit.ba.hotelwien.data.AutentifikacijaResultVM;
import android.fit.ba.hotelwien.data.Global;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtUsername = findViewById(R.id.korisnickoIme);
        txtPassword = findViewById(R.id.lozinka);
        
        findViewById(R.id.btnPrijava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnPrijavaClick();
            }
        });
        
        findViewById(R.id.btnRegistracija).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(login.this,registracija.class));
            }
        });
    }

    private void do_btnPrijavaClick() {
        if(validacija()){
            String strUserName= txtUsername.getText().toString();
            String strPassword= txtPassword.getText().toString();

            AutentifikacijaLoginPostVM model = new AutentifikacijaLoginPostVM(strUserName,strPassword);

            MyApiRequest.get(this, "api/Autentifikacija/LoginCheck/" + strUserName + "/" + strPassword, new MyRunnable<AutentifikacijaResultVM>() {
                @Override
                public void run(AutentifikacijaResultVM x) {
                    checkLogin(x);
                }
            });
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(login.this).create();
            alertDialog.setTitle("Greska");
            alertDialog.setMessage("Unesite korisnicko ime i lozinku.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
       


    }

    private void checkLogin(AutentifikacijaResultVM x) {
        if ("PogresniPodaci".equals(x.Ime)) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Pogrešno korisničko ime/lozinka.", Snackbar.LENGTH_LONG).show();
        } else {
            Global.prijavljeniTurist = x;
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean validacija() {
        if (txtUsername.getText().toString().isEmpty())
            return false;
        if (txtPassword.getText().toString().isEmpty())
            return false;
        return true;
    }
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }
}
