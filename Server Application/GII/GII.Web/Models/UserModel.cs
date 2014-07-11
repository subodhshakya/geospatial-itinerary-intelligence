using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class UserModel
    {
        public int UserId { get; set; }
        public string Url { get; set; }
        public string FirstName { get; set; }
        public string MiddleName { get; set; }
        public string LastName { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }        
        public string Street { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string Country { get; set; }
        public int? ZipCode { get; set; }
        public int? NoOfTraveler { get; set; }
        public string message { get; set; }
    }
}