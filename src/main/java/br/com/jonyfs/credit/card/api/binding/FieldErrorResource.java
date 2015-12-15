package br.com.jonyfs.credit.card.api.binding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldErrorResource extends GlobalErrorResource {

    private String parametro;

    public FieldErrorResource(String parametro, String codigo, String mensagem) {
        super(codigo, mensagem);
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }

    @Override
    public String toString() {
        return "FieldErrorResource [parametro=" + parametro + ", codigo=" + getCodigo() + ", mensagem=" + getMensagem() + "]";
    }

}