package config;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class FootballTests extends FootballConfig{

    @Test
    public void getDetailsOfOneArea(){
        given()
                .queryParam("areas", 2076)
                .when()
                    .get("/areas");
    }

    @Test
    public void getDetailsOfMultipleAreas(){
        String areaIds = "2076,2077,2080";

        given()
                .urlEncodingEnabled(false)
                .queryParam("areas", areaIds)
                .when()
                    .get("/areas");
    }

    @Test
    public void getDateFounded(){
        given()
                .when()
                    .get("teams/57")
                .then()
                    .body("founded", equalTo(1886));
    }

    @Test
    public void getFirstTeamName(){
        given()
                .when()
                    .get("/competitions/2021/teams")
                .then()
                .body("teams.name[0]", equalTo("Arsenal FC"));
    }

    @Test
    public void getAllTeamData_DoCheckFirst(){
        Response response =
                given()
                        .when()
                            .get("teams/57")
                        .then()
                            .contentType(ContentType.JSON)
                        .extract().response();
        String jsonResponseAsString = response.asString();
        System.out.println(jsonResponseAsString);
    }

    @Test
    public void extractHeaders(){
        Response response =
                get("teams/57")
                        .then()
                            .extract().response();
        String contenTypeHeader = response.getContentType();
        System.out.println(contenTypeHeader);

        String apiVersionHeader = response.getHeader("X-API-Version");
        System.out.println(apiVersionHeader);
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName = get("competitions/2021/teams").jsonPath().getString("teams.name[0]");

        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamNames(){
        Response response =
                get("competitions/2021/teams")
                        .then()
                            .extract().response();

        List<String> teamNames = response.path("teams.name");

        for(String teamName: teamNames){
            System.out.println(teamName);
        }
    }
    
}
