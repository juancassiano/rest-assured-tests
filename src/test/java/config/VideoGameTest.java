package config;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.VideoGame;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class VideoGameTest extends VideoGameConfig{
    String gameBodyJson =
            """
                {
                "category": "Platform",
                "name": "Mario",
                "rating": "Universal",
                "releaseDate": "2012-05-04",
                "reviewScore": 85
                }
            """;
    @Test
    public void getAllGames(){
        given()
        .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNwGameByJSON(){

        given()
                .body(gameBodyJson)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNwGameByXNL(){
        String gameBodyXml =
                """
                    <VideoGameRequest>
                        <category>Platform</category>
                        <name>Mario</name>
                        <rating>Mature</rating>
                        <releaseDate>2012-05-04</releaseDate>
                        <reviewScore>85</reviewScore>
                    </VideoGameRequest>
                """;

        given()
                .body(gameBodyXml)
                .contentType("application/xml")
                .accept("application/xml")
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void updateGameByJSON(){

        given()
                .body(gameBodyJson)
        .when()
                .put("videogame/3")
        .then();
    }
    @Test
    public void deleteGame(){

        given()
                .accept("text/plain")
        .when()
                .delete("videogame/3")
        .then();
    }

    @Test
    public void getSingleGame(){

        given()
                .pathParam("videoGameId", 5)
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then();
    }

    @Test
    public void testVideoGameSerializationByJSON(){
        VideoGame videoGame = new VideoGame("Shooter","MyAwesomeGame","Mature","2024-12-05",98);

        given()
                .body(videoGame)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void testVideoameSchemaXML(){
        given()
                .pathParam("videoGameId", 3)
                .accept("application/xml")
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJSON(){
        given()
                .pathParam("videoGameId",5)
                .accept("application/json")
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void convertJsonToPojo(){
        Response response =
                given()
                        .pathParam("videoGameId",5)
                 .when()
                        .get(VideoGameEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

}
