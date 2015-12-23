package br.com.jonyfs.credit.card.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.stereotype.Component;

@RunWith(Suite.class)
@SuiteClasses({ IndexControllerIntegrationTests.class, VersionControllerIntegrationTests.class, PaymentControllerV3IntegrationTests.class })
@Component
public class AllIntegrationTests {

}
