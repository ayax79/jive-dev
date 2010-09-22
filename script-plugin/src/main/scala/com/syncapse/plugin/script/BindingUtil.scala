package com.syncapse.plugin.script

import com.jivesoftware.community.JiveContext
object BindingUtil {
  protected lazy val entries = List[(String, String, JiveContext => AnyRef)](
    (" ctx: Jive Context", "ctx", {jc => jc}),
    (" ap: Authentication Provider", "ap", {jc => jc.getAuthenticationProvider}),
    (" acm: Activity Manager", "acm", {jc => jc.getActivityManager}),
    (" anm: Announcement Manager", "anm", {jc => jc.getAnnouncementManager}),
    (" cm: CommunityManager", "cm", {jc => jc.getCommunityManager}),
    (" fm: ForumManager", "fm", {jc => jc.getForumManager}),
    (" um: UserManager", "um", {jc => jc.getUserManager}))

  def buildBindingMap(ctx: JiveContext): Map[String, AnyRef] = entries map { case (_, name, f) => (name, f(ctx))} toMap

  def buildHelp = {
    val builder = new StringBuilder
    entries foreach {case (desc, _, _) => builder ++= "<li>" ++= desc ++= "</li>"}
    builder.toString
  }

}