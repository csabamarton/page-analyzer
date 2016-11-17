package com.csmarton.services.analyzer;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PageTitleAnalyzer
{
	public String getPageTitle(Document doc)
	{
		String title = doc.title();

		if(StringUtils.isEmpty(title)) {
			title = "No title";
		}

		return title;
	}
}
