package br.com.jonyfs.credit.card.api;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

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
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.jonyfs.credit.card.api.builder.PaymentBuilder;
import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;
import br.com.jonyfs.credit.card.api.service.PaymentService;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CreditCardApiApplication.class)
@WebAppConfiguration
public class PaymentControllerV3IntegrationTests {

    protected ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.INDENT_OUTPUT, true);

    protected MockMvc mockMvc;

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext wac;

    private RestDocumentationResultHandler document;

    private final DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    
    @Resource
    PaymentService paymentService; 

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        this.document = document(ResourcePaths.Payment.V3.ROOT + "/{method-name}/{step}/",
                preprocessRequest(prettyPrint())

        );

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(this.restDocumentation).snippets())
                .alwaysDo(this.document)
                .build();
        MockitoAnnotations.initMocks(this);

        assertNotNull(mockMvc);

    }

    public HttpHeaders getHeadersGzip() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "content-encoding:gzip");
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "accept-encoding:gzip");
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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyIfPaymentHasNoData() throws Exception {
        
        PaymentBuilder builder = new PaymentBuilder();
        Payment payment = builder.build();
        
        mockMvc
                .perform(
                        post(ResourcePaths.Payment.V3.ROOT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(getHeadersGzip())
                                .content(mapper.writeValueAsString(payment)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
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
        
        mockMvc
                .perform(
                        post(ResourcePaths.Payment.V3.ROOT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(getHeadersGzip())
                                .content(mapper.writeValueAsString(payment)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$", notNullValue())))
                .andReturn();

        // ConstrainedFields fields = new ConstrainedFields(Payment.class);
        // this.document.snippets(
        // requestFields(
        // fields.withPath("chave").type(JsonFieldType.STRING).description("Chave
        // de
        // Autenticação").attributes(key("constraints").value(NotEmpty.class.getSimpleName())),
        // fields.withPath("pdv").type(JsonFieldType.STRING).description("Identificação
        // do
        // PDV").attributes(key("constraints").value(NotEmpty.class.getSimpleName())),
        // fields.withPath("arquivos").type(JsonFieldType.ARRAY).description("Array
        // de arquivos").type(JsonFieldType.ARRAY).description("Array de
        // arquivos").attributes(key("constraints").value(NotEmpty.class.getSimpleName()))),
        // responseFields(
        // fieldWithPath("content").type(JsonFieldType.ARRAY).description("Array
        // com as respostas de tratamento para os documentos fiscais
        // informados."),
        // fieldWithPath("content[0].id").type(JsonFieldType.STRING).description("Identificação
        // do primeiro documento processado."),
        // fieldWithPath("content[0].status").type(JsonFieldType.STRING).description("Status
        // de Processamento do documento armazenado na custódia.")));
        //
        // mockMvc
        // .perform(
        // post(ResourcePaths.DocumentoFiscal.ROOT_V_1_0)
        // .contentType(MediaType.APPLICATION_JSON)
        // .headers(httpHeaders)
        // .content(mapper.writeValueAsString(documentoFiscalRequest)))
        // .andDo(print())
        // .andExpect(status().isCreated())
        // .andExpect(jsonPath("$", notNullValue()))
        // .andExpect((jsonPath("$.content", notNullValue())))
        // .andExpect(jsonPath("$.content", hasSize(1)))
        // .andExpect(jsonPath("$.content[0].id", notNullValue()))
        // .andExpect(jsonPath("$.content[0].status", hasToString("CREATED")))
        // .andReturn();
        //
        // verify(autenticacaoService, only()).isChaveAcessoValida(anyString(),
        // anyString());
        // verify(validacaoService, only()).validaXml(anyString(), anyString());
        // verify(estabelecimentoService, only()).findByCnpj(anyString());

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
        assertNotNull("payment is null!",payment);
        
        mockMvc
        .perform(
                get(ResourcePaths.Payment.V3.ROOT + "/" + payment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(getHeadersGzip())
                        .content(mapper.writeValueAsString(payment)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect((jsonPath("$", notNullValue())))
        .andExpect((jsonPath("$.cardType", notNullValue())))
        .andExpect((jsonPath("$.expirationDate", notNullValue())))
        .andExpect((jsonPath("$.store", notNullValue())))
        .andExpect((jsonPath("$.id", notNullValue())))
        .andReturn();

        // ConstrainedFields fields = new ConstrainedFields(Payment.class);
        // this.document.snippets(
        // requestFields(
        // fields.withPath("chave").type(JsonFieldType.STRING).description("Chave
        // de
        // Autenticação").attributes(key("constraints").value(NotEmpty.class.getSimpleName())),
        // fields.withPath("pdv").type(JsonFieldType.STRING).description("Identificação
        // do
        // PDV").attributes(key("constraints").value(NotEmpty.class.getSimpleName())),
        // fields.withPath("arquivos").type(JsonFieldType.ARRAY).description("Array
        // de arquivos").type(JsonFieldType.ARRAY).description("Array de
        // arquivos").attributes(key("constraints").value(NotEmpty.class.getSimpleName()))),
        // responseFields(
        // fieldWithPath("content").type(JsonFieldType.ARRAY).description("Array
        // com as respostas de tratamento para os documentos fiscais
        // informados."),
        // fieldWithPath("content[0].id").type(JsonFieldType.STRING).description("Identificação
        // do primeiro documento processado."),
        // fieldWithPath("content[0].status").type(JsonFieldType.STRING).description("Status
        // de Processamento do documento armazenado na custódia.")));
        //
        // mockMvc
        // .perform(
        // post(ResourcePaths.DocumentoFiscal.ROOT_V_1_0)
        // .contentType(MediaType.APPLICATION_JSON)
        // .headers(httpHeaders)
        // .content(mapper.writeValueAsString(documentoFiscalRequest)))
        // .andDo(print())
        // .andExpect(status().isCreated())
        // .andExpect(jsonPath("$", notNullValue()))
        // .andExpect((jsonPath("$.content", notNullValue())))
        // .andExpect(jsonPath("$.content", hasSize(1)))
        // .andExpect(jsonPath("$.content[0].id", notNullValue()))
        // .andExpect(jsonPath("$.content[0].status", hasToString("CREATED")))
        // .andReturn();
        //
        // verify(autenticacaoService, only()).isChaveAcessoValida(anyString(),
        // anyString());
        // verify(validacaoService, only()).validaXml(anyString(), anyString());
        // verify(estabelecimentoService, only()).findByCnpj(anyString());

    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}
