package com.sandy.wayzon_android.livecricketscore.model;

public class MatchDetails
{
    private String time;

    private String match_key;

    private String first_team_name;

    private String second_team_name;

    private String match_id;

    private String date;

    private String venue;


    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getMatch_key ()
    {
        return match_key;
    }

    public void setMatch_key (String match_key)
    {
        this.match_key = match_key;
    }

    public String getFirst_team_name() {
        return first_team_name;
    }

    public void setFirst_team_name(String first_team_name) {
        this.first_team_name = first_team_name;
    }

    public String getSecond_team_name() {
        return second_team_name;
    }

    public void setSecond_team_name(String second_team_name) {
        this.second_team_name = second_team_name;
    }

    public String getMatch_id ()
    {
        return match_id;
    }

    public void setMatch_id (String match_id)
    {
        this.match_id = match_id;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
