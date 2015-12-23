/**
 * 
 */
package br.com.jonyfs.credit.card.api.binding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author jony.santos@bematech.com.br
 *
 */
@JsonIgnoreProperties(
                ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GlobalErrorResource {
    private String code;
    private String message;

    public GlobalErrorResource(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
