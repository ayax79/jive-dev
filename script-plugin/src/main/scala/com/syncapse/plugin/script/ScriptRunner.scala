package com.syncapse.plugin.script

import com.syncapse.jive.Loggable
import javax.script.{SimpleScriptContext, ScriptEngineManager}
import java.io.{Writer, StringWriter}


trait ScriptType {
  abstract def name: String;
}

object ScriptType {
  case class ECMAScriptType extends ScriptType {def name = "ECMAScript"}

  def getScriptType(name: String): ScriptType = name match {
    case ECMAScriptType.name => ECMAScriptType
  }


}



object ScriptRunner extends Loggable {
  protected lazy val engineManager = new ScriptEngineManager();

  def execute(scriptType: ScriptType, script: String): String = {
    val engine = engineManager.getEngineByName(scriptType)
    if (engine != null) {
      val writer = new StringWriter
      val ctx = buildContext(ctx)
      engine.eval(script, ctx)
      writer.toString
    } else logger.warn("Could not determine engine, not executing script")
  }


  protected def buildContext(writer: Writer) = {
    val context = new SimpleScriptContext
    context.setWriter(writer)
    context
  }

}