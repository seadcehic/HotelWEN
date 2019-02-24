using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.ModelVM
{
    public class RezervacijeResultVM
    {
        public List<Row> rows { get; set; }

        public class Row
        {
            public int TuristID { get; internal set; }
            public string NazivSobe { get; internal set; }
            public DateTime DatumZavrsetka { get; internal set; }
            public int RezervacijaID { get; internal set; }
            public int SobaID { get; internal set; }
            public string Cijena { get; internal set; }
            public DateTime DatumPocetka { get; internal set; }
        }
    }
}