using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class SectorModel
    {
        public int SectorId { get; set; }
        public int OriginCityId { get; set; }
        public int DestinationCityId { get; set; }
        public System.DateTime PlanDate { get; set; }
        public double Cost { get; set; }
        public double? Distance { get; set; }
        public int NoOfSegments { get; set; }
        public int UserId { get; set; }
        public string message { get; set; }
        public int Completed { get; set; }
        public List<SegmentModel> SegmentList { get; set; }
    }
}