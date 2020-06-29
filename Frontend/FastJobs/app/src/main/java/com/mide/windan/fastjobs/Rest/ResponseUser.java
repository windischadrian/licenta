package com.mide.windan.fastjobs.Rest;

public class ResponseUser {
    public String _id;
    public String name;
    public String email ;
    public String phoneNumber;
    public String username;
    public int rating;
    public int numberOfRatings;

    public String toString(){
        return
                "\nname: " + name +
                "\nusername: " + username +
                "\nemail: " + email +
                "\nphoneNumber: " + phoneNumber +
                "\nrating: " + rating +
                "\nnumberOfRatings: " + numberOfRatings;
    }

}
