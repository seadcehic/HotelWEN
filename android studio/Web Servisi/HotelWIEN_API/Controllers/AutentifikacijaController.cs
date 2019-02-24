using HotelWIEN_API.Helper;
using HotelWIEN_API.Models;
using HotelWIEN_API.ModelVM;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace HotelWIEN_API.Controllers
{
    public class AutentifikacijaController : AuthToken
    {
        private HotelWienEntities db = new HotelWienEntities();

        [HttpGet]
        [Route("api/Autentifikacija/LoginCheck/{username}/{pass}")]
        public IHttpActionResult LoginCheck(string username, string pass)
        {
            string token = Guid.NewGuid().ToString();

            Turist turist = db.Turist.Where(s => s.KorisnickoIme == username && s.LozinkaSalt == pass).FirstOrDefault();
            // unutar lozinkaSalt je smjesten string password

            if (turist != null)
            {
                AutentifikacijaResultVM a = new AutentifikacijaResultVM();
                a.TuristID = turist.TuristId;
                a.Ime = turist.Ime;
                a.Prezime = turist.Prezime;
                a.KorisnickoIme = turist.KorisnickoIme;
                a.LozinkaSalt = turist.LozinkaSalt;
                a.Telefon = turist.Telefon;
                a.Email = turist.Email;
                a.Token = token;

                db.AutorizacijskiToken.Add(new AutorizacijskiToken
                {
                    Vrijednost = a.Token,
                    TuristId = a.TuristID,
                    VrijemeEvidentiranja = DateTime.Now,
                    IpAdresa = "..."
                });

                db.SaveChanges();

                return Ok(a);
            }

            AutentifikacijaResultVM y = new AutentifikacijaResultVM();
            y.Ime = "PogresniPodaci";

            return Ok(y);
        }

        [HttpDelete]
        [Route("api/Autentifikacija/Logout")]
        public IHttpActionResult Logout()
        {
            IzbrisiToken();
            return Ok();
        }
    }
}
