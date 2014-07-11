/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.24.2014
 * Description: ReviewController.cs. Handles GET and POST request to access review from database and insert new review into database.
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
    public class ReviewController:BaseApiController
    {
        //
        // GET: /Review/        

        public HttpResponseMessage Get(string id)
        {
            var idsArray = id.Split('|');
            int userId =Convert.ToInt32(idsArray[0]);
            int sectorId = Convert.ToInt32(idsArray[1]);
            int segmentId = Convert.ToInt32(idsArray[2]);
            int placeId = Convert.ToInt32(idsArray[3]);
            Review review = null;
            if (sectorId != 0 && segmentId==0 && placeId==0)
            {
                review = TheRepository.GetReviewBySector(userId, sectorId);
            }
            else if (segmentId != 0 && sectorId == 0 && placeId == 0)
            {
                review = TheRepository.GetReviewBySegment(userId, segmentId);
            }
            else if (placeId != 0 && segmentId == 0 && sectorId == 0)
            {
                review = TheRepository.GetReviewByPlace(userId, placeId);
            }
            
            if (review != null)
            {
                return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateReviewModel(review, "success"));                
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "No Review Found!");
            }
        }


        //POST
        public HttpResponseMessage Post([FromBody] ReviewModel reviewModel)
        {
            try
            {
                Review review = null;                
                var entity = TheModelFactory.CreateReview(reviewModel);
                if(entity==null) Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not read review info from body");
                bool reviewExists = false;
                bool isInsertOperation = true;
                if (reviewModel.SectorId != 0 && reviewModel.SegmentId == 0 && reviewModel.PlaceId == 0)
                {
                    reviewExists = TheRepository.CheckReviewExistsForSector((Int32)reviewModel.UserId, (Int32)reviewModel.SectorId);
                    //do update code here.
                    if (reviewExists)
                    {
                        review = TheRepository.UpdateReviewSector(entity, (Int32)reviewModel.UserId, (Int32)reviewModel.SectorId);
                        isInsertOperation = false;
                    }
                }
                else if (reviewModel.SegmentId != 0 && reviewModel.SectorId == 0 && reviewModel.PlaceId == 0)
                {
                    reviewExists = TheRepository.CheckReviewExistsForSegment((Int32)reviewModel.UserId, (Int32)reviewModel.SegmentId);
                    //do update code here.
                    if (reviewExists)
                    {
                        review = TheRepository.UpdateReviewSegment(entity, (Int32)reviewModel.UserId, (Int32)reviewModel.SegmentId);
                        isInsertOperation = false;
                    }
                    
                }
                else if (reviewModel.PlaceId != 0 && reviewModel.SegmentId == 0 && reviewModel.SectorId == 0)
                {
                    reviewExists = TheRepository.CheckReviewExistsForPlace((Int32)reviewModel.UserId, (Int32)reviewModel.PlaceId);
                    //do update code here.
                    if (reviewExists)
                    {
                        review = TheRepository.UpdateReviewPlace(entity, (Int32)reviewModel.UserId, (Int32)reviewModel.PlaceId);
                        isInsertOperation = false;
                    }
                }
                if (review != null && reviewExists==true)
                {
                    return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateReviewModel(entity, "success"));
                }
                else if(review != null && reviewExists && !isInsertOperation)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Could not update to the database");
                }

                if (reviewExists == false) 
                { 
                    if(TheRepository.AddReview(entity))
                    {
                        return Request.CreateResponse(HttpStatusCode.Created, TheModelFactory.CreateReviewModel(entity, "success"));
                    
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

            return null;
        }
    }
}