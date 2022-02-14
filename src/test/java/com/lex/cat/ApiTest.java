package com.lex.cat;

import com.lex.cat.model.Cat;
import com.lex.cat.model.type.CatColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.List;

@Testcontainers
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(initializers = {AbstractTest.Initializer.class})
@AutoConfigureWebTestClient
class ApiTest extends AbstractTest {

    @Autowired
    private WebTestClient webTestClient;

    private final Cat cat1 = new Cat(
            "Barsik",
            CatColor.BLACK,
            1,
            1
    );
    private final Cat cat2 = new Cat(
            "Murzik",
            CatColor.BLACK,
            10,
            10
    );

    private WebTestClient.ResponseSpec saveCat(Cat cat) {
        return webTestClient.post()
                .uri("/cat")
                .bodyValue(cat)
                .exchange();
    }

    @Test
    public void saveAndReturn() {
        saveCat(cat1);

        List<Cat> cats = webTestClient.get()
                .uri("/cats")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Cat.class)
                .returnResult()
                .getResponseBody();

        assert cats != null;
        Assertions.assertFalse(cats.isEmpty());
        Assertions.assertEquals(cat1, cats.get(0));
    }

    @Test
    public void incorrectSave() {
        saveCat(new Cat()).expectStatus().is5xxServerError();
    }

    @Test
    public void getOffsetByOrder() {
        saveCat(cat1);
        saveCat(cat2);

        List<Cat> cats = webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/cats")
                                .queryParam("offset", 1)
                                .queryParam("attribute", "tailLength")
                                .queryParam("order", "DESC")
                                .build()

                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Cat.class)
                .returnResult()
                .getResponseBody();

        assert cats != null;
        Assertions.assertEquals(1, cats.size());
        Assertions.assertEquals(cat1, cats.get(0));
    }

    @Test
    public void getOffsetByIncorrectParams() {
        webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/cats")
                                .queryParam("offset", -10)
                                .build()

                )
                .exchange()
                .expectStatus()
                .is5xxServerError();

        webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/cats")
                                .queryParam("attribute", "NOT_EXISTED_FIELD")
                                .build()

                )
                .exchange()
                .expectStatus()
                .is5xxServerError();

        webTestClient.get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/cats")
                                .queryParam("order", "NOT_EXISTED_ORDER")
                                .build()

                )
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    public void checkRateLimit() {
        Cat cat = new Cat(
                RandomStringUtils.random(10),
                CatColor.BLACK,
                1,
                1
        );

        for (int i = 0; i < RATE_LIMIT; i++) {
            saveCat(cat).expectStatus().isOk();
        }

        saveCat(cat)
                .expectStatus()
                .is4xxClientError();
    }
}
