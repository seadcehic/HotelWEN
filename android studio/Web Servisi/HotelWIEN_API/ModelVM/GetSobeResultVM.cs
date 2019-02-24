using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.ModelVM
{
    public class GetSobeResultVM
    {
        public List<Row> rows { get; set; }
        public string ImaPodataka { get; set; }
        public class Row
        {
            public int SobaID { get; internal set; }
            public string NazivSobe { get; internal set; }
            public decimal Cijena { get; internal set; }
            public string DatumPocetka { get; internal set; }
         
            public string DatumZavrsetka { get; internal set; }
        }
    }
}