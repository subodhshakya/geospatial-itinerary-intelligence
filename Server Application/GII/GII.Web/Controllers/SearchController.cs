/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.25.2014
 * Description: SearchController.cs. Handles POST request to search segments into the database.
 *************************************************************/

using GII.Data;
using GII.Web.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace GII.Web.Controllers
{
    public class SearchController : BaseApiController
    {
        //
        // GET: /Search/
        public IEnumerable<SegmentListModel> Post([FromBody] SearchParameterModel searchParamModel)
        {
            List<Segment> segmentList = TheRepository.GetSegments(searchParamModel.OriginCityId,
                searchParamModel.DestinationCityId, searchParamModel.Cost,
                searchParamModel.Distance, searchParamModel.Rating);
            List<SegmentModel> segmentModelList = new List<SegmentModel>();
            foreach (Segment segment in segmentList)
            {
                segmentModelList.Add(TheModelFactory.CreateSegmentModel(segment));
            }
            List<SegmentListModel> segmentListModel = new List<SegmentListModel>();
            if(segmentModelList.Count() > 0)
            {
                segmentListModel.Add(new SegmentListModel() { SegmentList = segmentModelList });
                return segmentListModel;
            }
            else
            {
                List<SegmentModel> errorSegmentModelList = new List<SegmentModel>();
                errorSegmentModelList.Add(new SegmentModel(){  message ="no segment"});
                segmentListModel.Add(new SegmentListModel() { SegmentList = errorSegmentModelList });
                return segmentListModel;
            }
        }
    }
}
