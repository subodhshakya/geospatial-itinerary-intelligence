using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class SearchParameterModel
    {
        public int OriginCityId { get; set; }
        public int DestinationCityId { get; set; }
        public double Cost { get; set; }
        public double? Distance { get; set; }
        public float Rating { get; set; }
    }
}