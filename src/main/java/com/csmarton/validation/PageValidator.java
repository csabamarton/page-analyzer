package com.csmarton.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.csmarton.model.Page;

@Component
public class PageValidator implements Validator
{
	@Override
	public boolean supports(Class<?> aClass)
	{
		return Page.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "url.required");

		Page page = (Page) o;
	}
}
