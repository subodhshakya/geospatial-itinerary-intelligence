package com.uah.geospatialitineraryintelligence.datamodel;
/**
 * @author Subodh
 *
 */
import java.io.Serializable;
import java.util.ArrayList;

public class GIISector implements Serializable {
	public int SectorId;
	public int OriginCityId ;
    public int DestinationCityId ;
    public String PlanDate ;
    public double Cost ;
    public double Distance ;
    public int NoOfSegments ;
    public int UserId ;
    public String message ;
    public int completed;
    public ArrayList<GIISegment> GIISegmentList;
}
