using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class CountryModel
    {
        public int CountryId { get; set; }
        public string Name { get; set; }
        public Nullable<int> CapitalCityId { get; set; }
        public string message { get; set; }
    }
}