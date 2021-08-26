package simulations

import io.gatling.core.Predef.{Simulation, configuration}

// imports
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class createuser extends Simulation {

  // protocol
  val httpProtocol = http.baseUrl("http://reqres.in")
    .acceptHeader("application/json").header("Content-type","application/json" )

  // define request
  def createUser() = {
    exec(http("create a user")
      .post("/api/users")
      .body(StringBody(
        """"
          |{"name":"Fabio","Job":"QA"}
        """.stripMargin
      )).asJson)
  }

  // setup scenario
  val scn = scenario("Trying to create new users")
    .exec(createUser())

  // inject users
  setUp(scn.inject(atOnceUsers(1))
    .protocols(httpProtocol))
}
