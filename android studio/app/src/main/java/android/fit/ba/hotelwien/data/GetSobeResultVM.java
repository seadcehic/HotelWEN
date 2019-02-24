package android.fit.ba.hotelwien.data;

import java.io.Serializable;
import java.util.List;

public class GetSobeResultVM implements Serializable {

    public static class Row implements Serializable {
        public int SobaID;
        public String NazivSobe;
        public String Cijena;
        public  String DatumPocetka;
        public String DatumZavrsetka;
    }
    public String ImaPodataka;
    public List<Row> rows;

}

