package mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.mapper.BasePointMapper;
import ru.gb.finalAttestation.mapper.BasePointMapperImpl;
import ru.gb.finalAttestation.model.BasePoint;
import ru.gb.finalAttestation.service.PointComparator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = {
        BasePointMapperImpl.class,
        PointComparator.class
})
class BasePointMapperImplTest {

    Random random = new Random();

    @Autowired
    BasePointMapper basePointMapper;

    @Autowired
    PointComparator pointComparator;

    @Test
    void toBasePointTest() {
        BasePointDto expectBasePointDto = new BasePointDto();
        expectBasePointDto.setName("1001");
        expectBasePointDto.setX(999999999999L);
        expectBasePointDto.setY(888888888888L);
        expectBasePointDto.setZ(9000000000L);
        expectBasePointDto.setSheet("L-37-32");
        expectBasePointDto.setAccuracyClass("2кл");
        expectBasePointDto.setCoordinateSystem("МСК-61-2");

        BasePoint actualBasePoint = basePointMapper.toBasePoint(expectBasePointDto);

        assertNotNull(actualBasePoint);
        assertTrue(pointComparator.compareBasePointDtoBasePoint(expectBasePointDto, actualBasePoint));
    }

    @Test
    void toBasePointDtoTest() {
        BasePoint expectBasePoint = new BasePoint(
                "Горная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        );

        BasePointDto actualBasePointDto = basePointMapper.toBasePointDto(expectBasePoint);

        assertNotNull(actualBasePointDto);
        assertTrue(pointComparator.compareBasePointBasePointDTO(expectBasePoint, actualBasePointDto));
    }

    @Test
    void toListBasePointDtoTest() {
        List<BasePoint> expectBasePointList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expectBasePointList.add(new BasePoint(
                    "Point_N_" + random.nextInt(100),
                    random.nextLong(1000000000),
                    random.nextLong(1000000000),
                    random.nextLong(1000000),
                    "L-37-" + random.nextInt(144),
                    random.nextInt(4) + "кл",
                    String.valueOf(random.nextInt(10000))
            ));
        }

        List<BasePointDto> actualBasePointDtoList = basePointMapper.toListBasePointDto(expectBasePointList);

        assertNotNull(actualBasePointDtoList);
        assertEquals(expectBasePointList.size(), actualBasePointDtoList.size());
        for (int i = 0; i < expectBasePointList.size(); i++) {
            boolean result = pointComparator.compareBasePointBasePointDTO(expectBasePointList.get(i),
                    actualBasePointDtoList.get(i));
            assertTrue(result);
        }

    }

    @Test
    void toListBasePointTest() {
        List<BasePointDto> expectBasePointDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BasePointDto basePointDto = new BasePointDto();
            basePointDto.setName("Point_N_" + random.nextInt(100));
            basePointDto.setX(random.nextLong(1000000000));
            basePointDto.setY(random.nextLong(1000000000));
            basePointDto.setZ(random.nextLong(1000000));
            basePointDto.setSheet("L-37-" + random.nextInt(144));
            basePointDto.setAccuracyClass(random.nextInt(4) + "кл");
            basePointDto.setCoordinateSystem(String.valueOf(random.nextInt(10000)));
            expectBasePointDtoList.add(basePointDto);
        }

        List<BasePoint> actualBasePointList = basePointMapper.toListBasePoint(expectBasePointDtoList);

        assertNotNull(actualBasePointList);
        assertEquals(expectBasePointDtoList.size(), actualBasePointList.size());
        for (int i = 0; i < expectBasePointDtoList.size(); i++) {
            boolean result = pointComparator.compareBasePointDtoBasePoint(expectBasePointDtoList.get(i), actualBasePointList.get(i));
            assertTrue(result);
        }

    }
}
