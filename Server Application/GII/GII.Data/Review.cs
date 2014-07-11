//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace GII.Data
{
    using System;
    using System.Collections.Generic;
    
    public partial class Review
    {
        public int ReviewId { get; set; }
        public Nullable<double> Rating { get; set; }
        public string Comment { get; set; }
        public System.DateTime ReviewDate { get; set; }
        public int UserId { get; set; }
        public Nullable<int> SegmentId { get; set; }
        public Nullable<int> PlaceId { get; set; }
        public Nullable<int> SectorId { get; set; }
    
        public virtual Place Place { get; set; }
        public virtual Sector Sector { get; set; }
        public virtual User User { get; set; }
    }
}