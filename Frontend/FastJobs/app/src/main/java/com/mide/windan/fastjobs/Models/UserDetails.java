package com.mide.windan.fastjobs.Models;

import com.mide.windan.fastjobs.Rest.ResponseUser;

public class UserDetails {

    public static String _id;
    public static String name;
    public static String username;
    public static String email ;
    public static String phoneNumber;
    public static int rating;
    public static int numberOfRatings;

    public static String toStringStatic(){
        return "_id: " + _id +
                "\nname: " + name +
                "\nusername: " + username +
                "\nemail: " + email +
                "\nphoneNumber: " + phoneNumber +
                "\nrating: " + rating +
                "\nnumberOfRatings: " + numberOfRatings;
    }

    public static void fromResponse(ResponseUser ru){
        UserDetails._id = ru._id;
        UserDetails.name = ru.name;
        UserDetails.username = ru.username;
        UserDetails.email = ru.email;
        UserDetails.numberOfRatings = ru.numberOfRatings;
        UserDetails.rating = ru.rating;
        UserDetails.phoneNumber = ru.phoneNumber;
    }
}
