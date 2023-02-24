package com.challenge.w2m.superheros;

import com.challenge.w2m.superheros.aop.EmptyComponentForTesting;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.challenge.w2m.superheros.constants.Constants.FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SuperherosApplicationTests {

	// Aprovecho que el contexto ya esta cargado para no cargarlo 2 veces
	@Autowired
	EmptyComponentForTesting emptyComponentForTesting;

	@AfterEach
	void removeTestData() {
		File file = new File(FILE_PATH);
		File directory = new File(file.getParent());
		if (file.exists()) {
			file.delete();
		}
		if (directory.exists()) {
			directory.delete();
		}
	}

	@Test
	@DisplayName("test for custom annotation")
	void testForCustomAnnotation() {
		emptyComponentForTesting.methodForTestingCustomAnnotation();
		File result = new File(FILE_PATH);
		assertTrue(result.exists());
		assertTrue(result.length() > 0);
	}

	@Test
	void contextLoads() {
		SuperherosApplication.main(new String[] {});
	}

}
