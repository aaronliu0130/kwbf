package net.sourceforge.jwbf.mediawiki.actions.queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

@RunWith(MockitoJUnitRunner.class)
public class ImageInfoTest {

  @Spy private ImageInfo testee = new ImageInfo(Mockito.mock(MediaWikiBot.class), "A");

  @Test
  public void testParseArticleTitles() {
    // GIVEN / WHEN
    testee.processAllReturningText(BaseQueryTest.emptyXml());

    // THEN
    // -- no exception
  }
}
