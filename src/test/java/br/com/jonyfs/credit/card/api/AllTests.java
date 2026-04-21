package br.com.jonyfs.credit.card.api;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.com.jonyfs.credit.card.api.model.CardTypeTest;

@Suite
@SelectClasses({ CardTypeTest.class })
public class AllTests {
}
