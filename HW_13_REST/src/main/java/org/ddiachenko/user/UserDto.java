package org.ddiachenko.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.Getter;

@Data
public class UserDto {
    private int id;
    private String username;

    private final String name;
    private final String email;
    private final Address address;
    private final String phone;
    private final String website;
    private final Company company;

    public UserDto(String username, String name, String email, Address address, String phone, String website, Company company) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public String getInfo() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Data
    public static class Address {

        private final String street;
        private final String suite;
        private final String city;
        private final String zip;
        private final Geo geo;

        public Address(String street, String suite, String city, String zip, Geo geo) {
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zip = zip;
            this.geo = geo;
        }

        @Data
        public static class Geo {
            private final String lat;
            private final String lng;

            public Geo(String lat, String lng) {
                this.lat = lat;
                this.lng = lng;
            }
        }
    }

    @Data
    public static class Company {
        private final String name;
        private final String catchPhrase;
        private final String bs;

        public Company(String name, String catchPhrase, String bs) {
            this.name = name;
            this.catchPhrase = catchPhrase;
            this.bs = bs;
        }

    }
}
