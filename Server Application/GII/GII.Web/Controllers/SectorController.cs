/************************************************************
 * Authors: Binaya Raj Shrestha
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.27.2014
 * Description: SectorController.cs. Handles GET and POST request to get sector from database and insert new sector into database.
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
    public class SectorController : BaseApiController
    {
        //
        // GET: /Sector/
        public IEnumerable<SectorListModel> GetSector(string id)
        {
            var idsArray = id.Split('|');
            string userId = idsArray[0];
            string itineraryType = idsArray[1];

            List<SectorListModel> sectorListModel = new List<SectorListModel>();
            List<SectorModel> sectorModelList = new List<SectorModel>();
            List<Sector> sectorList = TheRepository.GetUserSector(userId, Convert.ToInt16(itineraryType));
            foreach (Sector sector in sectorList)
            {
                List<SegmentModel> segmentModelList = new List<SegmentModel>();
                List<Segment> segmentList = TheRepository.GetSegmentList(Convert.ToInt32(sector.SectorId));

                foreach (Segment segment in segmentList)
                {
                    segmentModelList.Add(TheModelFactory.CreateSegmentModel(segment));
                }
                sectorModelList.Add(TheModelFactory.CreateSectorModel(sector, segmentModelList));
            }
            if (sectorModelList.Count() > 0)
            {
                sectorListModel.Add(new SectorListModel() { SectorList = sectorModelList });
                return sectorListModel;
            }
            else
            {
                List<SectorModel> sectorModelErrorList = new List<SectorModel>();
                sectorModelErrorList.Add(new SectorModel() { message = "no sector" });
                sectorListModel.Add(new SectorListModel() { SectorList = sectorModelErrorList });
                return sectorListModel;
            }
        }

        // POST
        public HttpResponseMessage Post([FromBody] SectorModel sectorModel)
        {
            try
            {
                List<Segment> segList = new List<Segment>();
                for (int i = 0; i < sectorModel.SegmentList.Count(); i++)
                {
                    segList.Add(TheModelFactory.CreateSegment(sectorModel.SegmentList[i]));
                }
                List<SegmentModel> segModel = new List<SegmentModel>();
                segModel = TheModelFactory.CreateSegmentList(segList);
                var entity = TheModelFactory.CreateSector(sectorModel, segList);
                Sector sector = null;
                if (entity == null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read review info from body");
                bool sectorExists = false;
                sectorExists = TheRepository.CheckSectorExists((int)sectorModel.UserId, (int)sectorModel.SectorId);
                if (sectorExists)
                {
                    sector = TheRepository.UpdateSectorCompleted((int)sectorModel.SectorId, (int)sectorModel.UserId, Convert.ToBoolean(sectorModel.Completed));
                    if (sector != null)
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateSectorModel(entity, segModel));
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not update to the database");
                    }
                }
                else
                {
                    if (TheRepository.AddSector(entity))
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateSectorModel(entity, sectorModel.SegmentList));
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not save to the database");
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
            return null;
        }
    }
}
