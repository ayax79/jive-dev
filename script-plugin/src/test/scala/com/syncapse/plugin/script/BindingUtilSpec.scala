package com.syncapse.plugin.script

import org.specs.mock.Mockito
import org.specs.Specification
import org.specs.runner.JUnit4
import com.jivesoftware.base.UserManager
import com.jivesoftware.base.aaa.AuthenticationProvider
import com.jivesoftware.community._

class BindingUtilSpec extends JUnit4(BindingUtilSpec)
object BindingUtilSpec extends Specification with Mockito {

  "BindingUtil" should {
    "build help" in {
      val help = BindingUtil.buildHelp
      help must include("<li>")
      help must include("</li>")
      help must include("um")
    }

    "build binginds map" in {
      val ctx = mock[JiveContext]
      ctx.getUserManager returns mock[UserManager]
      ctx.getAuthenticationProvider returns mock[AuthenticationProvider]
      ctx.getActivityManager returns mock[ActivityManager]
      ctx.getAnnouncementManager returns mock[AnnouncementManager]
      ctx.getCommunityManager returns mock[CommunityManager]
      ctx.getForumManager returns mock[ForumManager]

      val map = BindingUtil.buildBindingMap(ctx)

      map must haveKey("ctx")
      map must haveValue(ctx)
      map must haveKey("um")
      map must haveKey("cm")
      map must haveKey("ap")
      map must haveKey("acm")
      map must haveKey("anm")
      map must haveKey("fm")

      there was atLeastOne(ctx).getUserManager
      there was atLeastOne(ctx).getAuthenticationProvider
      there was atLeastOne(ctx).getActivityManager
      there was atLeastOne(ctx).getAnnouncementManager
      there was atLeastOne(ctx).getCommunityManager
      there was atLeastOne(ctx).getForumManager
    }

  }

}