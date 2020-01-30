package ir.ac.sbu.crisismanaging.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Location
{
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("areas")
    private List<String> areas;

    public Location(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
        areas = new ArrayList<>();
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public List<String> getAreas()
    {
        return areas;
    }

    public void setAreas(List<String> areas)
    {
        this.areas = areas;
    }
}
