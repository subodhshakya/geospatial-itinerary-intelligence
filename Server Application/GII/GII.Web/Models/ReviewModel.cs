using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class ReviewModel
    {
        public int ReviewId { get; set; }
        public double? Rating { get; set; }
        public string Comment { get; set; }
        public DateTime ReviewDate { get; set; }
        public int? UserId { get; set; }
        public int? SegmentId { get; set; }
        public int? PlaceId { get; set; }
        public int? SectorId { get; set; }
        public string message { get; set; }
    }
}