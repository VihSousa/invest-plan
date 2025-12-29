package br.com.vihsousa.invest_plan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"api.security.token.secret=12345678-segredo-de-teste-muito-longo-para-funcionar",

		// Forces the use of the H2 memory bank.
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.jpa.hibernate.ddl-auto=create-drop",

		// Turns off the swagger for the test
		"springdoc.api-docs.enabled=false"
})
class InvestmentPlanningApplicationTests {
	@Test
	void contextLoads() { }
}