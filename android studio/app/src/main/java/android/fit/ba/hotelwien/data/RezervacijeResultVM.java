package android.fit.ba.hotelwien.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RezervacijeResultVM implements Serializable {

    public static class Row implements Serializable {
        public int TuristID ;
        public String NazivSobe;
        public Date DatumZavrsetka;
        public int RezervacijaID ;
        public int SobaID ;
        public String Cijena;
        public Date DatumPocetka ;
    }
    public List<Row> rows;

}

