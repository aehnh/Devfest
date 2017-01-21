package com.example.anders.devfest;

import android.location.Location;

import java.util.ArrayList;

public class Room {

    private String name;
    private int capacity;
    private String description;
    private Location location;
    private String owner;
    private ArrayList<String> members;

    public String getName(){ return name;}
    public int getCapacity() {return capacity;};
    public int getSize(){return members.size();}
    public String getDescription(){return description;}
    public Location getLocation(){return location;}
    public String getOwner() { return owner; }
    public ArrayList<String> getMembers(){return members;}

    public Room(String name,int capacity, String description, Location location, String creator) {
        this.name=name;
        this.capacity=capacity;
        this.description=description;
        this.location=location;
        this.owner = creator;
        members = new ArrayList<String>();
        members.add(creator);
    }

    public void addMember(String member) {
        this.members.add(member);
    }
    public void removeMember(String member) {
        for(int i = 0; i < this.members.size(); i++) {
            if(this.members.get(i).equals(member)) {
                this.members.remove(i);
                return;
            }
        }
    }
}
