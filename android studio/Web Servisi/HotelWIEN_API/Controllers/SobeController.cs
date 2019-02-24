using HotelWIEN_API.Helper;
using HotelWIEN_API.Models;
using HotelWIEN_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Linq;

using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;

namespace HotelWIEN_API.Controllers
{
    public class SobeController : AuthToken
    {
        private HotelWienEntities baza = new HotelWienEntities();


        [Route("api/Sobe/GetSobe/{datumOD},{datumDO}")]
        public IHttpActionResult GetSobe(string datumOD, string datumDO)
        {
            DateTime OD = Convert.ToDateTime(datumOD);
         
            GetSobeResultVM model = new GetSobeResultVM();
            DateTime DO = Convert.ToDateTime(datumDO);
          
            List<Rezervacije> sveRezervacije = baza.Rezervacije.ToList();
            List<Rezervacije> zauzeteRezervacije = new List<Rezervacije>();

            foreach (var sveRez in sveRezervacije)
            {
                if((DO>=sveRez.DatumPocetka) &&( DO<=sveRez.DatumZavrsetka))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if ((OD >= sveRez.DatumPocetka) && (DO <= sveRez.DatumZavrsetka))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if ((OD>=sveRez.DatumPocetka) &&( OD<=sveRez.DatumZavrsetka))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
                if((OD<sveRez.DatumPocetka) && (DO>sveRez.DatumZavrsetka))
                {
                    zauzeteRezervacije.Add(sveRez);
                }
            }
            List<Soba> sveSobe = baza.Soba.ToList();
            List<Soba> slobodneSobe = new List<Soba>();

            if (zauzeteRezervacije.Count == 0)
            {
                slobodneSobe = baza.Soba.ToList();
            }
            else
            {
                foreach (var soba in sveSobe)
                {
                    bool zabrani = false;
                    foreach (var rez in zauzeteRezervacije)
                    {
                        if (soba.SobaId == rez.SobaId)
                        {
                            zabrani = true;
                        }
                    }
                    if (!zabrani)
                    {
                        slobodneSobe.Add(soba);
                    }
                }
            }
            

            if (slobodneSobe.Count != 0)
            {
                model.rows = slobodneSobe.Select(x => new GetSobeResultVM.Row
                {
                    SobaID = x.SobaId,
                    NazivSobe = x.NazivSobe,
                    Cijena = x.Cijena,
                    DatumPocetka = OD.ToString(),
                    DatumZavrsetka = DO.ToString()
                }).ToList();
                return Ok(model);
            }
            else {
                model.ImaPodataka = "NemaPodataka";
                return Ok(model);
            }
        }

        [ResponseType(typeof(Soba))]
       
        public IHttpActionResult PostSoba([FromBody] RezervacijaPostVM soba)
        {
            DateTime datumOD = Convert.ToDateTime(soba.OD);
            DateTime datumDO = Convert.ToDateTime(soba.DO);
       
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            Rezervacije rezervacija = new Rezervacije {
                SobaId = soba.SobaId,
                TuristId=soba.TuristId,
                DatumPocetka=Convert.ToDateTime(soba.OD),
                DatumZavrsetka=Convert.ToDateTime(soba.DO)
            };

            baza.Rezervacije.Add(rezervacija);
            baza.SaveChanges();

            return Ok();
        }
      
    }
}
