package com.nokchax.escape.leetcode.crawl.file;

import com.nokchax.escape.leetcode.crawl.LeetcodeJsonCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import org.springframework.stereotype.Component;

@Component
public class LeetcodeJsonFileCrawler implements LeetcodeJsonCrawler<String> {

    @Override
    public void crawlProblems(String json) {

    }

    @Override
    public CrawledUserInfo crawlUserInfo(String paramType) {
        return null;
    }
}