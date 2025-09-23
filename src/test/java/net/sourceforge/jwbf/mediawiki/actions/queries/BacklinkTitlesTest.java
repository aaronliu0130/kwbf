package net.sourceforge.jwbf.mediawiki.actions.queries;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

@RunWith(MockitoJUnitRunner.class)
public class BacklinkTitlesTest {

  @Spy private BacklinkTitles testee = new BacklinkTitles(mock(MediaWikiBot.class), "");

  @Test
  public void testParseArticleTitles() {
    // GIVEN / WHEN
    ImmutableList<String> result = testee.parseElements(BaseQueryTest.emptyXml());

    // THEN
    assertTrue(result.isEmpty());
  }
}
