package com.syncapse.plugin.script

import com.syncapse.jive.Loggable
import java.lang.String
import javax.script._
import java.io.{PrintWriter, StringWriter, Writer}

trait ScriptType {
  def name: String;
}

object ScriptType {
  case object JAVA_SCRIPT extends ScriptType {def name = "JavaScript"}

  def getScriptType(name: String): ScriptType = name match {
    case "JavaScript" => JAVA_SCRIPT.asInstanceOf[ScriptType]
  }
}

case class ScriptResult(result: Option[String], output: String, error: String)
case class ErrorResult(error: String)

object ScriptRunner extends Loggable {
  protected lazy val engineManager = new ScriptEngineManager();

  def execute(scriptType: ScriptType, script: String, bindings: Map[String, AnyRef]): Either[ErrorResult, ScriptResult] = {
    val engine = engineManager.getEngineByName(scriptType.name)
    if (engine != null) {
      try {
        val stdOut = new StringWriter
        val stdErr = new StringWriter

        val c = engine.getContext
        c.setWriter(new PrintWriter(stdOut))
        c.setErrorWriter(new PrintWriter(stdErr))

        bindings foreach {case (key, value) => c.setAttribute(key, value, ScriptContext.ENGINE_SCOPE)}
        val result = engine.eval(script)

        if (result != null) {
          Right(ScriptResult(Some(result.toString), stdOut.toString, stdErr.toString))
        }
        else {
          Right(ScriptResult(None, stdOut.toString, stdErr.toString))
        }
      }
      catch {
        case e: Throwable =>
          logger.warn(e.getMessage, e)
          Left(ErrorResult(e.getMessage))
      }
    } else {
      logger.warn("Could not determine engine, not executing script")
      Left(ErrorResult("Could not determine engine, not executing script"))
    }
  }

}