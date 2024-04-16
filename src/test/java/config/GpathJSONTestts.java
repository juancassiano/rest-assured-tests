package config;

import org.junit.Test;
import java.util.Map;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class GpathJSONTestts extends FootballConfig {

  @Test
  public void extractMapOfElementsWithFind(){
    Response response = get("competitions/2021/teams");

    Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find { it.name == 'Manchester United FC' }");

    System.out.println("Map of team data = " + allTeamDataForSingleTeam);
  }
  
}
