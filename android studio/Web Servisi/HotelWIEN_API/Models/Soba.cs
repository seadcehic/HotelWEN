using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace HotelWIEN_API.Models
{
   [Table("Soba")]
    public class Soba
    {
        [Key]
        public int SobaId { get; set; }
        public string NazivSobe { get; set; }
        public decimal Cijena { get; set; }

    }
}