/************************************************************
 * Authors: Archana Maharjan
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.20.2014
 * Description: PhotoController.cs. Handles GET and POST request to get photo and upload photo into database.
 *************************************************************/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using GII.Web.Models;
using GII.Data;
using System.Web.Mvc;
using System.Net.Http;
using System.Web.Http;
using System.Net;
using GII.Data.GIIRepositoryPattern;
using System.Data.Entity.Infrastructure;

namespace GII.Web.Controllers
{
    public class PhotoController : BaseApiController
    {
        //
        // GET: /Photo/

        public PhotoModel GetPhoto(string id)
        {
            var idsArray = id.Split('|');
            int userId = Convert.ToInt32(idsArray[0]);
            int cityId = Convert.ToInt32(idsArray[1]);
            var place = TheRepository.GetPlace(cityId);
            var photo = TheRepository.GetPhoto(userId, (int)place.PlaceId);
            if (photo != null)
            {
                return TheModelFactory.CreatePhotoModel(photo, "success");
            }
            else
            {
                return TheModelFactory.CreatePhotoModel(new Photo() { PhotoId = -1 }, "no photo found");
            }
        }

        //POST
        public HttpResponseMessage Post([FromBody] PhotoModel photoModel)
        {
            try
            {
                var entity = TheModelFactory.CreatePhoto(photoModel);
                if (entity == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read review info from body");
                if (TheRepository.AddPhoto(entity))
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreatePhotoModel(entity, "success"));

                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not save to the database");
                    }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
    }
}
