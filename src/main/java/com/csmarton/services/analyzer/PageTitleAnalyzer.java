package com.csmarton.services.analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PageTitleAnalyzer
{
	public String getPageTitle(Document doc)
	{
		String title = null;
		if (doc == null) {
			try {
				doc = Jsoup.connect("http://example.com/").get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		title = doc.title();

		return title;
	}
}
