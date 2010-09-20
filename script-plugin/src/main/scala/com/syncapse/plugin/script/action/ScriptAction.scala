package com.syncapse.plugin.script.action

import com.opensymphony.xwork2.{Action, ActionSupport}
import reflect.BeanProperty
import com.syncapse.plugin.script.{ScriptResult, ErrorResult, ScriptType, ScriptRunner}
import java.io.{ByteArrayInputStream, InputStream}
import util.parsing.json.JSONObject

class ScriptAction extends ActionSupport {
  @BeanProperty
  var script: String = null

  @BeanProperty
  var scriptType: String = ScriptType.ECMA_SCRIPT

  @BeanProperty
  var scriptResult: InputStream = null

  override def execute = {
    Action.SUCCESS
  }

  def process: String = {
    val sType = ScriptType.getScriptType(scriptType)
    val json = asJSON(ScriptRunner.execute(sType, script))
    scriptResult = new ByteArrayInputStream(json.toString.getBytes("UTF-8"))
    "json"
  }

  def asJSON(scriptResult: Either[ErrorResult, ScriptResult]) = scriptResult match {
    case Left(ErrorResult(msg)) => new JSONObject(Map("msg" -> msg))
    case Right(ScriptResult(msg)) => new JSONObject(Map("msg" -> msg))
  }

}