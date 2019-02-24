package android.fit.ba.hotelwien;

import android.content.DialogInterface;
import android.fit.ba.hotelwien.data.TuristPostVM;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registracija extends AppCompatActivity {

    private EditText ime;
    private EditText prezime;
    private EditText telefon;
    private EditText email;
    private EditText username;
    private EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija);

        ime = findViewById(R.id.ime);
        prezime = findViewById(R.id.prezime);
        telefon = findViewById(R.id.telefon);
        email = findViewById(R.id.email);
        username = findViewById(R.id.korisnickoIme);
        pass = findViewById(R.id.lozinka);

        Button btnRegistracija = findViewById(R.id.btnregistrujSe);
        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnRegistracijaClick();
            }
        });
    }

    private void do_btnRegistracijaClick() {
        if(validacija())
        {
            TuristPostVM model = new TuristPostVM();
            model.Ime = ime.getText().toString();
            model.Prezime = prezime.getText().toString();
            model.Email = email.getText().toString();
            model.Telefon = telefon.getText().toString();
            model.KorisnickoIme = username.getText().toString();
            model.LozinkaSalt = pass.getText().toString();

            MyApiRequest.post(this, "api/Turisti", model, new MyRunnable<TuristPostVM>(){
                @Override
                public void run(TuristPostVM x)
                {
                    Toast.makeText(registracija.this, "Uspjesna registracija.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private boolean validacija() {

        AlertDialog adb = new AlertDialog.Builder(registracija.this).create();
        adb.setTitle("Greška");
        adb.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(ime.getText().toString().length() < 3)
        {
            adb.setMessage("Ime treba da sadrzi minimalno 3 karaktera.");
            adb.show();
            return false;
        }

        if(prezime.getText().toString().length() < 3)
        {
            adb.setMessage("Prezime treba da sadrzi minimalno 3 karaktera.");
            adb.show();
            return false;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()==false)
        {
            adb.setMessage("Email nije u ispravnom formatu.");
            adb.show();
            return false;
        }

        if(telefon.getText().toString().length() < 6)
        {
            adb.setMessage("Telefon treba da sadrzi minimalno 6 karaktera.");
            adb.show();
            return false;
        }

        if(username.getText().toString().length() < 4)
        {
            adb.setMessage("Korisnicko ime treba da sadrzi minimalno 4 karaktera.");
            adb.show();
            return false;
        }

        if(true)//lozinka
        {
            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(pass.getText().toString());

            if(matcher.matches() == false)
            {
                adb.setMessage("Password treba sadrzavat minimalno 6 karaktera, najmanje jedan broj i kombinaciju malih/velikih slova.");
                adb.show();
                return false;
            }
        }

        return true;
    }
}
