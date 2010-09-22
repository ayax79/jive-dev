package com.syncapse.plugin.script

import org.springframework.web.context.WebApplicationContext

object BindingUtil {
  protected lazy val entries = List[(String, String, WebApplicationContext => AnyRef)](
    (" ctx: Jive Context", "ctx", {jc => jc}),
    (" ap: Authentication Provider", "ap", {jc => jc.getBean("authenticationProvider")}),
    (" acm: Activity Manager", "acm", {jc => jc.getBean("activityManagerImpl")}),
    (" anm: Announcement Manager", "anm", {jc => jc.getBean("announcementManagerImpl")}),
    (" cm: CommunityManager", "cm", {jc => jc.getBean("communityManagerImpl")}),
    (" fm: ForumManager", "fm", {jc => jc.getBean("forumManagerImpl")}),
    (" um: UserManager", "um", {jc => jc.getBean("userManagerImpl")}),
    (" jp: jive properties", "jp", {jc => jc.getBean("jiveProperties")})
    )

  def buildBindingMap(ctx: WebApplicationContext): Map[String, AnyRef] = entries map {case (_, name, f) => (name, f(ctx))} toMap

  def buildHelp = {
    val builder = new StringBuilder
    entries foreach {case (desc, _, _) => builder ++= "<li>" ++= desc ++= "</li>"}
    builder.toString
  }

}