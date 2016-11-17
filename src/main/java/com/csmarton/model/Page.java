package com.csmarton.model;

import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;

import java.util.Map;

public class Page
{
	private String url;

	private String title;

	private String htmlVersion;

	private Document document;

	private int numOfInternalLinks;

	private int numOfExternalLinks;

	private boolean containsLoginForm;

	private Map<Integer, Integer> numOfHeadings = Maps.newTreeMap();

	private boolean processed = false;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getHtmlVersion()
	{
		return htmlVersion;
	}

	public void setHtmlVersion(String htmlVersion)
	{
		this.htmlVersion = htmlVersion;
	}

	public Document getDocument()
	{
		return document;
	}

	public void setDocument(Document document)
	{
		this.document = document;
	}

	public int getNumOfInternalLinks()
	{
		return numOfInternalLinks;
	}

	public void setNumOfInternalLinks(int numOfInternalLinks)
	{
		this.numOfInternalLinks = numOfInternalLinks;
	}

	public int getNumOfExternalLinks()
	{
		return numOfExternalLinks;
	}

	public boolean isContainsLoginForm()
	{
		return containsLoginForm;
	}

	public void setContainsLoginForm(boolean containsLoginForm)
	{
		this.containsLoginForm = containsLoginForm;
	}

	public void setNumOfExternalLinks(int numOfExternalLinks)
	{
		this.numOfExternalLinks = numOfExternalLinks;
	}

	public Map<Integer, Integer> getNumOfHeadings()
	{
		return numOfHeadings;
	}

	public void setNumOfHeadings(Map<Integer, Integer> numOfHeadings)
	{
		this.numOfHeadings = numOfHeadings;
	}

	public boolean isProcessed()
	{
		return processed;
	}

	public void setProcessed(boolean processed)
	{
		this.processed = processed;
	}
}
