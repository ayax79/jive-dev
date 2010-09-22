package com.syncapse.plugin.script

import org.specs.mock.Mockito
import org.specs.Specification
import org.specs.runner.JUnit4
import com.jivesoftware.base.UserManager
import com.jivesoftware.base.aaa.AuthenticationProvider
import com.jivesoftware.community._
import org.springframework.web.context.WebApplicationContext

class BindingUtilSpec extends JUnit4(BindingUtilSpec)
object BindingUtilSpec extends Specification with Mockito {

  "BindingUtil" should {
    "build help" in {
      val help = BindingUtil.buildHelp
      help must include("<li>")
      help must include("</li>")
      help must include("um")
    }

    "build bingings map" in {
      val ctx = mock[WebApplicationContext]
      ctx.getBean("userManagerImpl") returns mock[UserManager]
      ctx.getBean("authenticationProvider") returns mock[AuthenticationProvider]
      ctx.getBean("activityManagerImpl") returns mock[ActivityManager]
      ctx.getBean("announcementManagerImpl") returns mock[AnnouncementManager]
      ctx.getBean("communityManagerImpl") returns mock[CommunityManager]
      ctx.getBean("forumManagerImpl") returns mock[ForumManager]

      val map = BindingUtil.buildBindingMap(ctx)

      map must haveKey("ctx")
      map must haveValue(ctx)
      map must haveKey("um")
      map must haveKey("cm")
      map must haveKey("ap")
      map must haveKey("acm")
      map must haveKey("anm")
      map must haveKey("fm")

      there was atLeastOne(ctx).getBean("userManagerImpl")
      there was atLeastOne(ctx).getBean("authenticationProvider")
      there was atLeastOne(ctx).getBean("activityManagerImpl")
      there was atLeastOne(ctx).getBean("announcementManagerImpl")
      there was atLeastOne(ctx).getBean("communityManagerImpl")
      there was atLeastOne(ctx).getBean("forumManagerImpl")
    }

  }

}