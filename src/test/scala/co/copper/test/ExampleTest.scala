package co.copper.test

import org.scalatest._
import org.scalatest.featurespec.AnyFeatureSpec

class ExampleTest extends AnyFeatureSpec with GivenWhenThen with BeforeAndAfterAll {

  info("As a TV set owner")
  info("I want to be able to turn the TV on and off")
  info("So I can watch TV when I want")
  info("And save energy when I'm not watching TV")

  Feature("TV power button") {
    Scenario("User presses power button when TV is off") {

      Given("a TV set that is switched off")

      When("the power button is pressed")

      Then("the TV should switch on")
    }

  }
}
