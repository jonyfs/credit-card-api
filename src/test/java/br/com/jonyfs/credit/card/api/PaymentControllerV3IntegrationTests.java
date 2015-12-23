package br.com.jonyfs.credit.card.api;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.*;
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

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
                classes = CreditCardApiApplication.class)
@WebAppConfiguration
public class PaymentControllerV3IntegrationTests {

    protected ObjectMapper         mapper            = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.INDENT_OUTPUT, true);

    protected MockMvc              mockMvc;

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext  wac;

    private final DateFormat       df                = new SimpleDateFormat("yyyy-mm-dd");

    @Resource
    PaymentService                 paymentService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(documentationConfiguration(this.restDocumentation).snippets()).build();
        MockitoAnnotations.initMocks(this);

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
        Product product = new Product("Bike", 20.75);
        return product;
    }

    public Product getIphone6S() {
        Product product = new Product("iPhone 6S", 199.99);
        return product;
    }

    public Product getMotoX2015() {
        Product product = new Product("Moto X 2015", 149.27);
        return product;
    }

    public Product getSandiskCruzer32GBFlashDrive() {
        Product product = new Product("Sandisk Cruzer 32GB Flash Drive", 9.99);
        return product;
    }

    public Store getAmazon() {
        Store store = new Store("Amazon");
        return store;
    }

    public Store getBestBuy() {
        Store store = new Store("Best Buy");
        return store;
    }

    public Store getBhPhoto() {
        Store store = new Store("BH Photo");
        return store;
    }

    public Store getWalmart() {
        Store store = new Store("Walmart");
        return store;
    }

    public Date getExpirationDate() {
        try {
            return df.parse("2030-04-30");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyIfPaymentHasNoData() throws Exception {

        PaymentBuilder builder = new PaymentBuilder();
        Payment payment = builder.build();
        mockMvc.perform(post(ResourcePaths.Payment.V3.ROOT).contentType(MediaType.APPLICATION_JSON).headers(getRequestHeaders()).content(mapper.writeValueAsString(payment)))
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
                               responseFields(fieldWithPath("code").type(JsonFieldType.STRING).description("The code id of problem. Use it for support only"),
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

        mockMvc.perform(post(ResourcePaths.Payment.V3.ROOT).contentType(MediaType.APPLICATION_JSON).headers(getRequestHeaders()).content(mapper.writeValueAsString(payment)))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect((jsonPath("$", notNullValue())))
               .andExpect((jsonPath("$._links", notNullValue())))
               .andDo(document("." + ResourcePaths.Payment.V3.ROOT + "/{method-name}",
                               preprocessRequest(prettyPrint()),
                               preprocessResponse(prettyPrint()),
                               requestFields(fieldWithPath("id").type(JsonFieldType.STRING).description("The Credit Card Transaction ID."),
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
        assertNotNull("payment is null!", payment);

        mockMvc.perform(get(ResourcePaths.Payment.V3.GET, payment.getId()).contentType(MediaType.APPLICATION_JSON).headers(getRequestHeaders()))
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

                               responseFields(fieldWithPath("id").type(JsonFieldType.STRING).description("The Credit Card Transaction ID."),
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
