package com.csmarton.services.analyzer;

import com.csmarton.model.Page;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class PageLinkAnalyzer
{
	public void checkLinks(Page page)
	{
		try {
			String urlPrefix = getDomainName(page.getUrl());

			Elements links = page.getDocument().select("a");

			int numOfInternalLinks = 0;
			int numOfExternalLinks = 0;

			if (links != null && links.size() > 0) {
				for (Element linkElement : links) {
					String absHref = linkElement.attr("abs:href");

					if (absHref.startsWith(urlPrefix)) {
						numOfInternalLinks++;
					} else {
						numOfExternalLinks++;
					}
				}
			}

			page.setNumOfInternalLinks(numOfInternalLinks);
			page.setNumOfExternalLinks(numOfExternalLinks);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private String getDomainName(String url) throws URISyntaxException
	{
		URI uri = new URI(url);
		String domain = uri.getHost();

		if (domain == null) {
			return null;
		}

		return domain.startsWith("www.") ? uri.getScheme() + "://" + domain
				: uri.getScheme() + "://" + domain;
	}
}
