package br.com.jonyfs.credit.card.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
                classes = CreditCardApiApplication.class)
@WebAppConfiguration
public class IndexControllerIntegrationTests {

    protected MockMvc                      mockMvc;

    @Rule
    public final RestDocumentation         restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext          wac;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(documentationConfiguration(this.restDocumentation).uris()).build();
    }

    @Test
    public void test() throws Exception {
        this.document = document("." + ResourcePaths.ROOT_API, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));

        this.mockMvc.perform(get(ResourcePaths.ROOT_API).accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andDo(document)
                    .andExpect(status().isOk())
                    .andExpect((jsonPath("$", notNullValue())))
                    .andExpect((jsonPath("$._links", notNullValue())))
                    .andExpect((jsonPath("$._links.ex:payments", hasSize(3))));
    }

}
