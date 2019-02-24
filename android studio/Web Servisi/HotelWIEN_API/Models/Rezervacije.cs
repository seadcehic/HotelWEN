using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.Models
{
    [Table("Rezervacije")]

    public class Rezervacije
    {
        [Key]
        public int RezervacijaId { get; set; }

        public DateTime DatumPocetka { get; set; }
        public DateTime DatumZavrsetka { get; set; }
        public int SobaId { get; set; }
        [ForeignKey("SobaId")]
        public virtual Soba Soba { get; set; }
        public int TuristId { get; set; }
       [ForeignKey("TuristId")]
        public virtual Turist Turist { get; set; }

    }
}