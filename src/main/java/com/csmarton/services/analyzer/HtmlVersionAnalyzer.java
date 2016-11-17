package com.csmarton.services.analyzer;

import com.google.common.collect.ImmutableMap;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HtmlVersionAnalyzer {
	private final static String HTML_5_REG_EX_PATTERN = "^(<!DOCTYPE|<!doctype)(\\s)(html|HTML)>$";
	private final static String HTML_4_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(html4/strict.dtd)(.*)>$";
	private final static String HTML_4_TRANSITIONAL_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(html4/loose.dtd)(.*)>$";
	private final static String HTML_4_FRAMESET_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(html4/frameset.dtd)(.*)>$";
	private final static String HTML_1_STRICT_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(xhtml1/DTD/xhtml1-strict.dtd)(.*)>$";
	private final static String HTML_1_TRANSITIONAL_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(xhtml1/DTD/xhtml1-transitional.dtd)(.*)>$";
	private final static String HTML_1_FRAMESET_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(xhtml1/DTD/xhtml1-frameset.dtd)(.*)>$";
	private final static String HTML_1_1_REG_EX_PATTERN = "^(?=<!DOCTYPE|<!doctype)(.*)(xhtml11/DTD/xhtml11.dtd)(.*)>$";

	public enum HTML_VERSIONS {
		HTML5, HTML4_STRICT, HTML_4_TRANSITIONAL, HTML_4_FRAMESET, HTML_1_STRICT, HTML_1_TRANSITIONAL, HTML_1_FRAMESET, HTML_1_1
	}

	private static ImmutableMap<HTML_VERSIONS, Pattern> htmlVersionEnumMap = ImmutableMap
			.<HTML_VERSIONS, Pattern>builder()
			.put(HTML_VERSIONS.HTML5, buildPattern(HTML_5_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML4_STRICT, buildPattern(HTML_4_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_4_TRANSITIONAL, buildPattern(HTML_4_TRANSITIONAL_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_4_FRAMESET, buildPattern(HTML_4_FRAMESET_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_1_STRICT, buildPattern(HTML_1_STRICT_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_1_TRANSITIONAL, buildPattern(HTML_1_TRANSITIONAL_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_1_FRAMESET, buildPattern(HTML_1_FRAMESET_REG_EX_PATTERN))
			.put(HTML_VERSIONS.HTML_1_1, buildPattern(HTML_1_1_REG_EX_PATTERN))
			.build();


	private static Pattern buildPattern(String p) {
		return Pattern.compile(p);
	}

	public HTML_VERSIONS getHtmlVersion(Document document) {
		List<Node> nods = document.childNodes();

		String docTypeLine = null;

		for (Node node : nods) {
			if (node instanceof DocumentType) {
				DocumentType documentType = (DocumentType)node;
				docTypeLine = documentType.toString();
				System.out.println(documentType.toString());

				break;
			}
		}

		for (HTML_VERSIONS htmlVersion : htmlVersionEnumMap.keySet()) {
			Pattern pattern  = htmlVersionEnumMap.get(htmlVersion);

			Matcher matcher = pattern.matcher(docTypeLine);

			if(matcher.matches()){
				return htmlVersion;
			}
		}

		return null;
	}
}
