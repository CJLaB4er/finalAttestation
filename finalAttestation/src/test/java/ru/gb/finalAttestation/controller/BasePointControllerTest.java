package ru.gb.finalAttestation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.gb.finalAttestation.dto.AreaDto;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.mapper.BasePointMapper;
import ru.gb.finalAttestation.model.BasePoint;
import ru.gb.finalAttestation.repository.BasePointRepository;
import ru.gb.finalAttestation.service.PointComparator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BasePointControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BasePointRepository basePointRepository;

    @Autowired
    BasePointMapper basePointMapper;

    @Autowired
    PointComparator pointComparator;

    @BeforeEach
    public void setUp() {
        basePointRepository.deleteAll();
    }

    @Test
    void getAllBasePointsTest() {
        List<BasePoint> expectedBasePointList = createDemoPoints();
        basePointRepository.saveAll(expectedBasePointList);

        List<BasePointDto> responseBody = webTestClient.get()
                .uri("point")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(expectedBasePointList.size(), responseBody.size());
        boolean result;
        for (int i = 0; i < expectedBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectedBasePointList.get(i), responseBody.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getBasePointByIdTestSuccess() {
        BasePoint expectBasePoint = basePointRepository.save(new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        ));

        BasePointDto responseBody = webTestClient.get()
                .uri("point/" + expectBasePoint.getId())
                .exchange()
                .expectBody(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        boolean result = pointComparator.compareBasePointBasePointDTO(expectBasePoint, responseBody);
        assertTrue(result);
    }

    @Test
    void getBasePointByIdNotFound() {
        basePointRepository.saveAll(createDemoPoints());

        webTestClient.get()
                .uri("point/4")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getBasePointByNameTestSuccess() {
        List<BasePoint> expectedBasePointList = createDemoPoints();
        basePointRepository.saveAll(expectedBasePointList);

        List<BasePointDto> responseBody = webTestClient.get()
                .uri("point/name/Гор")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(expectedBasePointList.size(), responseBody.size());
        boolean result;
        for (int i = 0; i < expectedBasePointList.size(); i++) {
            result = pointComparator
                    .compareBasePointBasePointDTO(expectedBasePointList.get(i),
                            responseBody.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getBasePointByNameNotFound() {
        basePointRepository.saveAll(createDemoPoints());

        webTestClient.get()
                .uri("point/name/1234")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getBasePointBySheetTestSuccess() {
        List<BasePoint> expectedBasePointList = createDemoPoints();
        basePointRepository.saveAll(expectedBasePointList);

        List<BasePointDto> responseBody = webTestClient.get()
                .uri("point/sheet/L-37")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(expectedBasePointList.size(), responseBody.size());
        boolean result;
        for (int i = 0; i < expectedBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectedBasePointList.get(i), responseBody.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getBasePointBySheetNotFound() {
        basePointRepository.saveAll(createDemoPoints());

        webTestClient.get()
                .uri("point/sheet/1234")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void removeBasePointByIdTest() {
        BasePoint expectBasePoint = basePointRepository.save(new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        ));

        BasePointDto responseBody = webTestClient.delete()
                .uri("point/" + expectBasePoint.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        boolean result = pointComparator.compareBasePointBasePointDTO(
                expectBasePoint, responseBody
        );
        assertTrue(result);

    }

    @Test
    void createBasePointTest() {
        BasePointDto expectBasePointDto = new BasePointDto();
        expectBasePointDto.setName("1001");
        expectBasePointDto.setX(999999999999L);
        expectBasePointDto.setY(888888888888L);
        expectBasePointDto.setZ(9000000000L);
        expectBasePointDto.setSheet("L-37-32");
        expectBasePointDto.setAccuracyClass("2кл");
        expectBasePointDto.setCoordinateSystem("МСК-61-2");

        BasePointDto responseBody = webTestClient.post()
                .uri("point")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expectBasePointDto),BasePointDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        boolean result = pointComparator.compareBasePointDtoBasePointDto(
                expectBasePointDto, responseBody);
        assertTrue(result);
    }

    @Test
    void getByAreaTest() {
        Random random = new Random();
        List<BasePoint> expectedBasePointList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expectedBasePointList.add(new BasePoint(
                    "basePoint_" + (i + 1),
                    random.nextInt(1000),
                    random.nextInt(1000),
                    random.nextInt(100),
                    "Empty",
                    "Empty",
                    "Empty"
            ));
            basePointRepository.saveAll(expectedBasePointList);
            AreaDto areaDto = new AreaDto();
            areaDto.setX(0);
            areaDto.setY(0);
            areaDto.setAreaHeight(1000);
            areaDto.setAreaWidth(1000);

            List<BasePointDto> responseBody = webTestClient.post()
                    .uri("point/area")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(areaDto), AreaDto.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(BasePointDto.class)
                    .returnResult()
                    .getResponseBody();

            assertNotNull(responseBody);
            assertEquals(expectedBasePointList.size(), responseBody.size());
            boolean result;
            for (int j = 0; j < expectedBasePointList.size(); j++) {
                result = pointComparator.compareBasePointBasePointDTO(
                        expectedBasePointList.get(i),
                        responseBody.get(i));
                assertTrue(result);
            }

        }

    }

    @Test
    void updateBasePointTest() {
        BasePoint expectBasePoint = basePointRepository.save(new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        ));
        BasePointDto expectBasePointDto = new BasePointDto();
        expectBasePointDto.setName("1001");
        expectBasePointDto.setX(999999999999L);
        expectBasePointDto.setY(888888888888L);
        expectBasePointDto.setZ(9000000000L);
        expectBasePointDto.setSheet("L-37-32");
        expectBasePointDto.setAccuracyClass("2кл");
        expectBasePointDto.setCoordinateSystem("МСК-61-2");

        BasePointDto responseBody = webTestClient.put()
                .uri("point/" + expectBasePoint.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expectBasePointDto), BasePointDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BasePointDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        boolean result = pointComparator.compareBasePointDtoBasePointDto(
                expectBasePointDto, responseBody);
        assertTrue(result);
    }

    /**
     * Gets list of BasePoint instance
     * @return list
     */
    private List<BasePoint> createDemoPoints() {
        return List.of(
                new BasePoint(
                        "Горная",
                        5212365400L,
                        7575821450L,
                        1159780L,
                        "L-37-09",
                        "3кл",
                        "1942г"),
                new BasePoint(
                        "Горняк",
                        5212456400L,
                        7575789450L,
                        1122280L,
                        "L-37-10",
                        "3кл",
                        "1942г")
        );
    }
}