using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class CityModel
    {
        public int CityId { get; set; }
        public string Name { get; set; }
        public int? ZipCode { get; set; }
        public int CountryId { get; set; }
        public string message { get; set; }        
    }
}