package com.mobiquity.step_definitions;

import com.mobiquity.utilities.APIUtility;
import com.mobiquity.utilities.ConfigurationReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class API_Steps {
    RequestSpecification requestSpecification;
    Response response;
    String baseURI = ConfigurationReader.get("baseURI");


    @When("User sends GET request to endpoint {string}")
    public void user_sends_GET_request_to_endpoint(String path) {
        response = when().get(baseURI+path);
        //System.out.println(response.body().prettyPrint());
    }

    @Then("Verifies that response status code is {int}")
    public void verifies_that_response_status_code_is(int status) {
        Assert.assertEquals(status,response.statusCode());
    }

    @Then("Verifies that response content type is {string}")
    public void verifies_that_response_content_type_is(String conType) {
        Assert.assertEquals(conType,response.contentType());
    }

    @Then("Verifies the a record with username {string}")
    public void verifies_the_a_record_with_username(String userName) {
        Assert.assertTrue(APIUtility.isUserInclude(userName));
    }

    @Then("Verifies there is no record with username {string}")
    public void verifies_there_is_no_record_with_username(String invaliduser) {
        Assert.assertTrue(!APIUtility.isUserInclude(invaliduser));
    }


    @When("User is able to GET endpoint {string} written by username {string}")
    public void user_is_able_to_GET_endpoint_written_by_username(String postsURI, String userName) {
        if (APIUtility.getUserId(userName)==0){
            System.out.println("There is no user with username "+ userName);
        }
        response=given().queryParam("userId",APIUtility.getUserId(userName)).when().get(baseURI+postsURI);
    }


    @Then("Verifies the {int} posts written by the user")
    public void verifies_the_posts_written_by_the_user(int postNum) {
        List<Map<String,Object>> allPosts=response.body().as(List.class);
        if (allPosts.size()!=0){
            System.out.println("The number of posts are written by user is "+postNum);
        }
        Assert.assertEquals(postNum,allPosts.size());


    }

    @When("User is able to GET all comments for each posts written by username {string}")
    public void user_is_able_to_GET_all_comments_for_each_posts_written_by_username(String userName) {
        int userId=APIUtility.getUserId(userName);
        List<Integer> postIds = APIUtility.getPostIds(userId);

        List<String>  commentsEmails = APIUtility.getCommentsEmails(postIds);

        for (Integer postId : postIds) {
            response = given().queryParam("postId", postId).when().get(ConfigurationReader.get("baseURI") + "/comments");
        }

    }

    @Then("Verifies comments e-mails of {string} posts are proper format")
    public void verifies_comments_e_mails_of_posts_are_proper_format(String userName) {
        int userId=APIUtility.getUserId(userName);
        List<Integer> postIds = APIUtility.getPostIds(userId);

        List<String> commentsEmails = new ArrayList<>();
        System.out.println("All posts Ids and their comments' emails list...");
        for (Integer postId : postIds) {
            Response response = given().queryParam("postId", postId).when().get(ConfigurationReader.get("baseURI") + "/comments");
            List<Map<String, Object>> allcomments = response.body().as(List.class);
            System.out.println("Post Id : " + postId.toString());
            for (Map<String, Object> allcomment : allcomments) {
                commentsEmails.add(allcomment.get("email").toString());
                if (APIUtility.isValidEmail(allcomment.get("email").toString())){
                    System.out.println("Comment e-mail : " + "\t" + allcomment.get("email").toString() + "\t \t Valid e-mail format");
                }else {
                    System.out.println("Comment e-mail : " + "\t" + allcomment.get("email").toString()+  "\t \t  Invalid e-mail format");
                }
            }
        }
    }









}
