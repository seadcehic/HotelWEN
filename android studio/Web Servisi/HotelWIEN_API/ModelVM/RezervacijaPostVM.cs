using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.ModelVM
{
    public class RezervacijaPostVM
    {
        public int SobaId { get; set; }
      
        public int TuristId { get; set; }
        public string OD { get; set; }
        public string DO { get; set; }

    }
}