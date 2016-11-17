package com.csmarton.controllers;

import com.csmarton.model.Page;
import com.csmarton.services.PageAnalyzerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class PageControllerIntegrationTests
{
	private MockMvc mockMvc;

	@Mock
	private PageAnalyzerServiceImpl pageAnalyzerService;

	@InjectMocks
	private PageController pageController;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
	}

	@Test
	public void pageFormGetRequest_ShouldReturnWithStatusOkAndAPageEntity() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(get("/page")).andExpect(status().isOk()).andExpect
				(view().name("index")).andReturn();

		ModelAndView modelAndView = mvcResult.getModelAndView();

		assertWithMessage("Response should contain Page entity").that(modelAndView.getModel())
				.containsKey("page");

		Page returnPage = (Page)modelAndView.getModel().get("page");

		assertWithMessage("Response should contain Page entity")
				.that(returnPage)
				.isInstanceOf(Page.class);
		assertWithMessage("Response Page should be processed")
				.that(returnPage.isProcessed())
				.isFalse();
	}

	@Test
	public void pageFormPostRequestShouldReturnWithStatusOk() throws Exception
	{
		Page page = new Page();

		page.setUrl("http://example.com");

		Page returnPage = performThePostRequest(page);

		assertWithMessage("Response Page should be processed")
				.that(returnPage.isProcessed())
				.isTrue();
	}

	@Test
	public void pagePostRequest_EmptyUrlInput_ShouldReturnWithNonProcessedPage() throws Exception
	{
		Page page = new Page();

		Page returnPage = performThePostRequest(page);

		assertWithMessage("Response Page should be processed")
				.that(returnPage.isProcessed())
				.isFalse();
	}

	private Page performThePostRequest(Page page) throws Exception
	{
		doCallRealMethod().when(pageAnalyzerService).processLink(any(Page.class), any(BindingResult
				.class));

		MvcResult mvcResult = mockMvc.perform(post("/page")
				.param("url", page.getUrl()))
				.andExpect(status().isOk())
				.andExpect(view().name("index")).andReturn();

		ModelAndView modelAndView = mvcResult.getModelAndView();

		assertWithMessage("Response should contain Page entity")
				.that(modelAndView.getModel())
				.containsKey("page");

		Page returnPage = (Page)modelAndView.getModel().get("page");

		assertWithMessage("Response should contain Page entity")
				.that(returnPage)
				.isInstanceOf(Page.class);
		return returnPage;
	}

}
