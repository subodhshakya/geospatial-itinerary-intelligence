using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class CoTravelerModel
    {
        public int UserId { get; set; }
        public int CoTravelerId { get; set; }
        public string Name { get; set; }
        public string Relationship { get; set; }
        public string message { get; set; }
    }
}