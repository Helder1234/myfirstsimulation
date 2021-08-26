package simulations

import io.gatling.core.scenario.Simulation

// imports
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class createpet extends Simulation {

  // protocol
  val httpProtocol = http.baseUrl("https://petstore.swagger.io")
    .acceptHeader("application/json").header("Content-type","application/json" )

  // define request
  def createpet() = {
    exec(http("create a user")
      .post("/v2/pet")
      .body(StringBody(session => s"""{"id":90909090123,"category":{"id":10,"name":"Fabu"},"name":"Cachorro","photoUrls":["string"],"tags":[{"id":2021,"name":"Novo"}],"status":"available"}""")).asJson)
  }

  // setup scenario
  val scn = scenario("Trying to create new Dog")
    .exec(createpet())

  // inject users
  setUp(scn.inject(atOnceUsers(10))
    .protocols(httpProtocol))
}
