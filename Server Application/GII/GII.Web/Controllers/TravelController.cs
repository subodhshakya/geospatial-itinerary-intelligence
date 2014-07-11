/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.28.2014
 * Description: TravelController.cs. Handles POST request to update travel information into the database
 *************************************************************/

using GII.Data;
using GII.Web.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace GII.Web.Controllers
{
    public class TravelController : BaseApiController
    {
        //
        // GET: /Travel/
        // POST
        public HttpResponseMessage Post([FromBody] SegmentModel segmentModel)
        {
            var entity = TheModelFactory.CreateSegment(segmentModel);
            Sector updatedSector = TheRepository.UpdateSegment(entity);
            List<SegmentModel> segmentModelList = new List<SegmentModel>();
            foreach(Segment seg in updatedSector.Segments)
            {
                segmentModelList.Add(TheModelFactory.CreateSegmentModel(seg));
            }

            if (updatedSector != null)
            {
                return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateSectorModel(updatedSector,segmentModelList));
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not update database");
            }
        }
    }
}
