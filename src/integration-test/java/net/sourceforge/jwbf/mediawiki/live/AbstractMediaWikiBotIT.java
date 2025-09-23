package net.sourceforge.jwbf.mediawiki.live;

import static net.sourceforge.jwbf.TestHelper.assumeReachable;

import jakarta.inject.Provider;

import com.google.common.annotations.VisibleForTesting;

import net.sourceforge.jwbf.mediawiki.MediaWiki;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public abstract class AbstractMediaWikiBotIT implements Provider<MediaWikiBot> {

  private static final String WIKIPEDIA_DE = "https://de.wikipedia.org/w/index.php";
  @VisibleForTesting protected MediaWikiBot bot = null;
  private MediaWiki.Version version;

  public MediaWikiBot bot() {
    return bot;
  }

  public void bot(MediaWikiBot bot) {
    this.bot = bot;
  }

  public MediaWiki.Version version() {
    return version;
  }

  public void version(MediaWiki.Version version) {
    this.version = version;
  }

  @Override
  public MediaWikiBot get() {
    return bot;
  }

  /** @deprecated do not use this */
  @Deprecated
  public static String getWikipediaDeUrl() {
    assumeReachable(WIKIPEDIA_DE);
    return WIKIPEDIA_DE;
  }
}
