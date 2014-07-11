using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class PlaceModel
    {
        public int PlaceId { get; set; }
        public string Name { get; set; }
        public int CityId { get; set; }
        public double XCoordinate { get; set; }
        public double YCoordinate { get; set; }
        public string Message { get; set; }
    }
}
