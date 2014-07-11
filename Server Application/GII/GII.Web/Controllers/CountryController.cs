/************************************************************
 * Authors: Archana Maharjan
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.17.2014
 * Description: CountryController.cs. Handles GET request for 
 * accessing countries from database.
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
    public class CountryController : BaseApiController
    {
        //
        // GET: /Country/
        public IEnumerable<CountryListModel> Get()
        {
            List<CountryListModel> countryListModel = new List<CountryListModel>();
            List<Country> countryList = TheRepository.GetCountryList();
            List<CountryModel> countryModelList = new List<CountryModel>();
            foreach (Country country in countryList)
            {
                countryModelList.Add(TheModelFactory.CreateCountryModel(country, "success"));
            }
            countryListModel.Add(new CountryListModel() { CountryList = countryModelList });
            return countryListModel;
        }

        public CountryModel GetCountry(int id)
        {
            Country country = TheRepository.GetCountry(id);
            if (country != null)
            { return TheModelFactory.CreateCountryModel(country, "success"); }
            else
            { return TheModelFactory.CreateCountryModel(new Country() { CountryId = -1 }, "no country found"); }
        }
    }
}
