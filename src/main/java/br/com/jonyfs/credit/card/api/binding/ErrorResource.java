package br.com.jonyfs.credit.card.api.binding;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
	private String code;
	private String message;
	private List<FieldErrorResource> fieldErrors;

	public ErrorResource() {
	}

	public ErrorResource(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResource [code=" + code + ", message=" + message + ", fieldErrors=" + fieldErrors + "]";
	}
}