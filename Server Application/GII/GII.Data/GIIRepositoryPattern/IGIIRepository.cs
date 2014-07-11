/************************************************************
 * Authors: Subodh Shakya
 * Organization: University of Alabama in Hunstsville
 * Course: CS687-Database Systems
 * Date: 03.10.2014
 * Description: IGIIRepository. Interface consisting of methods to access database
 *************************************************************/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GII.Data.GIIRepositoryPattern
{
    /// <summary>
    /// GII Repository.
    /// Consists of all functions to interact with GII DB Context
    /// </summary>
    public interface IGIIRepository
    {               
        // All Create Operations
        bool AddUser(User user);
        bool AddCoTraveler(CoTraveler coTraveler);
        bool AddSector(Sector sector);
        bool AddSegment(Segment segment);
        bool AddPlace(Place place);
        bool AddReview(Review review);
        bool AddPhoto(Photo photo);
        bool SaveAll();
        
        //Authenticaion
        User AuthenticateUser(string email_username, string password);
        bool CheckUserExists(string email_username);
        bool CheckReviewExistsForSector(int userId, int sectorId);
        bool CheckReviewExistsForSegment(int userId, int segmentId);
        bool CheckReviewExistsForPlace(int userId, int placeId);
        bool CheckSectorExists(int userId, int sectorId);
        bool CheckPlaceExists(string placeName, int cityId);

        // All Read Operations
        User GetUserInfo(int userId);
        User GetUserInfo(string username_email, string pwd);
        Sector GetSectorInfo(int userId);
        Sector GetSector(int sectorId);
        List<CoTraveler> GetCoTravelerInfo(int userId);
        Segment GetSegment(int segmentId);
        List<Sector> GetUserSector(string userId,int completed);
        List<Segment> GetSegmentList(int sectorId);
        List<Country> GetCountryList();
        Country GetCountry(int countryId);
        Review GetReview(int reviewId);
        Review GetReviewBySector(int userId, int sectorId);
        Review GetReviewBySegment(int userId, int segmentId);
        Review GetReviewByPlace(int userId, int placeId);
        Place GetPlace(int cityId);
        Photo GetPhoto(int userId, int placeId);
        List<City> GetCityList();
        City GetCity(int cityId);
        List<Segment> GetSegments(long OrginCityId, long DestinationCityId, double Cost, double? Distance, float Rating);

        // All Update Operations
        User UpdateUser(User user, string username_email, int userId);
        User UpdateNoOfTravellers(int userId, int noOfTravellers);
        Sector UpdateSegment(Segment segment);
        Sector UpdateSector(int sectorId, double cost, double distance);
        Sector UpdateSectorCompleted(int sectorId, int userId, bool completed);
        Review UpdateReviewSector(Review review, int userId, int sectorId);
        Review UpdateReviewSegment(Review review, int userId, int segmentId);
        Review UpdateReviewPlace(Review review, int userId, int placeId);
    }
}
