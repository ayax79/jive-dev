package com.syncapse.plugin.script.action

import com.opensymphony.xwork2.Action
import reflect.BeanProperty
import java.io.{ByteArrayInputStream, InputStream}
import com.twitter.json.Json
import com.jivesoftware.community.action.util.Decorate
import com.jivesoftware.community.action.JiveActionSupport
import com.syncapse.plugin.script._

@Decorate(false)
class ScriptAction extends JiveActionSupport {
  @BeanProperty
  var script: String = null

  @BeanProperty
  var scriptType: String = ScriptType.ECMA_SCRIPT.name

  @BeanProperty
  var scriptResult: InputStream = null

  override def execute = {
    Action.SUCCESS
  }

  def process: String = {
    val sType = ScriptType.getScriptType(scriptType)
    val json = asJSON(ScriptRunner.execute(sType, script, BindingUtil.buildBindingMap(getJiveContext)))
    scriptResult = new ByteArrayInputStream(json.toString.getBytes("UTF-8"))
    "json"
  }

  def getHelp = BindingUtil.buildHelp

  protected def asJSON(scriptResult: Either[ErrorResult, ScriptResult]) = scriptResult match {
    case Left(ErrorResult(msg)) => Json.build(Map("msg" -> msg))
    case Right(ScriptResult(msg)) => Json.build(Map("msg" -> msg))
  }

}