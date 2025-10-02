package net.sourceforge.jwbf.mediawiki.actions.queries;

import org.junit.Ignore;
import org.junit.Test;

import com.github.dreamhead.moco.RequestMatcher;
import com.google.common.collect.ImmutableList;

import net.sourceforge.jwbf.AbstractIntegTest;
import net.sourceforge.jwbf.GAssert;
import net.sourceforge.jwbf.TestHelper;
import net.sourceforge.jwbf.mediawiki.ApiMatcherBuilder;
import net.sourceforge.jwbf.mediawiki.MediaWiki;
import net.sourceforge.jwbf.mediawiki.MocoIntegTest;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WatchListIntegTest extends AbstractIntegTest {

  RequestMatcher watchlist =
      ApiMatcherBuilder.of() //
          .param("action", "query") //
          .param("format", "json") //
          .paramNewContinue(MediaWiki.Version.MW1_24) //
          .param("list", "watchlist") //
          .param("wllimit", "max") //
          .param("wlnamespace", "0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15") //
          .param("wlshow", "bot|anon|minor") //
          .build();

  RequestMatcher loginSuccess =
      ApiMatcherBuilder.of() //
          .param("action", "login") //
          .param("format", "xml") //
          .build();

  @Test
  public void test() {
    // GIVEN
    server.request(loginSuccess).response(TestHelper.anyWikiResponse("login_valid.xml"));
    MocoIntegTest.applySiteinfoXmlToServer(server, MediaWiki.Version.MW1_24, this.getClass());
    server.request(watchlist).response(TestHelper.anyWikiResponse("watchlist.json"));
    MediaWikiBot bot = new MediaWikiBot(host());
    bot.login("Hunsu", "password");

    // WHEN
    WatchList testee =
        WatchList.from(bot) //
            .withProperties(WatchList.WatchListProperties.values()) //
            .owner("Hunsu", "notoken") //
            .build();
    ImmutableList<WatchResponse> resultList = testee.getCopyOf(3);

    // THEN
    ImmutableList<WatchResponse> expected = ImmutableList.of(
        new WatchResponse(0, "Revenge (TV series)", null, null, null, null, null, 31808292,
            641213576, 641162910, 0, 0, false, "edit", false, false, false, false),
        new WatchResponse(4, "Wikipedia:Reference desk/Language", null, null, null, null, null,
            2515121, 641213194, 641195380, 0, 0, false, "edit", false, false, false, false),
        new WatchResponse(4, "Wikipedia:Reference desk/Humanities", null, null, null, null, null,
            2535875, 641211705, 641208678, 0, 0, false, "edit", false, false, false, false)
    );
    GAssert.assertEquals(expected, resultList);
  }
}
