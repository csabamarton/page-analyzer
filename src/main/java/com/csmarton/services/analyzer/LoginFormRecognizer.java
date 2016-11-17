package com.csmarton.services.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class LoginFormRecognizer
{
	public boolean containsLoginForm(Document document)
	{
		Elements inputs = document.select("input");

		if (inputs != null && inputs.size() > 0) {
			for (Element input : inputs) {
				String inputType = input.attr("type");

				if ("password".equals(inputType)) {
					return true;
				}
			}
		}

		return false;
	}
}
