package com.csmarton.services;

import com.csmarton.model.Page;
import com.csmarton.services.analyzer.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.Map;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.spy;

public class PageAnalyzerServiceImplTest {
	private final static String SPIEGEL_LOGIN_PAGE_URL = "https://www.spiegel.de/meinspiegel/login" 
			+ ".html";

	PageAnalyzerServiceImpl pageAnalyzerService;
	Page page;
	Document examplePageDocment;

	HtmlVersionAnalyzer htmlVersionAnalyzer;
	PageTitleAnalyzer pageTitleAnalyzer;
	PageHeadingsAnalyzer pageHeadingsAnalyzer;
	PageLinkAnalyzer pageLinkAnalyzer;
	LoginFormRecognizer loginFormRecognizer;


	@Before
	public void setUp() throws Exception
	{
		page = new Page();
		page.setUrl("http://example.com/");
		examplePageDocment = Jsoup.connect("http://example.com/").get();

		pageAnalyzerService = spy(PageAnalyzerServiceImpl.class);

		htmlVersionAnalyzer = spy(HtmlVersionAnalyzer.class);
		pageHeadingsAnalyzer = spy(PageHeadingsAnalyzer.class);
		pageLinkAnalyzer = spy(PageLinkAnalyzer.class);
		loginFormRecognizer = spy(LoginFormRecognizer.class);


	}

	@Test
	public void callGetPageTitle_whenPassingHtmlDocument_shouldReturnWithTitle()
	{
		String title = pageTitleAnalyzer.getPageTitle(examplePageDocment);

		assertWithMessage("There was some problem during parsing the title").that(title)
				.isNotEmpty();
		assertWithMessage("Wrong titlehas been parsed").that(title).isEqualTo("Example Domain");
	}

	@Test
	public void callProcessLink_WithValidLink_ShouldSetDocument()
	{
		BindingResult bindingResult = spy(BindingResult.class);

		page.setUrl("http://example.com/");
		pageAnalyzerService.processLink(page, bindingResult);

		assertWithMessage("Link could not been processed").that(page.getDocument()).isNotNull();
	}

	@Test
	public void callProcessLink_WithOutValidLink_ShouldNotSetDocument()
	{
		BindingResult bindingResult = spy(BindingResult.class);

		page.setUrl(null);

		pageAnalyzerService.processLink(page, bindingResult);

		assert_().that(page.getDocument()).isNull();

	}

	@Test
	public void callNumOfHeadings_WithValidExampleLink_ShouldReturnHeadings()
	{
		Map<Integer, Integer> numOfHeadings = pageHeadingsAnalyzer.numOfHeadings(examplePageDocment);

		assertWithMessage("Heading could not been processed").that(numOfHeadings).isNotNull();
		assertWithMessage("Wrong number of H1s").that(numOfHeadings).containsKey(1);
	}

	@Test
	public void callCheckLinks_ShouldReturnWithLinks()
	{
		page.setDocument(examplePageDocment);

		pageLinkAnalyzer.checkLinks(page);

		assertWithMessage("Wrong number of External Links").that(page.getNumOfExternalLinks())
				.isEqualTo(1);
		assertWithMessage("Wrong number of Internal Links").that(page.getNumOfInternalLinks())
				.isEqualTo(0);
	}

	@Test
	public void callCheckLinks_WithSpiegelLink_ShouldReturnWithLinks() throws IOException
	{
		page.setUrl(SPIEGEL_LOGIN_PAGE_URL);
		page.setDocument(Jsoup.connect(page.getUrl()).get());

		pageLinkAnalyzer.checkLinks(page);

		assertWithMessage("Wrong number of External Links").that(page.getNumOfExternalLinks())
				.isEqualTo(96);
		assertWithMessage("Wrong number of Internal Links").that(page.getNumOfInternalLinks())
				.isEqualTo(174);
	}

	@Test
	public void spiegelLoginPage_ShouldBeRecognised() throws IOException
	{ // my funniest method name ever :)
		page.setUrl(SPIEGEL_LOGIN_PAGE_URL);
		page.setDocument(Jsoup.connect(page.getUrl()).get());

		boolean b = loginFormRecognizer.containsLoginForm(page.getDocument());
		assertWithMessage("Spiegel Login page was not recognised").that(b).isTrue();
	}
}