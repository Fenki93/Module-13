package org.ddiachenko.todo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoTest {

    public  static final String BASE_URL ="https://jsonplaceholder.typicode.com/users/";

    public static void main(String[] args) throws IOException {
        List<ToDoUser> result = filter();

        for (ToDoUser user : result) {
            System.out.println(user.getInfo());
        }
    }

    private static List<ToDoUser> filter() throws IOException {
        Type type = new TypeToken<List<ToDoUser>>() {}.getType();
        List<ToDoUser> usersList = new Gson().fromJson(connect(7), type);

        return usersList.stream()
                .filter(user -> !user.isCompleted())
                .collect(Collectors.toList());
    }

    public static String connect(int id) throws IOException {
        return Jsoup.connect(BASE_URL+ id + "/todos")
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}