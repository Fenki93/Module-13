package org.ddiachenko.post;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CommentTest {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static void main(String[] args) throws IOException {
        int userId = 9;
        List<PostDto> postDtoList = fetchData(userId, "users/", "/posts", PostDto.class);

        List<Integer> postIds = postDtoList.stream()
                .map(PostDto::getId)
                .peek(System.out::println)
                .toList();

        for (int postId : postIds) {
            List<CommentDto> commentDtoList = fetchData(postId, "posts/", "/comments", CommentDto.class);

            writeToFile(commentDtoList, userId, postId);
        }
    }

    private static <T> List<T> fetchData(int id, String prefix, String suffix, Class<T> tClass) throws IOException {
        Type type = TypeToken.getParameterized(List.class, tClass).getType();
        return new Gson().fromJson(connect(id, prefix, suffix), type);
    }

    private static void writeToFile(List<CommentDto> commentDtoList, int userId, int postId) {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";

        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(commentDtoList));
        } catch (IOException e) {
            e.getCause();
        }
    }

    public static String connect(int userId, String folder, String path) throws IOException {
        return Jsoup.connect(BASE_URL+ folder + userId + path)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}
