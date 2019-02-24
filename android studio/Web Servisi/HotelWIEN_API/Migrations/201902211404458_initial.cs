namespace HotelWIEN_API.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.AutorizacijskiToken",
                c => new
                    {
                        AutorizacijskiTokenID = c.Int(nullable: false, identity: true),
                        Vrijednost = c.String(),
                        VrijemeEvidentiranja = c.DateTime(),
                        IpAdresa = c.String(),
                        DeviceInfo = c.String(),
                        TuristId = c.Int(),
                    })
                .PrimaryKey(t => t.AutorizacijskiTokenID)
                .ForeignKey("dbo.Turist", t => t.TuristId)
                .Index(t => t.TuristId);
            
            CreateTable(
                "dbo.Turist",
                c => new
                    {
                        TuristId = c.Int(nullable: false, identity: true),
                        Ime = c.String(),
                        Prezime = c.String(),
                        Telefon = c.String(),
                        Email = c.String(),
                        KorisnickoIme = c.String(),
                        LozinkaSalt = c.String(),
                        LozinkaHash = c.String(),
                    })
                .PrimaryKey(t => t.TuristId);
            
            CreateTable(
                "dbo.Rezervacije",
                c => new
                    {
                        RezervacijaId = c.Int(nullable: false, identity: true),
                        DatumPocetka = c.DateTime(nullable: false),
                        DatumZavrsetka = c.DateTime(nullable: false),
                        SobaId = c.Int(nullable: false),
                        TuristId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.RezervacijaId)
                .ForeignKey("dbo.Soba", t => t.SobaId, cascadeDelete: true)
                .ForeignKey("dbo.Turist", t => t.TuristId, cascadeDelete: true)
                .Index(t => t.SobaId)
                .Index(t => t.TuristId);
            
            CreateTable(
                "dbo.Soba",
                c => new
                    {
                        SobaId = c.Int(nullable: false, identity: true),
                        NazivSobe = c.String(),
                        Cijena = c.Decimal(nullable: false, precision: 18, scale: 2),
                    })
                .PrimaryKey(t => t.SobaId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Rezervacije", "TuristId", "dbo.Turist");
            DropForeignKey("dbo.Rezervacije", "SobaId", "dbo.Soba");
            DropForeignKey("dbo.AutorizacijskiToken", "TuristId", "dbo.Turist");
            DropIndex("dbo.Rezervacije", new[] { "TuristId" });
            DropIndex("dbo.Rezervacije", new[] { "SobaId" });
            DropIndex("dbo.AutorizacijskiToken", new[] { "TuristId" });
            DropTable("dbo.Soba");
            DropTable("dbo.Rezervacije");
            DropTable("dbo.Turist");
            DropTable("dbo.AutorizacijskiToken");
        }
    }
}
