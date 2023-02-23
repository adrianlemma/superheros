package com.challenge.w2m.superheros.entity;

import com.challenge.w2m.superheros.exception.SuperheroException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static com.challenge.w2m.superheros.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class SuperheroTest {

    @TestFactory
    @DisplayName("Test null validators for params")
    Stream<DynamicNode> testFactoryNullValues() {
        return Stream.of("Null").map(input -> dynamicContainer("Params in " + input,
                Stream.of(
                        dynamicTest("Name " + input,
                                () -> assertThrows(SuperheroException.class,
                                        () -> new Superhero(SUPERHERO_ID, null, SUPERHERO_SECRET_IDENTITY,
                                                List.of(SUPERHERO_SUPER_POWER)))),
                        dynamicTest("SecretIdentity " + input,
                                () -> assertThrows(SuperheroException.class,
                                        () -> new Superhero(SUPERHERO_ID, SUPERHERO_NAME, null,
                                                List.of(SUPERHERO_SUPER_POWER)))),
                        dynamicTest("SuperPowers " + input,
                                () -> assertThrows(SuperheroException.class,
                                        () -> new Superhero(SUPERHERO_ID, SUPERHERO_NAME, SUPERHERO_SECRET_IDENTITY, null))),
                        dynamicTest("SuperPower empty",
                                () -> assertThrows(SuperheroException.class,
                                        () -> new Superhero(SUPERHERO_ID, SUPERHERO_NAME, SUPERHERO_SECRET_IDENTITY, List.of(" "))))
                )));
    }

    @Test
    @DisplayName("test getters and setters only for coverage")
    void testGettersAndSettersOnlyForCoverage() {
        Superhero superhero = new Superhero();
        superhero.setSuperheroId(SUPERHERO_ID);
        superhero.setName(SUPERHERO_NAME);
        superhero.setSecretIdentity(SUPERHERO_SECRET_IDENTITY);
        superhero.setSuperPowers(List.of(SUPERHERO_SUPER_POWER));
        assertEquals(SUPERHERO_ID, superhero.getSuperheroId());
        assertEquals(SUPERHERO_NAME, superhero.getName());
        assertEquals(SUPERHERO_SECRET_IDENTITY, superhero.getSecretIdentity());
        assertEquals(SUPERHERO_SUPER_POWER, superhero.getSuperPowers().get(0));
    }
}
