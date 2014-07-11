/************************************************************
 * Authors: Binaya Raj Shrestha
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 04.01.2014
 * Description: UserInfoController.cs. Handles GET and POST request to get user information from database and insert new record of user into database.
 *************************************************************/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GII.Data;
using GII.Data.GIIRepositoryPattern;
using System.Web.Http;
using System.Net.Http;
using System.Net;
using GII.Web.Models;
using System.Data.Entity.Infrastructure;

namespace GII.Web.Controllers
{    
    public class UserInfoController : BaseApiController    
    {
        //
        // GET: /UserInfo/        
        public string Get()
        {
            return "Test Get String";
        }

        // HTTP Verb: GET
        public HttpResponseMessage GetUserInfo(int id)
        {
            try
            {
                var userInfo = TheRepository.GetUserInfo(id);
                if (userInfo != null)
                {
                    return Request.CreateResponse(HttpStatusCode.OK, TheModelFactory.CreateUserModel(userInfo,"user found"));
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

        
        public HttpResponseMessage Post([FromBody] UserModel userModel)
        {
            try
            {

                var entity = TheModelFactory.CreateUser(userModel);

                if (entity == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read user info from body");

                bool userExists = TheRepository.CheckUserExists(userModel.Email);
                if (!userExists)
                {                    
                    if (TheRepository.AddUser(entity))
                    {
                        entity = TheRepository.GetUserInfo(entity.Email, entity.Password);
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateUserModel(entity,"success"));
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not save to the database");
                    }
                }
                else
                {
                    var usr = TheRepository.UpdateUser(entity, userModel.Email, (int)userModel.UserId);
                    if (usr != null)
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateUserModel(entity, "success"));
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not update to the database");
                    }
                }
            }
            catch (DbUpdateException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotAcceptable, ex);
            }
            catch (Exception ex)
            {

                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
    }
}
