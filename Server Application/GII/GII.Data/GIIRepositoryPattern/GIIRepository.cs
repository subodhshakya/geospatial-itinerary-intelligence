/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.10.2014
 * Description: GIIRepository. Repository consisting of methods to access database
 *************************************************************/

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GII.Data.GIIRepositoryPattern
{
    public class GIIRepository : IGIIRepository
    {
        private GIIContext _ctx;

        public GIIRepository(GIIContext ctx)
        {
            _ctx = ctx;
        }

        public bool AddUser(User user)
        {
            try
            {
                string queryInsertUser = "INSERT INTO dbo.[User] (FirstName,MiddleName,LastName,Email,Password,Street,City,State,Country,Zipcode,NoOfTraveler)" +
                    "VALUES (@firstname,@middlename,@lastname,@email,@password,@street,@city,@state,@country,@zipcode,@nooftraveler);";
                var args = new DbParameter[] 
                {                    
                    new SqlParameter {ParameterName = "@firstname",Value = user.FirstName},
                    new SqlParameter {ParameterName = "@middlename",Value = user.MiddleName},
                    new SqlParameter {ParameterName = "@lastname",Value = user.LastName},
                    new SqlParameter {ParameterName = "@email",Value = user.Email},
                    new SqlParameter {ParameterName = "@password",Value = user.Password},
                    new SqlParameter {ParameterName = "@street",Value = user.Street},
                    new SqlParameter {ParameterName = "@city",Value = user.City},
                    new SqlParameter {ParameterName = "@state",Value = user.State},
                    new SqlParameter {ParameterName = "@country",Value = user.Country},
                    new SqlParameter {ParameterName = "@zipcode",Value = user.ZipCode},
                    new SqlParameter {ParameterName = "@nooftraveler",Value = user.NoOfTraveler}
                };
                _ctx.Database.ExecuteSqlCommand(queryInsertUser, args);
                SaveAll();
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public bool AddCoTraveler(CoTraveler coTraveler)
        {
            try
            {
                string queryInsertCoTraveler = "INSERT INTO dbo.[CoTraveler] (UserId,Name,Relationship)" +
                    "VALUES (@userid,@coTravelerName,@relationship)";
                var args = new DbParameter[] 
                {
                    new SqlParameter{ ParameterName = "@userid",Value = coTraveler.UserId},
                    new SqlParameter{ ParameterName = "@coTravelerName", Value = coTraveler.Name},
                    new SqlParameter{ ParameterName = "@relationship", Value = coTraveler.Relationship}
                };
                _ctx.Database.ExecuteSqlCommand(queryInsertCoTraveler, args);
                SaveAll();
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public bool AddSector(Sector sector)
        {
            try
            {
                sector.OriginCityId = sector.Segments.FirstOrDefault().OriginCityId;
                sector.DestinationCityId = sector.Segments.LastOrDefault().DestinationCityId;
                sector.PlanDate = DateTime.Now;
                IEnumerable<Segment> allSegment = sector.Segments;
                allSegment = allSegment.OrderBy(c => c.NextSegmentId);
                sector.Segments = null;
                _ctx.Sectors.Add(sector);
                SaveAll();
                foreach (Segment segment in allSegment)
                {                    
                    segment.SectorId = sector.SectorId;
                    AddSegment(segment);
                    SaveAll();
                }

                List<Segment> segmentList = new List<Segment>();
                segmentList = GetSegments((int)sector.SectorId);
                segmentList = segmentList.OrderBy(c=>c.NextSegmentId).ToList();

                if (segmentList.Count().Equals(1))
                {
                    UpdateNextSegmentId(segmentList.FirstOrDefault(), int.MaxValue);
                    SaveAll();
                }
                else
                {
                    int segCount = segmentList.Count();
                    for (int i = 0; i < segCount - 1; i++)
                    {
                        UpdateNextSegmentId(segmentList[i], (int)segmentList[i + 1].SegmentId);
                        SaveAll();
                    }
                    UpdateNextSegmentId(segmentList[segCount-1], 1000);
                    SaveAll();
                }
                return true;
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        public bool SaveAll()
        {
            return _ctx.SaveChanges() > 0;
        }


        public bool AddSegment(Segment segment)
        {
            try
            {
                string queryInsertSegment = "INSERT INTO dbo.[Segment] (OriginCityId,DestinationCityId,StartDate,EndDate,Distance,Cost,SectorId,ReviewId,NextSegmentId)" +
                    "VALUES (@originCityId,@destinationCityId,@startDate,@endDate,@distance,@cost,@sectorId,@reviewId,@nextSegmentId)";

                var args = new DbParameter[] 
                {
                    new SqlParameter{ ParameterName = "@originCityId",Value = segment.OriginCityId},
                    new SqlParameter{ ParameterName = "@destinationCityId", Value = segment.DestinationCityId},
                    new SqlParameter{ ParameterName = "@startDate", Value = segment.StartDate== new DateTime(0001,1,1) ? new DateTime(2000,1,1):segment.StartDate},
                    new SqlParameter{ ParameterName = "@endDate", Value = segment.EndDate == new DateTime(0001,1,1) ? new DateTime(2000,1,1):segment.EndDate},
                    new SqlParameter{ ParameterName = "@distance", Value = segment.Distance==null?0:segment.Distance},
                    new SqlParameter{ ParameterName = "@cost", Value = segment.Cost == null?0:segment.Cost},
                    new SqlParameter{ ParameterName = "@sectorId", Value = segment.SectorId == null? -1 : segment.SectorId},
                    new SqlParameter{ ParameterName = "@reviewId", Value = segment.ReviewId == null ? -1:segment.ReviewId},
                    new SqlParameter{ ParameterName = "@nextSegmentId", Value = segment.NextSegmentId==null?-1:segment.NextSegmentId}                                            
                };

                _ctx.Database.ExecuteSqlCommand(queryInsertSegment, args);                                
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


        public bool AddPlace(Place place)
        {
            try
            {
                string queryInsertPlace = "INSERT INTO dbo.[Place] (Name, CityId, XCoordinate, YCoordinate)" +
                    "VALUES (@name,@cityId,@xCoordinate,@yCoordinate);";
                var args = new DbParameter[] 
                {                    
                    new SqlParameter {ParameterName = "@name",Value = place.Name},
                    new SqlParameter {ParameterName = "@cityId", Value = place.CityId},
                    new SqlParameter {ParameterName = "@xCoordinate",Value = place.XCoordinate},
                    new SqlParameter {ParameterName = "@yCoordinate",Value = place.YCoordinate}
                };
                _ctx.Database.ExecuteSqlCommand(queryInsertPlace, args);
                SaveAll();
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public bool AddPhoto(Photo photo)
        {
            try
            {
                string queryInsertPhoto = "INSERT INTO dbo.[Photo] (Image, PlaceId, UserId, UploadDate)" +
                    "VALUES (@image,@placeId,@userId,@uploadDate);";
                var args = new DbParameter[] 
                {                    
                    new SqlParameter {ParameterName = "@image",Value = photo.Image},
                    new SqlParameter {ParameterName = "@placeId", Value = photo.PlaceId},
                    new SqlParameter {ParameterName = "@userId",Value = photo.UserId},
                    new SqlParameter {ParameterName = "@uploadDate",Value = photo.UploadDate}
                };
                _ctx.Database.ExecuteSqlCommand(queryInsertPhoto, args);
                SaveAll();
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public User AuthenticateUser(string email_username, string password)
        {
            string query = "SELECT * FROM dbo.[User] WHERE Email = @p0 AND Password = @p1";

            var args = new DbParameter[] { new SqlParameter { ParameterName = "p0", Value = email_username },
            new SqlParameter{ ParameterName = "p1", Value = password }};

            User user = _ctx.Users.SqlQuery(query, args).SingleOrDefault();

            if (user != null)
            {
                return user;
            }
            else
            {
                return new User() { UserId = -1 };
            }
        }

        public bool CheckUserExists(string email_username)
        {
            string query = "SELECT * FROM dbo.[User] WHERE Email = @p0";
            User user = _ctx.Users.SqlQuery(query, email_username).SingleOrDefault();
            if (user != null && !string.IsNullOrEmpty(user.Email))
            { return true; }
            else
            { return false; }
        }

        public User GetUserInfo(int userId)
        {
            // p0 is parameter 0 (first parameter)
            string query = "SELECT * FROM dbo.[User] WHERE UserId = @p0";
            User user = _ctx.Users.SqlQuery(query, userId).SingleOrDefault();
            return user;
        }

        public User GetUserInfo(string username_email, string pwd)
        {
            // query to find user id
            string queryUser = "SELECT * FROM dbo.[User] WHERE Email = @username AND Password = @password";
            var args = new DbParameter[] 
            { 
                new SqlParameter { ParameterName = "username", Value = username_email },
                new SqlParameter { ParameterName = "password", Value = pwd } 
            };
            User user = _ctx.Users.SqlQuery(queryUser, args).FirstOrDefault();
            return user;
        }

        public Sector GetSectorInfo(int userId)
        {
            string query = "SELECT * FROM dbo.[Sector] WHERE UserId = @p0";
            Sector sector = _ctx.Sectors.SqlQuery(query, userId).SingleOrDefault();
            return sector;
        }

        public Sector GetSector(int sectorId)
        {
            string query = "SELECT * FROM dbo.[Sector] WHERE SectorId = @p0";
            Sector sector = _ctx.Sectors.SqlQuery(query, sectorId).SingleOrDefault();
            return sector;
        }

        public List<CoTraveler> GetCoTravelerInfo(int userId)
        {
            string query = "SELECT * FROM dbo.[CoTraveler] WHERE UserId = @p0";
            List<CoTraveler> cotravelerList = _ctx.CoTravelers.SqlQuery(query, userId).ToList();
            return cotravelerList;
        }

        public Segment GetSegment(int segmentId)
        {
            string query = "SELECT * FROM dbo.[Segment] WHERE SegmentId = @p0";
            Segment segment = _ctx.Segments.SqlQuery(query, segmentId).SingleOrDefault();
            return segment;
        }

        private List<Segment> GetSegments(int sectorId)
        {
            string query = "SELECT * FROM dbo.[Segment] WHERE SectorId = @p0";
            List<Segment> segmentList = _ctx.Segments.SqlQuery(query, sectorId).ToList();
            return segmentList;
        }


        public List<Sector> GetUserSector(string userId,int completed)
        {
            string query = "SELECT * FROM dbo.[Sector] WHERE UserId = @userId AND Completed = @completed";
            var args = new DbParameter[] { 
                new SqlParameter { ParameterName = "userId", Value = userId },
                new SqlParameter { ParameterName = "completed", Value = completed }
            };            

            List<Sector> sectorList = _ctx.Sectors.SqlQuery(query, args).ToList();
            return sectorList;
        }

        List<Segment> IGIIRepository.GetSegmentList(int sectorId)
        {
            string querySegment = "SELECT * FROM dbo.[Segment] WHERE SectorId = @p1";

            var args = new DbParameter[] { new SqlParameter { ParameterName = "p1", Value = sectorId } };
            List<Segment> segmentList = _ctx.Segments.SqlQuery(querySegment, args).ToList();
            return segmentList;
        }


        public List<Country> GetCountryList()
        {
            string queryCountry = "SELECT * FROM dbo.[Country]";
            List<Country> countryList = _ctx.Countries.SqlQuery(queryCountry).ToList();
            return countryList;
        }

        public Country GetCountry(int countryId)
        {
            string queryCountry = "SELECT * FROM dbo.[Country] WHERE CountryId = @p1";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "p1", Value = countryId } };
            Country country = _ctx.Countries.SqlQuery(queryCountry, args).FirstOrDefault();
            return country;
        }

        public List<City> GetCityList()
        {
            string queryCity = "SELECT * FROM dbo.[City]";
            List<City> cityList = _ctx.Cities.SqlQuery(queryCity).ToList();
            return cityList;
        }

        public City GetCity(int cityId)
        {
            string queryCity = "SELECT * FROM dbo.[City] WHERE CityId = @p1";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "p1", Value = cityId } };
            City city = _ctx.Cities.SqlQuery(queryCity, args).FirstOrDefault();
            return city;
        }

        public List<Segment> GetSegments(long OrginCityId, long DestinationCityId, double Cost, double? Distance, float Rating)
        {            
            string selectSegmentQuery = "SELECT * FROM dbo.[Segment] WHERE ";
            string conditionOriginCityId = " OriginCityId = @originCityId ";
            string conditionDestinationCityId = " DestinationCityId = @destinationCityId ";
            string conditionCost = " Cost <= @cost ";
            string conditionDistance = " Distance <= @distance ";
            
            string queryConditions = "";
            bool firstConditionSelected = false;

            List<SqlParameter> argList = new List<SqlParameter>();

            if (OrginCityId != -1)
            {
                firstConditionSelected = true;
                queryConditions += conditionOriginCityId;
                argList.Add(new SqlParameter { ParameterName = "originCityId", Value = OrginCityId });                
            }
            if (DestinationCityId != -1)
            {
                if (firstConditionSelected == false)
                {
                    firstConditionSelected = true;
                    queryConditions += conditionDestinationCityId;
                }
                else
                {
                    queryConditions += " AND " + conditionDestinationCityId;
                }
                argList.Add(new SqlParameter { ParameterName = "destinationCityId", Value = DestinationCityId });
            }
            if (Cost != 0)
            {
                if (firstConditionSelected == false)
                {
                    firstConditionSelected = true;
                    queryConditions += conditionCost;
                }
                else
                {
                    queryConditions += " AND " + conditionCost;
                }
                argList.Add(new SqlParameter {  ParameterName ="cost", Value = Cost});
            }
            if (Distance != 0)
            {
                if (firstConditionSelected == false)
                {
                    firstConditionSelected = true;
                    queryConditions += conditionDistance;
                }
                else
                {
                    queryConditions += " AND " + conditionDistance;
                }
                argList.Add(new SqlParameter {  ParameterName = "distance",Value = Distance});
            }
            
            
            var args = argList.ToArray();          

            List<Segment> segmentList = _ctx.Segments.SqlQuery(selectSegmentQuery + queryConditions,args).ToList();
            
            if (Rating != 0)
            {
                Review r = new Review();
                List<Segment> segmentListFilteredByReview = new List<Segment>();
                double val = 0;
                foreach (Segment seglist in segmentList)
                { 
                    ///****************TODO************************************************
                    r = GetReview((int)seglist.ReviewId);
                    val = (double)r.Rating;
                    if (val<Rating || val == Rating)
                    {
                        segmentListFilteredByReview.Add(seglist);
                    }
                }
                return segmentListFilteredByReview;
            }
            return segmentList;
        }

        private bool UpdateNextSegmentId(Segment segment, int nextSegmentId)
        {
            string updateNextSegmentIdQuery = "UPDATE dbo.[Segment] SET NextSegmentId = @nextSegmentId WHERE SegmentId=@segmentId";
            var args = new DbParameter[] { new SqlParameter { ParameterName="nextSegmentId", Value = nextSegmentId },
            new SqlParameter{ ParameterName ="segmentId",Value=segment.SegmentId}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateNextSegmentIdQuery, args);
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        private int GetSegmentId(int sectorId, int originCityId)
        {
            string getSegmentIdQuery = "SELECT * FROM dbo.[Segment] WHERE SectorId = @sectorId AND OriginCityId = @orginCityId";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "sectorId",Value = sectorId },
            new SqlParameter{ ParameterName ="orginCityId", Value = originCityId}};
            try
            {
                Segment segment = _ctx.Segments.SqlQuery(getSegmentIdQuery, args).FirstOrDefault();
                return (int)segment.SegmentId;
            }
            catch (Exception ex)
            {
                return -1;
            }
        }


        public Sector UpdateSegment(Segment newSegment)
        {
            int sectorId = (int)newSegment.SectorId;
            int segmentId = (int)newSegment.SegmentId;
            
            try
            {
                string updateSegmentQuery = "UPDATE dbo.[Segment] SET Distance = @distance,Cost = @cost, StartDate = @startDate, EndDate = @endDate WHERE SegmentId=@segmentId AND SectorId = @sectorId";
                var argsSegment = new DbParameter[] { 
                    new SqlParameter { ParameterName = "distance",Value = newSegment.Distance },
                    new SqlParameter{ ParameterName ="cost", Value = newSegment.Cost},
                    new SqlParameter{ ParameterName ="startDate", Value = newSegment.StartDate},
                    new SqlParameter{ ParameterName ="endDate", Value = newSegment.EndDate},
                    new SqlParameter{ ParameterName ="segmentId", Value = newSegment.SegmentId},
                    new SqlParameter{ ParameterName ="sectorId", Value = newSegment.SectorId}
                };

                _ctx.Database.ExecuteSqlCommand(updateSegmentQuery, argsSegment);
                _ctx.SaveChanges();
            }
            catch (Exception ex)
            {
                throw ex;
            }

            Sector sector = GetSector(sectorId);
            double totalCost = 0;
            double totalDistance = 0;
            foreach (Segment seg in sector.Segments)
            { 
                totalCost += seg.Cost == null ? 0 : (double)seg.Cost;
                totalDistance += seg.Distance == null ? 0 : (double)seg.Distance;
            }
            sector.Cost = totalCost;
            sector.Distance = totalDistance;

            Sector latestSector = UpdateSector(sectorId, totalCost, totalDistance);

            return latestSector;
        }


        public Sector UpdateSector(int sectorId, double cost, double distance)
        {
            try
            {
                string updateSectorQuery = "UPDATE dbo.[Sector] SET Cost = @seccost, Distance = @secdistance WHERE SectorId = @secId";
                var argsSector = new DbParameter[] {
                    new SqlParameter {ParameterName = "seccost",Value = cost},
                    new SqlParameter {ParameterName = "secdistance",Value = distance},
                    new SqlParameter {ParameterName = "secId",Value = sectorId}
                };

                _ctx.Database.ExecuteSqlCommand(updateSectorQuery, argsSector);
                _ctx.SaveChanges();
            }
            catch (Exception ex)
            {
                throw ex;
            }

            try
            {
                string getSectorQuery = "SELECT * FROM dbo.[Sector] WHERE SectorId = @sectorId";
                var args = new DbParameter[] { 
                new SqlParameter { ParameterName = "sectorId",Value = sectorId }};
                Sector latestSector = _ctx.Sectors.SqlQuery(getSectorQuery, args).FirstOrDefault();
                SaveAll();
                return latestSector;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //check Sector Exists or Not
        public bool CheckSectorExists(int userId, int sectorId)
        {
            string query = "SELECT * FROM dbo.[Sector] WHERE UserId = @userId AND SectorId = @sectorId";

            var args = new DbParameter[] 
            { 
                new SqlParameter{ParameterName = "userId", Value = userId},
                new SqlParameter{ParameterName = "sectorId", Value = sectorId}
            };

            Sector sector = _ctx.Sectors.SqlQuery(query, args).SingleOrDefault();





            //checking whether review is null or not for given userId and segmentId
            if (sector != null)
            { return true; }
            else
            { return false; }
        }


        //Update Sector for Complete field
        public Sector UpdateSectorCompleted(int sectorId, int userId, bool completed)
        {
            try
            {
                string updateSectorCompletedQuery = "UPDATE dbo.[Sector] SET Completed = @completed WHERE SectorId = @sectorId AND UserId = @userId";
                var argsSector = new DbParameter[] {
                    new SqlParameter {ParameterName = "completed",Value = completed},
                    new SqlParameter {ParameterName = "sectorId",Value = sectorId},
                    new SqlParameter {ParameterName = "userId",Value = userId}
                };

                _ctx.Database.ExecuteSqlCommand(updateSectorCompletedQuery, argsSector);
                _ctx.SaveChanges();
            }
            catch (Exception ex)
            {
                throw ex;
            }

            try
            {
                string getSectorQuery = "SELECT * FROM dbo.[Sector] WHERE SectorId = @sectorId AND UserId = @userId";
                var args = new DbParameter[] { 
                new SqlParameter { ParameterName = "sectorId",Value = sectorId },
                new SqlParameter { ParameterName = "userId",Value = userId }};
                Sector latestSector = _ctx.Sectors.SqlQuery(getSectorQuery, args).FirstOrDefault();
                SaveAll();
                return latestSector;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //GetReview by Id
        public Review GetReview(int reviewId)
        {
            string queryReview = "SELECT * FROM dbo.[Review] WHERE ReviewId = @p";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "p", Value = reviewId } };
            Review review = _ctx.Reviews.SqlQuery(queryReview, args).FirstOrDefault();
            return review;
        }

        //GetReviewBySector
        public Review GetReviewBySector(int userId, int sectorId)
        {
            string queryReviewBySector = "SELECT * FROM dbo.[Review] WHERE UserId=@userId AND SectorId=@sectorId";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "userId", Value = userId },
                                           new SqlParameter { ParameterName = "sectorId", Value = sectorId }};
            Review review = _ctx.Reviews.SqlQuery(queryReviewBySector, args).FirstOrDefault();
            return review;
        }

        //GetReviewBySegment
        public Review GetReviewBySegment(int userId, int segmentId)
        {
            string queryReviewBySegment = "SELECT * FROM dbo.[Review] WHERE UserId=@userId AND SegmentId=@segmentId";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "userId", Value = userId },
                                           new SqlParameter { ParameterName = "segmentId", Value = segmentId }};
            Review review = _ctx.Reviews.SqlQuery(queryReviewBySegment, args).FirstOrDefault();
            return review;
        }

        //GetReviewByPlace
        public Review GetReviewByPlace(int userId, int placeId)
        {
            string queryReviewByPlace = "SELECT * FROM dbo.[Review] WHERE UserId=@userId AND PlaceId=@placeId";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "userId", Value = userId },
                                           new SqlParameter { ParameterName = "placeId", Value = placeId }};
            Review review = _ctx.Reviews.SqlQuery(queryReviewByPlace, args).FirstOrDefault();
            return review;
        }

        //Insert Review
        public bool AddReview(Review review)
        {
            try
            {
                string queryInsertReview = "INSERT INTO dbo.[Review] (Rating, Comment, ReviewDate, UserId, SegmentId, PlaceId, SectorId)" +
                    "VALUES (@rating,@comment,@reviewDate,@userId,@segmentId,@placeId,@sectorId);";
                var args = new DbParameter[] 
                {                    
                    new SqlParameter {ParameterName = "@rating",Value = review.Rating},
                    new SqlParameter {ParameterName = "@comment", Value = review.Comment},
                    new SqlParameter {ParameterName = "@reviewDate",Value = review.ReviewDate},
                    new SqlParameter {ParameterName = "@userId",Value = review.UserId},
                    new SqlParameter {ParameterName = "@segmentId",Value = review.SegmentId==0?(object)DBNull.Value:review.SegmentId},
                    new SqlParameter {ParameterName = "@placeId",Value = review.PlaceId==0?(object)DBNull.Value:review.PlaceId},
                    new SqlParameter {ParameterName = "@sectorId",Value = review.SectorId==0?(object)DBNull.Value:review.SectorId}
                };
                _ctx.Database.ExecuteSqlCommand(queryInsertReview, args);
                SaveAll();
                return true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //CheckReviewExistsForSector
        public bool CheckReviewExistsForSector(int userId, int sectorId)
        {
            string query = "SELECT * FROM dbo.[Review] WHERE UserId = @userId AND SectorId = @sectorId";
            

            var args = new DbParameter[] 
            { 
                new SqlParameter{ParameterName = "userId", Value = userId},
                new SqlParameter{ParameterName = "sectorId", Value = sectorId}
            };
            Review review = _ctx.Reviews.SqlQuery(query, args).SingleOrDefault();

            //checking whether review is null or not for given userId and sectorId
            if (review != null)
            { return true; }
            else
            { return false; }
        }

        //CheckReviewExistsForSegment
        public bool CheckReviewExistsForSegment(int userId, int segmentId)
        {
            string query = "SELECT * FROM dbo.[Review] WHERE UserId = @userId AND SegmentId = @segmentId";

            var args = new DbParameter[] 
            { 
                new SqlParameter{ParameterName = "userId", Value = userId},
                new SqlParameter{ParameterName = "segmentId", Value = segmentId}
            };

            Review review = _ctx.Reviews.SqlQuery(query, args).SingleOrDefault();

            //checking whether review is null or not for given userId and segmentId
            if (review != null)
            { return true; }
            else
            { return false; }
        }
        
        public bool CheckReviewExistsForPlace(int userId, int placeId)
        {
            string query = "SELECT * FROM dbo.[Review] WHERE UserId = @userId AND PlaceId = @placeId";

            var args = new DbParameter[] 
            { 
                new SqlParameter{ParameterName = "userId", Value = userId},
                new SqlParameter{ParameterName = "placeId", Value = placeId}
            };

            Review review = _ctx.Reviews.SqlQuery(query, args).SingleOrDefault();
            //checking whether review is null or not for given userId and placeId
            if (review != null)
            { return true; }
            else
            { return false; }
        }
        
        public bool CheckPlaceExists(string placeName, int cityId)
        {
            string query = "SELECT * FROM dbo.[Place] WHERE Name = @placeName AND CityId = @cityId";

            var args = new DbParameter[] 
            { 
                new SqlParameter{ParameterName = "placeName", Value = placeName},
                new SqlParameter{ParameterName = "cityId", Value = cityId}
            };

            Place place = _ctx.Places.SqlQuery(query, args).SingleOrDefault();
            //checking whether review is null or not for given userId and placeId
            if (place != null)
            { return true; }
            else
            { return false; }
        }
        
        public Review UpdateReviewSector(Review review, int userId, int sectorId)
        {
            DateTime dtToday = DateTime.Now;
            string updateReviewSector = "UPDATE dbo.[Review] SET Rating = @rating, Comment = @comment, ReviewDate = @reviewDate WHERE UserId=@userId AND SectorId=@sectorId";
            var args = new DbParameter[] { new SqlParameter { ParameterName="rating", Value = review.Rating },
            new SqlParameter{ ParameterName ="comment",Value=review.Comment},
            new SqlParameter{ParameterName = "reviewDate", Value=dtToday},
            new SqlParameter{ParameterName = "userId", Value = userId},
            new SqlParameter{ParameterName = "sectorId", Value = sectorId}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateReviewSector, args);
                _ctx.SaveChanges();
                return GetReviewBySector(userId, sectorId);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        
        public Review UpdateReviewSegment(Review review, int userId, int segmentId)
        {
            DateTime dtToday = DateTime.Now;
            string updateReviewSegment = "UPDATE dbo.[Review] SET Rating = @rating, Comment = @comment, ReviewDate = @reviewDate WHERE UserId=@userId AND SegmentId=@segmentId";
            var args = new DbParameter[] { new SqlParameter { ParameterName="rating", Value = review.Rating },
            new SqlParameter{ ParameterName ="comment",Value=review.Comment},
            new SqlParameter{ParameterName = "reviewDate", Value=dtToday},
            new SqlParameter{ParameterName = "userId", Value = userId},
            new SqlParameter{ParameterName = "segmentId", Value = segmentId}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateReviewSegment, args);
                _ctx.SaveChanges();
                return GetReviewBySegment(userId, segmentId);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        
        public Review UpdateReviewPlace(Review review, int userId, int placeId)
        {
            DateTime dtToday = DateTime.Now;
            string updateReviewPlace = "UPDATE dbo.[Review] SET Rating = @rating, Comment = @comment, ReviewDate = @reviewDate WHERE UserId=@userId AND PlaceId=@placeId";
            var args = new DbParameter[] { new SqlParameter { ParameterName="rating", Value = review.Rating },
            new SqlParameter{ ParameterName ="comment",Value=review.Comment},
            new SqlParameter{ParameterName = "reviewDate", Value=dtToday},
            new SqlParameter{ParameterName = "userId", Value = userId},
            new SqlParameter{ParameterName = "placeId", Value = placeId}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateReviewPlace, args);
                _ctx.SaveChanges();
                return GetReviewByPlace(userId, placeId);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //update User Profile
        public User UpdateUser(User user, string username_email, int userId)
        {
            string updateUser = "UPDATE dbo.[User] SET FirstName=@firstName, MiddleName=@middleName, LastName=@lastName, Password =@password, Street=@street, City=@city, State=@state, Country=@country, ZipCode=@zipCode, NoOfTraveler=@noOfTraveler WHERE Email=@userName AND UserId=@userId";
            var args = new DbParameter[] {                   
                    new SqlParameter {ParameterName = "@userId",Value = userId},
                    new SqlParameter {ParameterName = "@firstName",Value = user.FirstName},
                    new SqlParameter {ParameterName = "@middleName",Value = user.MiddleName},
                    new SqlParameter {ParameterName = "@lastName",Value = user.LastName},
                    new SqlParameter {ParameterName = "@email",Value = username_email},
                    new SqlParameter {ParameterName = "@password",Value = user.Password},
                    new SqlParameter {ParameterName = "@street",Value = user.Street},
                    new SqlParameter {ParameterName = "@city",Value = user.City},
                    new SqlParameter {ParameterName = "@state",Value = user.State},
                    new SqlParameter {ParameterName = "@country",Value = user.Country},
                    new SqlParameter {ParameterName = "@zipCode",Value = user.ZipCode},
                    new SqlParameter {ParameterName = "@noOfTraveler",Value = user.NoOfTraveler}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateUser, args);
                _ctx.SaveChanges();
                return GetUserInfo(Convert.ToInt32(userId));
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //update User's Number of Travellers
        public User UpdateNoOfTravellers(int userId, int noOfTravellers)
        {
            string updateNoOfTravellers = "UPDATE dbo.[User] SET NoOfTraveler=@noOfTraveler WHERE UserId=@userId";
            var args = new DbParameter[] {                   
                    new SqlParameter {ParameterName = "@userId",Value = userId},
                    new SqlParameter {ParameterName = "@noOfTraveler",Value = noOfTravellers}};
            try
            {
                _ctx.Database.ExecuteSqlCommand(updateNoOfTravellers, args);
                _ctx.SaveChanges();
                return GetUserInfo(Convert.ToInt32(userId));
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //get place by City Id
        public Place GetPlace(int cityId)
        {
            string queryPlace = "SELECT * FROM dbo.[Place] WHERE CityId=@cityId";
            var args = new DbParameter[] { new SqlParameter { ParameterName = "cityid", Value = cityId }};
            Place place = _ctx.Places.SqlQuery(queryPlace, args).FirstOrDefault();
            return place;
        }

        //get photo by Photo Id
        public Photo GetPhoto(int userId, int placeId)
        {
            string queryPhoto = "SELECT * FROM dbo.[Photo] WHERE UserId=@userId AND PlaceId=@placeId";
            var args = new DbParameter[] { 
            new SqlParameter{ParameterName ="userId", Value=userId},
            new SqlParameter{ParameterName="placeId", Value=placeId}};
            Photo photo = _ctx.Photos.SqlQuery(queryPhoto, args).FirstOrDefault();
            return photo;
        }
    }
}
