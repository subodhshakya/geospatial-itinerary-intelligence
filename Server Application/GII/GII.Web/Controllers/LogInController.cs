/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.19.2014
 * Description: LogInController.cs. Handles POST request to validate user credentials against
 * records available in database.
 *************************************************************/

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
    public class LogInController : BaseApiController
    {
        //
        // POST: /LogIn/

        public HttpResponseMessage Post([FromBody] LogInModel logInModel)
        {
            try
            {            
                if (logInModel == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read user info from body");


                bool userExists = TheRepository.CheckUserExists(logInModel.Email);
                if (userExists)
                {
                    GII.Data.User user = TheRepository.AuthenticateUser(logInModel.Email, logInModel.Password);
                    if (user.UserId != -1)
                    {
                        return Request.CreateResponse(HttpStatusCode.OK, TheModelFactory.CreateLogInModel(user, "success"));
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "login failed");
                    }
                }
                else
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "user doesn't exists");
                }
            }
            catch (Exception ex)
            {

                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

    }
}
