package com.example.anders.devfest;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {

    private String name;
    private int capacity;
    private String description;
    private String owner;
    private String ownerName;
    private ArrayList<String> members;
    private ArrayList<String> memberNames;
    private double latitude;
    private double longitude;
    private String key;

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public int getSize() { return members.size(); }
    public String getDescription() { return description; }
    public String getOwner() { return owner; }
    public String getOwnerName() { return ownerName; }
    public ArrayList<String> getMembers() { return members; }
    public ArrayList<String> getMemberNames() { return memberNames; }
    public double getLatitude(){ return latitude; }
    public double getLongitude(){ return longitude; }
    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public Room() {

    }

    public Room(String name,int capacity, String description, double la, double lo, String creator, String creatorName) {
        this.name=name;
        this.capacity=capacity;
        this.description=description;
//        this.location=location;
        this.owner = creator;
        this.ownerName = creatorName;
        this.latitude=la;
        this.longitude=lo;
        members = new ArrayList<String>();
        members.add(creator);
        memberNames = new ArrayList<String>();
        memberNames.add(creatorName);
    }

    public void addMember(String user, String userName) {
        this.members.add(user);
        this.memberNames.add(userName);
    }
    public void removeMember(String member, String memberName) {
        for(int i = 0; i < this.members.size(); i++) {
            if(this.members.get(i).equals(member)) {
                this.members.remove(i);
                this.memberNames.remove(i);
                return;
            }
        }
    }
}
