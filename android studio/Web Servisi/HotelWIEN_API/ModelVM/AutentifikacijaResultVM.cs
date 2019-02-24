using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.ModelVM
{
    public class AutentifikacijaResultVM
    {
        public int TuristID { get; internal set; }
        public string KorisnickoIme { get; internal set; }
        public string LozinkaSalt { get; internal set; }
        public string Telefon { get; internal set; }
        public string Ime { get; internal set; }
        public string Prezime { get; internal set; }
        public string Email { get; internal set; }
        public string Token { get; internal set; }
    }
}