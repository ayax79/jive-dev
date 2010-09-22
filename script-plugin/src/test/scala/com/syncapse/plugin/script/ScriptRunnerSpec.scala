package com.syncapse.plugin.script

import org.specs.Specification
import org.specs.mock.Mockito
import org.specs.runner.JUnit4

class ScriptRunnerTest extends JUnit4(ScriptRunnerSpec)
object ScriptRunnerSpec extends Specification with Mockito {
  "ScriptRunner" should {
    "be 2 " in {
      ScriptRunner.execute(ScriptType.JAVA_SCRIPT, "1 + 1;", Map.empty) match {
        case Left(error) => fail("Should not have an error")
        case Right(result) => result.result match {
          case Some(x) => x must beEqual("2.0");
          case None => fail("should have output")
        }
      }
    }

    "handle print" in {
      ScriptRunner.execute(ScriptType.JAVA_SCRIPT, "print(\"hi\");", Map.empty) match {
        case Left(error) => fail("should not of failed")
        case Right(result) => result must notBeNull
      }
    }
  }

}