package simulations

import io.gatling.core.scenario.Simulation

// imports
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class pets extends Simulation {

  // protocol
  val httpProtocol = http.baseUrl("https://petstore.swagger.io")
    .acceptHeader("application/json").header("Content-type","application/json" )

  // define request
  def createPets() = {
    exec(http("create a pet")
      .post("/v2/pet")
      .body(RawFileBody("./src/test/resources/bodies/pet1.json")).asJson
      .check(status is(200)))
  }

  def findPets() = {
    exec(http("find pet")
      .get("/v2/pet/100319852000000")
      .check(status is(200))
    )
  }

  // setup scenario
  val scn = scenario("Trying to create new Dog")
    .exec(createPets())

    .pause(3)

    .exec(findPets())

  // inject users
  setUp(scn.inject(atOnceUsers(2))
    .protocols(httpProtocol));
}
