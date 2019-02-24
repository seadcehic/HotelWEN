using HotelWIEN_API.Helper;
using HotelWIEN_API.Models;
using HotelWIEN_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace HotelWIEN_API.Controllers
{
    public class RezervacijeController : AuthToken
    {
        private HotelWienEntities db = new HotelWienEntities();

        [HttpGet]
        [Route("api/Rezervacije/getRezervacijeByTuristID/{turistID}")]
        public IHttpActionResult getRezervacijeByTuristID(string turistID)
        {
            if (ProvjeriValidnostTokena() == false)
                return Unauthorized();

            int IDint = Convert.ToInt32(turistID);

            RezervacijeResultVM model = new RezervacijeResultVM {
                rows=db.Rezervacije.Where(s=>s.TuristId== IDint).Select(s=>new RezervacijeResultVM.Row {
                    RezervacijaID=s.RezervacijaId,
                    TuristID=s.TuristId,
                    SobaID=s.SobaId,
                    NazivSobe=s.Soba.NazivSobe,
                    DatumPocetka=s.DatumPocetka,
                    DatumZavrsetka =s.DatumZavrsetka,
                    Cijena = 
                    Math.Round(((s.DatumZavrsetka.Day-s.DatumPocetka.Day+1)*s.Soba.Cijena),2)
                    .ToString()

                }).ToList()
            };

            return Ok(model);
        }
        [HttpGet]
        [Route("api/GetRezervaciju/{rezervacijaID}")]
        public IHttpActionResult getRezervaciju(string rezervacijaID) {
            int IDint = Convert.ToInt32(rezervacijaID);
            Rezervacije rezervacija = db.Rezervacije.Where(s => s.RezervacijaId == IDint).FirstOrDefault();

            GetRezervacijuResultVM model = new GetRezervacijuResultVM {
                RezervacijaID=rezervacija.RezervacijaId,
                NazivSobe=rezervacija.Soba.NazivSobe,
                DatumPocetka=rezervacija.DatumPocetka,
                DatumZavrsetka=rezervacija.DatumZavrsetka,
                Cijena =
                    Math.Round(((rezervacija.DatumZavrsetka.Day - rezervacija.DatumPocetka.Day+1) * rezervacija.Soba.Cijena), 2)
                    .ToString()
            };

            return Ok(model);
        }
    }
}
