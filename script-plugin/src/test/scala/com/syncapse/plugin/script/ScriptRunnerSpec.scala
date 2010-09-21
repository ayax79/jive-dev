package com.syncapse.plugin.script

import org.specs.Specification
import org.specs.mock.Mockito
import org.specs.runner.JUnit4

class ScriptRunnerTest extends JUnit4(ScriptRunnerSpec)
object ScriptRunnerSpec extends Specification with Mockito {
  "ScriptRunner" should {
    "be 2 " in {
      ScriptRunner.execute(ScriptType.ECMA_SCRIPT, "1 + 1;") match {
        case Left(error) => fail("Should not have an error")
        case Right(result) => result.msg must beEqual("2.0");
      }
    }
  }

}