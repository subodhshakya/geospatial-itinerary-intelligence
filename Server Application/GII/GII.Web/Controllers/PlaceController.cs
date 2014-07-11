/************************************************************
 * Authors: Binaya Raj Shrestha
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.21.2014
 * Description: PlaceController.cs. Handles GET and POST get place information from database and insert new place into the database.
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
    public class PlaceController : BaseApiController
    {
        //
        // GET: /Place/

        // Gets place by CityId
        public PlaceModel GetPlace(int id)
        {
                var place = TheRepository.GetPlace(id);
                if (place != null)
                {
                    return TheModelFactory.CreatePlaceModel(place, "success");
                }
                else
                {
                    return TheModelFactory.CreatePlaceModel(new Place { PlaceId = -1 }, "no place found");
                }
        }



        //POST
        public HttpResponseMessage Post([FromBody] PlaceModel placeModel)
        {
            try
            {
                Place place = null;
                var entity = TheModelFactory.CreatePlace(placeModel);
                if (entity == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read review info from body");
                bool placeExists = false;
                
                placeExists = TheRepository.CheckPlaceExists(placeModel.Name, (Int32)placeModel.CityId);
                    //do update code here.
                if(placeExists)
                {
                    //either update or send entity back to user
                    return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreatePlaceModel(entity, "success"));
                }
               
                else 
                {
                   if (TheRepository.AddPlace(entity))
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreatePlaceModel(entity, "success"));

                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not save to the database");
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

    }
}
