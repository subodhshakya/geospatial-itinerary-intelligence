/************************************************************
 * Authors: Binaya Raj Shrestha
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.13.2014
 * Description: WebApiConfig.cs. Configures web api to specify route of each resource.
 *************************************************************/
using Newtonsoft.Json.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Formatting;
using System.Web.Http;

namespace GII.Web
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            var jsonFormatter = config.Formatters.OfType<JsonMediaTypeFormatter>().First();
            jsonFormatter.SerializerSettings.ContractResolver = new CamelCasePropertyNamesContractResolver();

            config.Routes.MapHttpRoute(
                name: "UserInfo",
                routeTemplate: "api/userinfo/{id}",
                defaults: new { controller = "UserInfo", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "LogIn",
                routeTemplate: "api/login/",
                defaults: new { controller = "LogIn"}
            );

            config.Routes.MapHttpRoute(
                name: "Segment",
                routeTemplate: "api/segment/{id}",
                defaults: new { controller = "Segment", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Sector",
                routeTemplate: "api/sector/{id}",
                defaults: new { controller = "Sector", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Country",
                routeTemplate: "api/country/{id}",
                defaults: new { controller = "Country", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "City",
                routeTemplate: "api/city/{id}",
                defaults: new { controller = "City", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "CoTraveler",
                routeTemplate: "api/cotraveler/{id}",
                defaults: new { controller = "CoTraveler", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Search",
                routeTemplate: "api/search/{id}",
                defaults: new { controller = "Search", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Travel",
                routeTemplate: "api/travel/{id}",
                defaults: new { controller = "Travel", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Review",
                routeTemplate: "api/review/{id}",
                defaults: new { controller = "Review", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Place",
                routeTemplate: "api/place/{id}",
                defaults: new { controller = "Place", id = RouteParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Photo",
                routeTemplate: "api/photo/{id}",
                defaults: new { controller = "Photo", id = RouteParameter.Optional }
            );
        }
    }
}
