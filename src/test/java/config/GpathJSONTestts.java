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

  @Test
  public void extractSingleValueWithHighestNumber(){
    Response response = get("teams/57");
    String playerName = response.path("squad.max {it.id}.name");
    // String playerName = response.path("squad.min {it.id}.name");

    System.out.println(playerName);
  }

  @Test
  public void extractMultipleValuesAndSumThem(){
    Response response = get("teams/57");
    int sumOfIds = response.path("squad.collect {it.id}.sum()");

    System.out.println(sumOfIds);
  }
  
  @Test
  public void extractMapWithFindAndFindAllWithParameters(){
    String position = "Offence";
    String nationality = "England";

    Response response = get("teams/57");

    Map<String, ?> playerOfCertainPosition = response.path("squad.findAll {it.potision == '%s'}.find {it.nationality == '%s'}", position, nationality);
    System.out.println(playerOfCertainPosition);
  }

  @Test
  public void extractMultiplePlayers(){
    String position = "Offence";
    String nationality = "England";

    Response response = get("teams/57");

    List<Map<String, ?>> AllPlayersOfCertainPosition = response.path("squad.findAll {it.potision == '%s'}.findAll {it.nationality == '%s'}", position, nationality);
    System.out.println(AllPlayersOfCertainPosition);
  }
}
