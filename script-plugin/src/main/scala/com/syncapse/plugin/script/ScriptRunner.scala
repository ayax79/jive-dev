package com.syncapse.plugin.script

import com.syncapse.jive.Loggable
import java.io.{Writer, StringWriter}
import javax.script.{ScriptContext, SimpleScriptContext, ScriptEngineManager}
import java.lang.String


trait ScriptType {
  def name: String;
}

object ScriptType {
  val ECMA_SCRIPT = "ECMAScript"

  case class ECMAScriptType extends ScriptType {def name = ECMA_SCRIPT}

  def getScriptType(name: String): ScriptType = name match {
    case ECMA_SCRIPT => ECMAScriptType()
  }
}

case class ScriptResult(content: String)
case class ErrorResult(msg: String)

object ScriptRunner extends Loggable {
  protected lazy val engineManager = new ScriptEngineManager();

  def execute(scriptType: ScriptType, script: String): Either[ErrorResult, ScriptResult] = {
    val engine = engineManager.getEngineByName(scriptType.name)
    if (engine != null) {
      val writer = new StringWriter
      val ctx = buildContext(writer)
      engine.eval(script, ctx)
      Right(ScriptResult(writer.toString))

    } else {
      logger.warn("Could not determine engine, not executing script")
      Left(ErrorResult("Could not determine engine, not executing script"))
    }
  }

  protected def buildContext(writer: Writer): ScriptContext = {
    val context = new SimpleScriptContext
    context.setWriter(writer)
    context
  }

}