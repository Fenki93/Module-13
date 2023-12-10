package org.ddiachenko.user;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
public class UserTest {
    public  static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    public static String jsonString;

    public static HttpClient client;

    public static void main(String[] args) throws IOException, InterruptedException {

        jsonString = getAllUsers();
        System.out.println("jsonString = " + jsonString);

        UserDto.Address.Geo geo = new UserDto.Address.Geo("111", "222");
        UserDto.Address address = new UserDto.Address("Street", "2", "Cityy", "zip111", geo);
        UserDto.Company company = new UserDto.Company("myCompany", "phraseUUU", "bss");
        UserDto newUserDto = new UserDto("razDwas", "emaN", "raz@ua.ua", address, "+44", "ua123.ua", company);

        newUserDto.setUsername("goit_updated");


        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        createUser(newUserDto);
        updateUser(newUserDto, 2);
        deleteUser(10);
        System.out.println("User with id 6 = " + getUserById(newUserDto, 6));
        System.out.println("username = " + getUserByUsername(newUserDto, "Samantha"));
    }

    public static void updateUser(UserDto userDto, int id) throws IOException, InterruptedException {
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(userDto.getInfo()))
                .build();

        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + putResponse.statusCode());
        System.out.println("response = " + putResponse);
        System.out.println("response.body() = " + putResponse.body());

    }

    public static void createUser(UserDto userDto) throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userDto.getInfo()))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + postResponse.statusCode());
        System.out.println("response = " + postResponse);
        System.out.println("response.body() = " + postResponse.body());

    }

    public static void deleteUser(int id) throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + postResponse.statusCode());
        System.out.println("response = " + postResponse);
    }

    public static String getUserById(UserDto userDto, int id) {
        Type type = TypeToken.getParameterized(List.class, UserDto.class)
                .getType();
        List<UserDto> list = new Gson().fromJson(jsonString, type);
        return list.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(userDto)
                .getInfo();
    }

    public static String getUserByUsername(UserDto userDto, String username) {
        Type type = TypeToken.getParameterized(List.class, UserDto.class)
                .getType();
        List<UserDto> list = new Gson().fromJson(jsonString, type);
        return list.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(userDto)
                .getInfo();
    }

    public static String getAllUsers() throws IOException {
        return Jsoup.connect(BASE_URL)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}
