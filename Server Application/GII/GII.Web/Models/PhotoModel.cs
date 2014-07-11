using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GII.Web.Models
{
    public class PhotoModel
    {
        public int PhotoId { get; set; }
        public byte[] Image { get; set; }
        public int PlaceId { get; set; }
        public int UserId { get; set; }
        public DateTime UploadDate { get; set; }
        public string Message { get; set; }
    }
}