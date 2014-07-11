/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.15.2014
 * Description: BaseApiController.cs. Base class for all controller classes in the project.
 *************************************************************/

using GII.Data.GIIRepositoryPattern;
using GII.Web.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace GII.Web.Controllers
{
    public class BaseApiController : ApiController
    {
        private IGIIRepository _repo;
        private ModelFactory _modelFactory;
        
        public BaseApiController()
        {            
            _repo = new GIIRepository(new Data.GIIContext());
        }

        protected IGIIRepository TheRepository
        {
            get
            {
                return _repo;
            }
        }

        protected ModelFactory TheModelFactory
        {
            get
            {
                if (_modelFactory == null)
                {
                    _modelFactory = new ModelFactory(Request);
                }
                return _modelFactory;
            }
        }
    }
}
