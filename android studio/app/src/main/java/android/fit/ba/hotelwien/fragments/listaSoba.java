package android.fit.ba.hotelwien.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.fit.ba.hotelwien.Util.Util;
import android.fit.ba.hotelwien.data.GetSobeResultVM;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.fit.ba.hotelwien.R;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;


public class listaSoba extends Fragment {
    private ListView listaSoba;
    private GetSobeResultVM podaci;
    private BaseAdapter adapter;
    private Calendar calendar;
    private DatePickerDialog dialogCalendar;
    private int yearOD;
    private int monthOD;
    private int dayOD;
    private int yearDO;
    private int monthDO;
    private int  dayDO;
    private Button btnOD;
    private Button btnDO;
    private Button trazi;
    private String datumOD;
    private String datumDO;


    public static android.fit.ba.hotelwien.fragments.listaSoba newInstance() {
        android.fit.ba.hotelwien.fragments.listaSoba fragment = new listaSoba();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.termini, container, false);
        btnOD = view.findViewById(R.id.datumPocetni);
        btnDO = view.findViewById(R.id.datumZavrsni);
        trazi = view.findViewById(R.id.trazi);

        btnOD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                 int month= calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                dialogCalendar=new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dayOD=dayOfMonth;
                        monthOD=month;
                        yearOD=year;
                    }
                }, year, month, day);
                dialogCalendar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCalendar.show();
            }
        });

        btnDO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month= calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                dialogCalendar=new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dayDO=dayOfMonth;
                        monthDO=month;
                        yearDO=year;
                    }
                }, year, month, day);
                dialogCalendar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCalendar.show();
            }
        });







        listaSoba = view.findViewById(R.id.listaSoba);
        trazi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popuniPodatkeTask();
            }
        });

        listaSoba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //verzija za azzure
                datumOD=(monthOD+1)+"-"+dayOD+"-"+yearOD;
                datumDO=(monthDO+1)+"-"+dayDO+"-"+yearDO;

                String cijena= String.valueOf((int)Double.parseDouble(podaci.rows.get(position).Cijena));
                String ukupnaCijena= String.valueOf((dayDO-dayOD+1)*Integer.parseInt(cijena));
                int sobaID=podaci.rows.get(position).SobaID;
              String nazivSobe=podaci.rows.get(position).NazivSobe;
               Util.otvoriFragmentKaoReplace(getActivity(),R.id.mjestoZaFragment, odredjenaSoba.newInstance(ukupnaCijena,sobaID,nazivSobe,datumOD,datumDO));
            }
        });


        return view;
    }

    private void popuniPodatkeTask() {
        //VERZIJA KAD RADIM SA VISUALOM POVEZANIM SA NGROK(LOCALHOST)
       // datumOD=dayOD+"-"+(monthOD+1)+"-"+yearOD;
        //datumDO=dayDO+"-"+(monthDO+1)+"-"+yearDO;


        //VERZIJA ZA AZZURE (PUBLISH API)
        datumOD=(monthOD+1)+"-"+dayOD+"-"+yearOD;
        datumDO=(monthDO+1)+"-"+dayDO+"-"+yearDO;

        MyApiRequest.get(getActivity(), "api/Sobe/GetSobe/"+datumOD+","+datumDO, new MyRunnable<GetSobeResultVM>() {

            @Override
            public void run(GetSobeResultVM x) {
                if(("NemaPodataka").equals(x.ImaPodataka))
                {
                    Toast.makeText(getActivity(), "Nema niti jedne slobodne sobe.",Toast.LENGTH_LONG).show();
                }
                else{
                    podaci = x;
                    popuniListu();
                }
            }
        });
    }

    private void popuniListu() {
        if (podaci.rows.size() > 0) {
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return podaci.rows.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View view, ViewGroup parent) {
                    if (view == null) {

                        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        view = inflater.inflate(R.layout.stavka_termini, parent, false);
                    }
                    TextView txtNazivSobe = view.findViewById(R.id.nazivSobeTermini);
                    TextView txtCijenaPoTerminu = view.findViewById(R.id.cijenaPoTerminu);

                    GetSobeResultVM.Row x = podaci.rows.get(position);

                    txtNazivSobe.setText(x.NazivSobe);
                    txtCijenaPoTerminu.setText(x.Cijena+" KM");

                    return view;
                }
            };

            listaSoba.setAdapter(adapter);
        }
    }
}