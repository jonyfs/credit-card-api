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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class IndexControllerIntegrationTests {

    protected MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(restDocumentation).uris())
                .build();
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
