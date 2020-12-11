package com.mobiquity.utilities;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class APIUtility {
    RequestSpecification requestSpecification;
    Response response;


    public static boolean isUserInclude(String userName){
        Response response = when().get(ConfigurationReader.get("baseURI") + "/users");
        Boolean isIncluded=false;
        List<Map<String,Object>> allRecords=response.body().as(List.class);

        for (Map<String, Object> allRecord : allRecords) {

            if (allRecord.get("username").toString().equalsIgnoreCase(userName)){
                isIncluded=true;
                break;
            }
        }
        return isIncluded;
    }


    public static int getUserId(String userName) {
        int userId = 0;
        Response response = when().get(ConfigurationReader.get("baseURI") + "/users");
        List<Map<String, Object>> allRecords = response.body().as(List.class);
        for (Map<String, Object> allRecord : allRecords) {
            if (allRecord.get("username").toString().equalsIgnoreCase(userName)) {
                userId = Integer.parseInt(allRecord.get("id").toString());
                System.out.println("userId = " + userId);
                break;
            }
        }
        return userId;
    }

    public static List<Integer> getPostIds(int userId) {
        List<Integer> postIds = new ArrayList<>();
        Response response = given().queryParam("userId", userId).when().get(ConfigurationReader.get("baseURI") + "/posts");
        List<Map<String, Object>> allPosts = response.body().as(List.class);
        for (Map<String, Object> allPost : allPosts) {
            postIds.add(Integer.parseInt(allPost.get("id").toString()));
        }
        return postIds;
    }

    public static List<String> getCommentsEmails(List<Integer> postIds) {
        List<String> commentsEmails = new ArrayList<>();
        for (Integer postId : postIds) {
            Response response = given().queryParam("postId", postId).when().get(ConfigurationReader.get("baseURI") + "/comments");
            List<Map<String, Object>> allcomments = response.body().as(List.class);
            System.out.println("Post Id : " + postId.toString());
            for (Map<String, Object> allcomment : allcomments) {
                commentsEmails.add(allcomment.get("email").toString());
                System.out.println("Comment e-mail : " + allcomment.get("email").toString());
            }
        }
    return commentsEmails;
    }

    public static Boolean isValidEmail(String email){
        Pattern pattern;
        Matcher matcher;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,}$";

        pattern = Pattern.compile(emailRegex);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
