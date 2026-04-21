package br.com.jonyfs.credit.card.api;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.jonyfs.credit.card.api.builder.PaymentBuilder;
import br.com.jonyfs.credit.card.api.exceptions.InvalidRequestException;
import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;
import br.com.jonyfs.credit.card.api.service.PaymentService;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

import jakarta.validation.constraints.NotEmpty;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PaymentControllerV3IntegrationTests {

    protected ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private final DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

    @Resource
    PaymentService paymentService;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(restDocumentation).snippets())
                .build();

        assertNotNull(mockMvc);
    }

    public HttpHeaders getRequestHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        return httpHeaders;
    }

    public HttpHeaders getResponseHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
        return httpHeaders;
    }

    public Product getBike() {
        return new Product("Bike", 20.75);
    }

    public Product getIphone6S() {
        return new Product("iPhone 6S", 199.99);
    }

    public Product getMotoX2015() {
        return new Product("Moto X 2015", 149.27);
    }

    public Product getSandiskCruzer32GBFlashDrive() {
        return new Product("Sandisk Cruzer 32GB Flash Drive", 9.99);
    }

    public Store getAmazon() {
        return new Store("Amazon");
    }

    public Store getBestBuy() {
        return new Store("Best Buy");
    }

    public Store getBhPhoto() {
        return new Store("BH Photo");
    }

    public Store getWalmart() {
        return new Store("Walmart");
    }

    public Date getExpirationDate() {
        try {
            return df.parse("2030-04-30");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyIfPaymentHasNoData() throws Exception {

        PaymentBuilder builder = new PaymentBuilder();
        Payment payment = builder.build();
        mockMvc.perform(post(ResourcePaths.Payment.V3.ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getRequestHeaders())
                .content(mapper.writeValueAsString(payment)))
               .andDo(print())
               .andExpect(status().isUnprocessableEntity())
               .andExpect((jsonPath("$", notNullValue())))
               .andExpect((jsonPath("$.code", notNullValue())))
               .andExpect((jsonPath("$.message", notNullValue())))
               .andExpect((jsonPath("$.exception", hasToString(InvalidRequestException.class.getSimpleName()))))
               .andExpect(jsonPath("$.errors", hasSize(5)))
               .andDo(document("." + ResourcePaths.Payment.V3.ROOT + "/{method-name}",
                               preprocessRequest(prettyPrint()),
                               preprocessResponse(prettyPrint()),
                               responseFields(
                                       fieldWithPath("code").type(JsonFieldType.STRING).description("The code id of problem. Use it for support only"),
                                       fieldWithPath("exception").type(JsonFieldType.STRING).description("Exception"),
                                       fieldWithPath("message").type(JsonFieldType.STRING).description("The message to explain the problem"),
                                       fieldWithPath("errors").type(JsonFieldType.ARRAY).description("Array with constraint problems"))))
               .andReturn();
    }

    @Test
    public void verifyIfPaymentWasCreated() throws JsonProcessingException, Exception {
        PaymentBuilder builder = new PaymentBuilder();
        builder.with(CardType.VISA);
        builder.with("4716651077977392");
        builder.with(getWalmart());
        builder.with(getMotoX2015());
        builder.with(getSandiskCruzer32GBFlashDrive());
        builder.with(getExpirationDate());

        Payment payment = builder.build();

        mockMvc.perform(post(ResourcePaths.Payment.V3.ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getRequestHeaders())
                .content(mapper.writeValueAsString(payment)))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect((jsonPath("$", notNullValue())))
               .andExpect((jsonPath("$._links", notNullValue())))
               .andDo(document("." + ResourcePaths.Payment.V3.ROOT + "/{method-name}",
                               preprocessRequest(prettyPrint()),
                               preprocessResponse(prettyPrint()),
                               requestFields(
                                       fieldWithPath("id").type(JsonFieldType.STRING).description("The Credit Card Transaction ID."),
                                       fieldWithPath("cardType").type(JsonFieldType.STRING).description("Credit Card Type").attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("cardNumber").type(JsonFieldType.STRING)
                                               .description("Credit Card Number")
                                               .attributes(key("constraints").value(CreditCardNumber.class.getSimpleName())),
                                       fieldWithPath("expirationDate").type(JsonFieldType.OBJECT)
                                               .description("Credit Card Expiration Date")
                                               .attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("store").type(JsonFieldType.OBJECT)
                                               .description("Store")
                                               .type(JsonFieldType.ARRAY)
                                               .attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("products").type(JsonFieldType.ARRAY)
                                               .description("Products Array")
                                               .type(JsonFieldType.ARRAY)
                                               .attributes(key("constraints").value(NotEmpty.class.getSimpleName())))))
               .andReturn();
    }

    @Test
    public void verifyIfPaymentCreatedExists() throws JsonProcessingException, Exception {

        PaymentBuilder builder = new PaymentBuilder();
        builder.with(CardType.VISA);
        builder.with("4716651077977392");
        builder.with(getWalmart());
        builder.with(getMotoX2015());
        builder.with(getSandiskCruzer32GBFlashDrive());
        builder.with(getExpirationDate());

        Payment payment = builder.build();

        payment = paymentService.doPayment(payment);
        assertNotNull(payment, "payment is null!");

        mockMvc.perform(get(ResourcePaths.Payment.V3.GET, payment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getRequestHeaders()))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect((jsonPath("$", notNullValue())))
               .andExpect((jsonPath("$.id", notNullValue())))
               .andExpect((jsonPath("$.cardType", notNullValue())))
               .andExpect((jsonPath("$.expirationDate", notNullValue())))
               .andExpect((jsonPath("$.store", notNullValue())))
               .andExpect(jsonPath("$.products", hasSize(2)))
               .andDo(document("." + ResourcePaths.Payment.V3.ROOT + "/{method-name}",
                               preprocessRequest(prettyPrint()),
                               preprocessResponse(prettyPrint()),
                               pathParameters(parameterWithName("id").description("The Credit Card Transaction ID.")),
                               responseFields(
                                       fieldWithPath("id").type(JsonFieldType.STRING).description("The Credit Card Transaction ID."),
                                       fieldWithPath("cardType").type(JsonFieldType.STRING).description("Credit Card Type").attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("cardNumber").type(JsonFieldType.STRING)
                                               .description("Credit Card Number")
                                               .attributes(key("constraints").value(CreditCardNumber.class.getSimpleName())),
                                       fieldWithPath("expirationDate").type(JsonFieldType.OBJECT)
                                               .description("Credit Card Expiration Date")
                                               .attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("store").type(JsonFieldType.OBJECT)
                                               .description("Store")
                                               .type(JsonFieldType.ARRAY)
                                               .attributes(key("constraints").value(NotNull.class.getSimpleName())),
                                       fieldWithPath("products").type(JsonFieldType.ARRAY)
                                               .description("Products Array")
                                               .type(JsonFieldType.ARRAY)
                                               .attributes(key("constraints").value(NotEmpty.class.getSimpleName())),
                                       fieldWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS links"))))
               .andReturn();
    }

}
