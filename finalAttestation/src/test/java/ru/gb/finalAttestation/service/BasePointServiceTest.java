package ru.gb.finalAttestation.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.finalAttestation.dto.AreaDto;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.mapper.BasePointMapper;
import ru.gb.finalAttestation.mapper.BasePointMapperImpl;
import ru.gb.finalAttestation.model.BasePoint;
import ru.gb.finalAttestation.repository.BasePointRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        BasePointMapperImpl.class,
        BasePointServiceImpl.class,
        PointComparator.class,

})
class BasePointServiceTest {

    @Autowired
    private BasePointService basePointService;

    @Autowired
    private BasePointMapper basePointMapper;

    @Autowired
    private PointComparator pointComparator;

    @MockBean
    private BasePointRepository basePointRepository;


    @Test
    void getAllTest() {
        List<BasePoint> expectBasePointList = createDemoPoints();
        Mockito.when(basePointRepository.findAll()).thenReturn(expectBasePointList);

        List<BasePointDto> actualBasePointList = basePointService.getAll();

        assertNotNull(actualBasePointList);
        assertEquals(expectBasePointList.size(), actualBasePointList.size());
        boolean result;
        for (int i = 0; i < expectBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectBasePointList.get(i), actualBasePointList.get(i));
            assertTrue(result);

        }
    }

    @Test
    void getByIdTest() {
        BasePoint expectBasePoint = new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        );
        Optional<BasePoint> basePointOptional = Optional.of(expectBasePoint);
        Mockito.when(basePointRepository.findById(1L)).thenReturn(basePointOptional);

        BasePointDto actualBasePointdto = basePointService.getById(1);

        assertNotNull(actualBasePointdto);
        boolean result = pointComparator.compareBasePointBasePointDTO(expectBasePoint, actualBasePointdto);
        assertTrue(result);
    }

    @Test
    void getByIdThrowException() {

        Mockito.when(basePointRepository.findById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> basePointService.getById(1L));
    }

    @Test
    void getByNameTest() {
        List<BasePoint> expectBasePointList = createDemoPoints();
        Mockito.when(basePointRepository.findByName("Гор")).thenReturn(expectBasePointList);

        List<BasePointDto> actualBasePointDtoList = basePointService.getByName("Гор");

        assertNotNull(actualBasePointDtoList);
        assertEquals(expectBasePointList.size(), actualBasePointDtoList.size());
        boolean result;
        for (int i = 0; i < expectBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectBasePointList.get(i), actualBasePointDtoList.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getByNameThrowsException() {
        List<BasePoint> expectBasePointList = new ArrayList<>();
        Mockito.when(basePointRepository.findByName("Гор")).thenReturn(expectBasePointList);

        assertThrows(RuntimeException.class, () -> basePointService.getByName("Гор"));
    }

    @Test
    void getBySheet() {
        List<BasePoint> expectBasePointList = createDemoPoints();
        Mockito.when(basePointRepository.findBySheet("L-37")).thenReturn(expectBasePointList);

        List<BasePointDto> actualBasePointDtoList = basePointService.getBySheet("L-37");

        assertNotNull(actualBasePointDtoList);
        assertEquals(expectBasePointList.size(), actualBasePointDtoList.size());
        boolean result;
        for (int i = 0; i < expectBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectBasePointList.get(i), actualBasePointDtoList.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getBySheetThrowsException() {

        List<BasePoint> expectBasePointList = new ArrayList<>();
        Mockito.when(basePointRepository.findBySheet("L-37")).thenReturn(expectBasePointList);

        assertThrows(RuntimeException.class, () -> basePointService.getBySheet("L-37"));

    }

    @Test
    void getByAreaTest() {
        List<BasePoint> expectBasePointList = createDemoPoints();
        AreaDto areaDto = new AreaDto();
        areaDto.setX(0);
        areaDto.setY(0);
        areaDto.setAreaHeight(1000);
        areaDto.setAreaWidth(1000);
        Mockito.when(basePointRepository.findByArea(0, 0, 1000, 1000))
                .thenReturn(expectBasePointList);

        List<BasePointDto> actualBasePointDtoList = basePointService.getByArea(areaDto);

        assertNotNull(actualBasePointDtoList);
        assertEquals(expectBasePointList.size(), actualBasePointDtoList.size());
        boolean result;
        for (int i = 0; i < expectBasePointList.size(); i++) {
            result = pointComparator.compareBasePointBasePointDTO(expectBasePointList.get(i), actualBasePointDtoList.get(i));
            assertTrue(result);
        }
    }

    @Test
    void getByAreaThrowException() {

        List<BasePoint> expectBasePointList = new ArrayList<>();
        AreaDto areaDto = new AreaDto();
        areaDto.setX(0);
        areaDto.setY(0);
        areaDto.setAreaHeight(1000);
        areaDto.setAreaWidth(1000);
        Mockito.when(basePointRepository.findByArea(0, 0, 1000, 1000))
                .thenReturn(expectBasePointList);

        assertThrows(RuntimeException.class, () -> basePointService.getByArea(areaDto));
    }

    @Test
    void createBasePoint() {
        BasePointDto expectBasePointDto = new BasePointDto();
        expectBasePointDto.setName("1001");
        expectBasePointDto.setX(999999999999L);
        expectBasePointDto.setY(888888888888L);
        expectBasePointDto.setZ(9000000000L);
        expectBasePointDto.setSheet("L-37-32");
        expectBasePointDto.setAccuracyClass("2кл");
        expectBasePointDto.setCoordinateSystem("МСК-61-2");
        BasePoint expectBasePoint = basePointMapper.toBasePoint(expectBasePointDto);
        Mockito.when(basePointRepository.saveAndFlush(Mockito.any())).thenReturn(expectBasePoint);

        BasePointDto actualBasePointDto = basePointService.createBasePoint(expectBasePointDto);

        assertNotNull(actualBasePointDto);
        boolean result = pointComparator.compareBasePointDtoBasePointDto(expectBasePointDto, actualBasePointDto);
        assertTrue(result);
    }

    @Test
    void removeById() {
        BasePoint expectBasePoint = new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        );
        Mockito.when(basePointRepository.findById(expectBasePoint.getId())).thenReturn(Optional.of(expectBasePoint));

        BasePointDto actualBasePointDto = basePointService.removeById(expectBasePoint.getId());

        assertNotNull(actualBasePointDto);
        boolean result = pointComparator.compareBasePointBasePointDTO(expectBasePoint, actualBasePointDto);
        assertTrue(result);
    }

    @Test
    void removeByIdThrowsException() {
        Mockito.when(basePointRepository.findById(Mockito.any())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> basePointService.removeById(1));
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
