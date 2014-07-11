/************************************************************
 * Authors: Archana Maharjan
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.17.2014
 * Description: CityController.cs. City controller. Handles GET request for accessing all city in database.
 *************************************************************/

using GII.Data;
using GII.Web.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace GII.Web.Controllers
{
    public class CityController : BaseApiController
    {
        //
        // GET: /City/
        public IEnumerable<CityListModel> Get()
        {
            List<CityListModel> cityListModel = new List<CityListModel>();
            List<City> cityList = TheRepository.GetCityList();
            List<CityModel> cityModelList = new List<CityModel>();
            foreach (City city in cityList)
            {
                cityModelList.Add(TheModelFactory.CreateCityModel(city, "success"));
            }
            cityListModel.Add(new CityListModel() { CityList = cityModelList });
            return cityListModel;
        }

        public CityModel GetCity(int id)
        {
            City city = TheRepository.GetCity(id);
            if (city != null)
            { return TheModelFactory.CreateCityModel(city, "success"); }
            else
            { return TheModelFactory.CreateCityModel(new City() { CityId = -1 }, "no city found"); }
        }
    }
}
