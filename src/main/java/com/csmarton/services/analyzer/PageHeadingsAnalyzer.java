package com.csmarton.services.analyzer;

import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.IntStream;

@Component
public class PageHeadingsAnalyzer
{
	public Map<Integer, Integer> numOfHeadings(Document doc)
	{
		Map<Integer, Integer> numOfHeadings = Maps.newTreeMap();
		Elements hTags = doc.select("h1, h2, h3, h4, h5, h6");

		boolean hasHeadings = false;

		if (hTags != null) {
			hasHeadings = true;
		}

		boolean finalHasHeadings = hasHeadings;

		IntStream.range(1, 7).forEach(counter -> {
			if (finalHasHeadings) {
				Elements headingtags = hTags.select("h" + counter);
				if (headingtags == null) {
					numOfHeadings.put(counter, 0);
				} else {
					numOfHeadings.put(counter, headingtags.size());
				}
			} else {
				numOfHeadings.put(counter, 0);
			}
		});

		return numOfHeadings;
	}
}
