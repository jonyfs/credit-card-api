package br.com.jonyfs.credit.card.api;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ IndexControllerIntegrationTests.class, VersionControllerIntegrationTests.class, PaymentControllerV3IntegrationTests.class })
public class AllIntegrationTests {

}
