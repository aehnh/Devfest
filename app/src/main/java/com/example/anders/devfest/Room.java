package com.example.anders.devfest;

import android.location.Location;

/**
 * Created by q on 2017-01-21.
 */

public class Room {

    private String Name;
    private int Size;
    private String Description;
    private Location Loca;
    private String[] Members;

    public String GetName(){ return Name;}
    public int GetSize(){return Size;}
    public String GetDescription(){return Description;}
    public Location GetLocation(){return Loca;}
    public String[] Getmembers(){return Members;}

    public Room(){

    }

    public Room(String name,int size,String description,Location loca,String[] members){

        this.Name=name;
        this.Size=size;
        this.Description=description;
        this.Loca=loca;
        this.Members=members;
    }
}
