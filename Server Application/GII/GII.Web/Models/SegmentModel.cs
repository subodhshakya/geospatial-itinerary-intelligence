using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class SegmentModel
    {
        public int? SegmentId { get; set; }
        public int OriginCityId { get; set; }
        public int DestinationCityId { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public double? Distance { get; set; }
        public double? Cost { get; set; }
        public int? SectorId { get; set; }
        public int? ReviewId { get; set; }
        public int? NextSegmentId { get; set; }
        public string message { get; set; }
    }
}