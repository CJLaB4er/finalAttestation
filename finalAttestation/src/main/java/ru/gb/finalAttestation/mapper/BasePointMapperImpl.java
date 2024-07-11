package ru.gb.finalAttestation.mapper;

import org.springframework.stereotype.Component;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.model.BasePoint;

import java.util.List;

/**
 * This class implements the methods defined in the BasePointMapper interface,
 * converting Dto and entities
 */
@Component
public class BasePointMapperImpl implements BasePointMapper{

    /**
     * Converts BasePointDto instance to BasePoint instance
     * @param basePointDto BasePointDto instance
     * @return BasePoint instance
     */
    public BasePoint toBasePoint(BasePointDto basePointDto) {

        BasePoint basePoint = new BasePoint();

        basePoint.setName(basePointDto.getName());
        basePoint.setX(basePointDto.getX());
        basePoint.setY(basePointDto.getY());
        basePoint.setZ(basePointDto.getZ());
        basePoint.setSheet(basePointDto.getSheet());
        basePoint.setAccuracyClass(basePointDto.getAccuracyClass());
        basePoint.setCoordinateSystem(basePointDto.getCoordinateSystem());

        return basePoint;
    }

    /**
     * Converts BasePoint instance to BasePointDto instance
     * @param basePoint BasePointDto instance
     * @return BasePointDto instance
     */
    public BasePointDto toBasePointDto(BasePoint basePoint) {

        BasePointDto basePointDto = new BasePointDto();

        basePointDto.setId(basePoint.getId());
        basePointDto.setName(basePoint.getName());
        basePointDto.setX(basePoint.getX());
        basePointDto.setY(basePoint.getY());
        basePointDto.setZ(basePoint.getZ());
        basePointDto.setSheet(basePoint.getSheet());
        basePointDto.setAccuracyClass(basePoint.getAccuracyClass());
        basePointDto.setCoordinateSystem(basePoint.getCoordinateSystem());

        return basePointDto;
    }

    /**
     * Converts BasePoint instance list to BasePointDto instance list
     * @param basePoints BasePointDto instance list
     * @return BasePointDto instance list
     */
    public List<BasePointDto> toListBasePointDto(List<BasePoint> basePoints) {
        return basePoints.stream().map(this::toBasePointDto).toList();
    }

    /**
     * Converts BasePointDto instance list to BasePoint instance list
     * @param basePointsDto BasePointDto instance list
     * @return BasePoint instance list
     */
    public List<BasePoint> toListBasePoint(List<BasePointDto> basePointsDto) {
        return basePointsDto.stream().map(this::toBasePoint).toList();
    }
}
