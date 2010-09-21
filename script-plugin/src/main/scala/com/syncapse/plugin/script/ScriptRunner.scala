package com.syncapse.plugin.script

import com.syncapse.jive.Loggable
import java.io.{Writer, StringWriter}
import java.lang.String
import javax.script._

trait ScriptType {
  def name: String;
}

object ScriptType {
  case object ECMA_SCRIPT extends ScriptType { def name = "ECMAScript" }

  def getScriptType(name: String): ScriptType = name match {
    case "ECMAScript" => ECMA_SCRIPT.asInstanceOf[ScriptType]
  }
}

case class ScriptResult(msg: String)
case class ErrorResult(msg: String)

object ScriptRunner extends Loggable {
  protected lazy val engineManager = new ScriptEngineManager();

  def execute(scriptType: ScriptType, script: String): Either[ErrorResult, ScriptResult] = {
    val engine = engineManager.getEngineByName(scriptType.name)
    if (engine != null) {                                 
      try {
        val writer = new StringWriter
        val ctx = buildContext(writer)
        val result = engine.eval(script, ctx).toString
        Right(ScriptResult(result))
      }
      catch {
        case e: ScriptException =>
          logger.warn(e.getMessage, e)
          Left(ErrorResult(e.getMessage))
      }
    } else {
      logger.warn("Could not determine engine, not executing script")
      Left(ErrorResult("Could not determine engine, not executing script"))
    }
  }

  protected def buildContext(writer: Writer): ScriptContext = {
    val context = new SimpleScriptContext
    context.setWriter(writer)
    context.setErrorWriter(writer)
    context
  }

}