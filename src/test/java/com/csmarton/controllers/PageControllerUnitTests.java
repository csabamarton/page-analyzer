package com.csmarton.controllers;

import com.csmarton.model.Page;
import com.csmarton.services.PageAnalyzerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

public class PageControllerUnitTests {
	private MockMvc mockMvc;

	@Mock
	private PageAnalyzerService pageAnalyzerService;

	@InjectMocks
	private PageController pageController;

	private BindingResult result;
	private Model model;
	private Page page;


	@Before
	public void setUp() throws Exception
	{
		result = mock(BindingResult.class);
		model = mock(Model.class);
		page = mock(Page.class);

		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();

	}

	@Test
	public void callingPagePostRequestMethod_WhenHasFormValidationError_ShouldNotProcessTheDocument()
	{
		when(result.hasErrors()).thenReturn(true);

		pageController.processPage(page, result, model);

		verify(pageAnalyzerService, never()).processDocument(page);
	}

	@Test
	public void callingPagePostRequestMethod_WhenNoFormValidationError_ShouldProcessTheDocument()
	{
		when(result.hasErrors()).thenReturn(false);

		pageController.processPage(page, result, model);

		verify(pageAnalyzerService, atLeastOnce()).processDocument(page);
	}
}