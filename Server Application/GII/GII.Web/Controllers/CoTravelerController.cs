/************************************************************
 * Authors: Archana Maharjan
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.17.2014
 * Description: CoTravelerController.cs. City controller. Handles GET and POST request for 
 * accessing and inserting cotraveler into database.
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
    public class CoTravelerController : BaseApiController
    {
        //
        // GET: /CoTraveler/
        public IEnumerable<CoTravelerListModel> Get(int id)
        {
            List<CoTravelerListModel> coTravelerListModel = new List<CoTravelerListModel>();
            List<CoTravelerModel> coTravelerModelList = new List<CoTravelerModel>();
            List<CoTraveler> coTravelerList = TheRepository.GetCoTravelerInfo(id);
            if (coTravelerList.Count() > 0)
            {
                foreach (CoTraveler coTraveler in coTravelerList)
                {
                    coTravelerModelList.Add(TheModelFactory.CreateCoTravelerModel(coTraveler));
                }
                coTravelerListModel.Add(new CoTravelerListModel() { CoTravelerList = coTravelerModelList });
            }
            else
            {
                List<CoTravelerModel> errorCoTravelerModelList = new List<CoTravelerModel>();
                errorCoTravelerModelList.Add(new CoTravelerModel() { message = "no co-traveler" });
                coTravelerListModel.Add(new CoTravelerListModel() { CoTravelerList = errorCoTravelerModelList });
            }
            return coTravelerListModel;
        }

        public HttpResponseMessage Post([FromBody] CoTravelerModel coTravelerModel)
        {
            try
            { 
            var entity = TheModelFactory.CreateCoTraveler(coTravelerModel);
            if (entity == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Couldn't read co-traveler info from the body");
            var coTravelerSuccess = TheRepository.AddCoTraveler(entity);
            var user = TheRepository.GetUserInfo((int)entity.UserId);
            var userUpdate = TheRepository.UpdateNoOfTravellers((int)user.UserId, (int)user.NoOfTraveler + 1);
            return Request.CreateResponse(HttpStatusCode.Created, "Co-travelers added.");
            }
            catch(Exception ex)
            {
                throw ex;
            }
        }
    }
}
