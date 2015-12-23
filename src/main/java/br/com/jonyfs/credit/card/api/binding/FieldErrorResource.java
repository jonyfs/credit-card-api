package br.com.jonyfs.credit.card.api.binding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(
                ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldErrorResource extends GlobalErrorResource {

    private String parameter;

    public FieldErrorResource(String parameter, String code, String message) {
        super(code, message);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return "FieldErrorResource [parameter=" + parameter + ", code=" + getCode() + ", message=" + getMessage() + "]";
    }

}