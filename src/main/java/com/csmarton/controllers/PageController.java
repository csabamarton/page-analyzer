package com.csmarton.controllers;

import com.csmarton.model.Page;
import com.csmarton.services.PageAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController
{
	@Autowired
	private PageAnalyzerService pageAnalyzerService;

	@RequestMapping("/page")
	public String page(Model model) {
		model.addAttribute(new Page());

		return "index";
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public String processPage(@ModelAttribute Page page, BindingResult errors, Model model) {
		System.out.println(page.getUrl());

		page.setProcessed(false);

		pageAnalyzerService.processLink(page, errors);

		if(errors.hasErrors()) {
			return "index";
		}

		pageAnalyzerService.processDocument(page);

		page.setProcessed(true);

		model.addAttribute(page);

		return "index";
	}

}
