package com.csmarton.services;

import com.csmarton.model.Page;
import org.springframework.validation.BindingResult;

public interface PageAnalyzerService
{
	void processLink(Page page, BindingResult errors);

	void processDocument(Page page);
}
