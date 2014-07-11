using GII.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;

namespace GII.Web.Models
{
    public class ModelFactory
    {
        private System.Web.Http.Routing.UrlHelper _UrlHelper;

        public ModelFactory(HttpRequestMessage request)
        {
            _UrlHelper = new System.Web.Http.Routing.UrlHelper(request);
        }

        public UserModel CreateUserModel(User user, string msg)
        {
            return new UserModel()
            {
                UserId = user.UserId,
                Url = _UrlHelper.Link("UserInfo", new { id = user.UserId }),
                FirstName = user.FirstName,
                MiddleName = user.MiddleName,
                LastName = user.LastName,
                Email = user.Email,
                Password = user.Password,
                Street = user.Street,
                City = user.City,
                State = user.State,
                Country = user.Country,
                ZipCode = user.ZipCode,
                NoOfTraveler = user.NoOfTraveler,
                message = msg
            };
        }

        public LogInModel CreateLogInModel(User user,string msg)
        {
            return new LogInModel()
            {
                UserId = user.UserId.ToString(),
                Email = user.Email,
                Password = "******",
                FirstName = user.FirstName,
                MiddleName = user.MiddleName,
                LastName = user.LastName,
                message = msg
            };
        }

        public SegmentModel CreateSegmentModel(Segment segment)
        {
            return new SegmentModel()
            {
                Cost = segment.Cost,
                DestinationCityId = segment.DestinationCityId,
                Distance = segment.Distance,
                EndDate = segment.EndDate,
                NextSegmentId = segment.NextSegmentId,
                OriginCityId = segment.OriginCityId,
                ReviewId = segment.ReviewId,
                SectorId = segment.SectorId,
                SegmentId = segment.SegmentId,
                StartDate = segment.StartDate,
                message = "success"
            };
        }

        public User CreateUser(UserModel userModel)
        { 
            try
            {
                var user = new User()
                    {                        
                        FirstName = userModel.FirstName,
                        MiddleName = userModel.MiddleName,
                        LastName = userModel.LastName,
                        Email = userModel.Email,
                        Street = userModel.Street,
                        City = userModel.City,
                        State = userModel.State,
                        Country = userModel.Country,
                        ZipCode = userModel.ZipCode,
                        NoOfTraveler = userModel.NoOfTraveler,
                        Password = userModel.Password
                    };

                return user;
            }
            catch (Exception)
            {                
                throw null;
            }
        }

        public CoTravelerModel CreateCoTravelerModel(CoTraveler coTraveler)
        {
            return new CoTravelerModel()
            {
                CoTravelerId = coTraveler.CoTravelerId,
                Name = coTraveler.Name,
                Relationship = coTraveler.Relationship,
                UserId = coTraveler.UserId,
                message = "success"
            };
        }

        public CoTraveler CreateCoTraveler(CoTravelerModel coTravelerModel)
        {
            return new CoTraveler()
            {
                CoTravelerId = coTravelerModel.UserId,
                Name = coTravelerModel.Name,
                Relationship = coTravelerModel.Relationship,
                UserId = coTravelerModel.UserId
            };
        }

        public SectorModel CreateSectorModel(Sector sector, List<SegmentModel> segmentList)
        {
            return new SectorModel()
            {
                Cost = sector.Cost,
                DestinationCityId = sector.DestinationCityId,
                Distance = sector.Distance,
                NoOfSegments = sector.NoOfSegments,
                OriginCityId = sector.OriginCityId,
                PlanDate = sector.PlanDate,
                UserId = sector.UserId,
                SegmentList = segmentList,
                message = "success",
                Completed = sector.Completed == true ? 1 : 0
            };
        }

        public Sector CreateSector(SectorModel sectorModel, List<Segment> segmentlList)
        {
            return new Sector()
            {
                Cost = sectorModel.Cost,
                DestinationCityId = sectorModel.DestinationCityId,
                Distance = sectorModel.Distance,
                NoOfSegments = sectorModel.NoOfSegments,
                OriginCityId = sectorModel.OriginCityId,
                PlanDate = sectorModel.PlanDate,
                UserId = sectorModel.UserId,
                Segments = segmentlList,
                Completed = sectorModel.Completed == 1 ? true : false
            };
        }

        public Segment CreateSegment(SegmentModel segModel)
        {
            return new Segment()
            {
                Cost = segModel.Cost,
                DestinationCityId = segModel.DestinationCityId,
                Distance = segModel.Distance,
                EndDate = segModel.EndDate,
                NextSegmentId = segModel.NextSegmentId,
                OriginCityId = segModel.OriginCityId,
                ReviewId = segModel.ReviewId,
                SectorId = segModel.SectorId,
                StartDate = segModel.StartDate,
                SegmentId = (int)segModel.SegmentId
            };
        }

        public List<SegmentModel> CreateSegmentList(List<Segment> segList)
        {
            List<SegmentModel> segementModelList = new List<SegmentModel>();
            foreach (Segment seg in segList)
            {
                
                segementModelList.Add(CreateSegmentModel(seg));
            }
            return segementModelList;
        }
        public CountryModel CreateCountryModel(Country country, string msg)
        {
            return new CountryModel()
            {
                CapitalCityId = country.CapitalCityId,
                CountryId = country.CountryId,
                Name = country.Name,
                message = msg
            };
        }

        public CityModel CreateCityModel(City city, string msg)
        {
            return new CityModel()
            {
                CityId = city.CityId,
                CountryId = city.CountryId,
                Name = city.Name,
                ZipCode = city.ZipCode,
                message = msg
            };
        }

        public ReviewModel CreateReviewModel(Review review, string msg)
        {
            return new ReviewModel()
            {
                ReviewId = review.ReviewId,
                Rating = (double)review.Rating == null ? 0.0: review.Rating,
                Comment = string.IsNullOrEmpty(review.Comment)==true?string.Empty:review.Comment,
                ReviewDate = review.ReviewDate == null ? DateTime.Now : review.ReviewDate,
                //UserId = review.UserId,
               // SegmentId = (int)review.SegmentId == null ? 0 : (int)review.SegmentId,
               // PlaceId = (int)review.PlaceId == null ? 0 : (int)review.PlaceId,
               // SectorId = (int)review.SectorId == null ? 0 : (int)review.SectorId,
                message = string.IsNullOrEmpty(msg) == true?string.Empty:msg
            };
        }

        public Review CreateReview(ReviewModel reviewModel)
        {
            try
            {
                var review = new Review()
                {
                    ReviewId = reviewModel.ReviewId,
                    Rating = reviewModel.Rating,
                    Comment = reviewModel.Comment,
                    ReviewDate = reviewModel.ReviewDate,
                    UserId = (int)reviewModel.UserId,
                    SegmentId = reviewModel.SegmentId,
                    PlaceId = reviewModel.PlaceId,
                    SectorId = reviewModel.SectorId
                };
                return review;                
            }
            catch (Exception)
            {
                throw null;
            }
        }

        public Place CreatePlace(PlaceModel placeModel)
        { 
            try
            {
                var place = new Place() { 
                    PlaceId=placeModel.PlaceId,
                    Name=placeModel.Name,
                    CityId=placeModel.CityId,
                    XCoordinate=placeModel.XCoordinate,
                    YCoordinate=placeModel.YCoordinate
                };
                return place;
            }
            catch(Exception)
            {
                throw null;
            }
        }

        public PlaceModel CreatePlaceModel(Place place, string msg)
        {
            return new PlaceModel()
            {
                PlaceId = place.PlaceId,
                Name = place.Name,
                CityId = place.CityId,
                XCoordinate = (double)place.XCoordinate,
                YCoordinate = (double)place.YCoordinate,
                Message = msg
            };
        }

        public Photo CreatePhoto(PhotoModel photoModel)
        {
            try
            {
                var photo = new Photo()
                {
                    PhotoId = photoModel.PhotoId,
                    Image=photoModel.Image,
                    PlaceId=photoModel.PlaceId,
                    UserId=photoModel.UserId,
                    UploadDate=photoModel.UploadDate
                };
                return photo;
            }
            catch (Exception)
            {
                throw null;
            }
        }

        public PhotoModel CreatePhotoModel(Photo photo, string msg) 
        {
            return new PhotoModel()
            {
                PhotoId=photo.PhotoId,
                Image=photo.Image,
                PlaceId=(int)photo.PlaceId,
                UserId=(int)photo.UserId,
                UploadDate=(DateTime)photo.UploadDate,
                Message=msg
            };
        }
    }
}