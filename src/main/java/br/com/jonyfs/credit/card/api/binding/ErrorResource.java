
package br.com.jonyfs.credit.card.api.binding;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(
                ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
// NOSONAR
public class ErrorResource implements Serializable {

    private static final long         serialVersionUID = -2415907856747146978L;

    private String                    code;
    private String                    exception;
    private String                    message;
    private List<FieldErrorResource>  errors;
    private List<GlobalErrorResource> globalErrors;

    private String getErrorNumber() {
        return Long.toHexString(System.currentTimeMillis());
    }

    public ErrorResource(Throwable throwable) {
        this.code = getErrorNumber();
        this.exception = throwable.getClass().getSimpleName();
        this.message = throwable.getMessage();
    }

    public ErrorResource(Throwable throwable, List<FieldErrorResource> erros) {
        if (throwable == null) {
            this.code = getErrorNumber();
        }
        else {
            this.code = getErrorNumber();
            this.exception = throwable.getClass().getSimpleName();
            this.message = throwable.getMessage();
        }
        this.errors = erros;
    }

    public ErrorResource(Throwable throwable, List<GlobalErrorResource> globalErrors, List<FieldErrorResource> errors) {
        if (throwable == null) {
            this.code = getErrorNumber();
        }
        else {
            this.code = getErrorNumber();
            this.exception = throwable.getClass().getSimpleName();
            this.message = throwable.getMessage();
        }
        this.errors = errors;
        this.globalErrors = globalErrors;
    }

    public ErrorResource(String message) {
        this.code = getErrorNumber();
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorResource> getErrors() {
        return errors;
    }

    public List<GlobalErrorResource> getGlobalErrors() {
        return globalErrors;
    }

}