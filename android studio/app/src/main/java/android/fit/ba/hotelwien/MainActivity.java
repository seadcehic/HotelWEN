package android.fit.ba.hotelwien;

import android.content.Context;
import android.content.Intent;
import android.fit.ba.hotelwien.Util.Util;
import android.fit.ba.hotelwien.data.Global;
import android.fit.ba.hotelwien.fragments.listaRezervacija;
import android.fit.ba.hotelwien.fragments.listaSoba;
import android.fit.ba.hotelwien.fragments.pocetna;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Util.otvoriFragmentKaoReplace(this,R.id.mjestoZaFragment,pocetna.newInstance());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glavni, menu);
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pocetna) {
           Util.otvoriFragmentKaoReplace(this,R.id.mjestoZaFragment, pocetna.newInstance());

            // Handle the camera action
        } else if (id == R.id.sobe) {
            Util.otvoriFragmentKaoReplace(this,R.id.mjestoZaFragment, listaSoba.newInstance());

        } else if (id == R.id.rezervacije) {
            int TuristID= Global.prijavljeniTurist.TuristID;
            Util.otvoriFragmentKaoReplace(this,R.id.mjestoZaFragment, listaRezervacija.newInstance(TuristID));

        } else if (id == R.id.odjava) {
            MyApiRequest.delete(this,"api/Autentifikacija/Logout",null);
            startActivity(new Intent(this, login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
