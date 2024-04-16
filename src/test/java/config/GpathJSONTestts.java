package config;

import org.junit.Test;
import java.util.Map;
import java.util.List;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class GpathJSONTestts extends FootballConfig {

  @Test
  public void extractMapOfElementsWithFind(){
    Response response = get("competitions/2021/teams");

    Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find { it.name == 'Manchester United FC' }");

    System.out.println("Map of team data = " + allTeamDataForSingleTeam);
  }

  @Test
  public void extractSingleValueWithFind(){
    Response response = get("teams/57");
    String certainPlayer = response.path("squad.find { it.id == 188476 }.name");

    System.out.println(certainPlayer);
  }

  @Test
  public void extractListOfValuesWithFindAll(){
    Response response = get("teams/57");
    List<String> playerNames = response.path("squad.findAll { it.id >= 18400}.name");

    System.out.println(playerNames);
  }
  
}
