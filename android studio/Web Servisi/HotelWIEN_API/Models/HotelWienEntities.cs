namespace HotelWIEN_API.Models
{
    using System;
    using System.ComponentModel.DataAnnotations;
    using System.Data.Entity;
    using System.Linq;

    public class HotelWienEntities : DbContext
    {
      
        public HotelWienEntities()
            : base("name=HotelWienEntities")
        {
            //this.Configuration.ProxyCreationEnabled = false;
        }

         public virtual DbSet<Turist> Turist { get; set; }
        public virtual DbSet<Soba> Soba { get; set; }
        public virtual DbSet<Rezervacije> Rezervacije { get; set; }

        public virtual DbSet<AutorizacijskiToken> AutorizacijskiToken { get; set; }

    }


}