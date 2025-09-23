/*
 * Copyright 2007 Thomas Stock.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors:
 *
 */
package net.sourceforge.jwbf.mediawiki.actions.queries;

import java.util.Iterator;

import jakarta.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.internal.Checked;
import net.sourceforge.jwbf.core.internal.NonnullFunction;
import net.sourceforge.jwbf.mapper.XmlElement;
import net.sourceforge.jwbf.mediawiki.MediaWiki;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * A specialization of {@link CategoryMembers} with contains {@link String}s.
 *
 * @author Thomas Stock
 */
public class CategoryMembersSimple extends BaseQuery<String> {

  private static final Logger log = LoggerFactory.getLogger(CategoryMembersSimple.class);

  private final CategoryMembers cm;

  @VisibleForTesting
  CategoryMembersSimple(MediaWikiBot bot, CategoryMembers cm) {
    super(bot);
    this.cm = Checked.nonNull(cm, "categoryMembers");
  }

  /**
   * @param categoryName like "Buildings" or "Chemical elements" without prefix "Category:" in
   *     {@link MediaWiki#NS_MAIN}
   */
  public CategoryMembersSimple(MediaWikiBot bot, String categoryName) {
    this(bot, categoryName, MediaWiki.NS_MAIN);
  }

  /**
   * @param categoryName like "Buildings" or "Chemical elements" without prefix "Category:"
   * @param namespaces for search
   */
  public CategoryMembersSimple(MediaWikiBot bot, String categoryName, int... namespaces) {
    this(bot, new CategoryMembersFull(bot, categoryName, namespaces));
  }

  private CategoryMembersSimple(
      MediaWikiBot bot, String categoryName, ImmutableList<Integer> namespace) {
    this(bot, new CategoryMembersFull(bot, categoryName, namespace));
  }

  @Override
  protected HttpAction prepareNextRequest() {
    return cm.prepareNextRequest();
  }

  @Override
  protected ImmutableList<String> parseElements(String s) {
    return cm.parseArticles(s, toTitleFunction());
  }

  @Override
  protected Optional<String> parseHasMore(String s) {
    return cm.parseHasMore(s);
  }

  @Override
  protected Iterator<String> copy() {
    return new CategoryMembersSimple(bot(), cm.categoryName, cm.namespace);
  }

  @Override
  public boolean hasNext() {
    return cm.hasNext();
  }

  @Override
  public String next() {
    return cm.next().getTitle();
  }

  static NonnullFunction<XmlElement, String> toTitleFunction() {
    return new NonnullFunction<XmlElement, String>() {
      @Nonnull
      @Override
      public String applyNonnull(@Nonnull XmlElement input) {
        return input.getAttributeValueNonNull("title");
      }
    };
  }
}
