/************************************************************
 * Authors: Archana Maharjan
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.27.2014
 * Description: SegmentController.cs. Handles GET and POST request to get segment from database and insert new segment into database.
 *************************************************************/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Mvc;

namespace GII.Web.Controllers
{
    public class SegmentController : BaseApiController
    {
        //
        // GET: /Segment/

        public string Get()
        {
            return "Test Get String";
        }

        public HttpResponseMessage GetSegment(int id)
        {
            try
            {
                var segment = TheRepository.GetSegment(id);
                if (segment != null)
                {
                    return Request.CreateResponse(HttpStatusCode.OK, TheModelFactory.CreateSegmentModel(segment));
                }
                else
                {
                    return Request.CreateResponse(HttpStatusCode.NotFound);
                }                
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
    }
}
