package br.com.jonyfs.credit.card.api.binding;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource implements Serializable {

    private static final long serialVersionUID = -2415907856747146978L;

    private String codigo;
    private String exception;
    private String mensagem;
    private List<FieldErrorResource> erros;
    private List<GlobalErrorResource> globalErros;

    private String getErrorNumber() {
        return Long.toHexString(System.currentTimeMillis());
    }

    public ErrorResource(Throwable throwable) {
        this.codigo = getErrorNumber();
        this.exception = throwable.getClass().getSimpleName();
        this.mensagem = throwable.getMessage();
    }

    public ErrorResource(Throwable throwable, List<FieldErrorResource> erros) {
        if (throwable == null) {
            this.codigo = getErrorNumber();
        } else {
            this.codigo = getErrorNumber();
            this.exception = throwable.getClass().getSimpleName();
            this.mensagem = throwable.getMessage();
        }
        this.erros = erros;
    }

    public ErrorResource(Throwable throwable, List<GlobalErrorResource> globalErrors, List<FieldErrorResource> erros) {
        if (throwable == null) {
            this.codigo = getErrorNumber();
        } else {
            this.codigo = getErrorNumber();
            this.exception = throwable.getClass().getSimpleName();
            this.mensagem = throwable.getMessage();
        }
        this.erros = erros;
        this.globalErros = globalErrors;
    }

    public ErrorResource(String mensagem) {
        this.codigo = getErrorNumber();
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getException() {
        return exception;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<FieldErrorResource> getErros() {
        return erros;
    }

    public List<GlobalErrorResource> getGlobalErros() {
        return globalErros;
    }

    @Override
    public String toString() {
        return "ErrorResource [codigo=" + codigo + ", exception=" + exception + ", mensagem=" + mensagem + ", erros=" + erros + ", globalErros=" + globalErros + "]";
    }

}