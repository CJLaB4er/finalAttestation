package ru.gb.finalAttestation.mapper;

import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.model.BasePoint;

import java.util.List;

/**
 * This interface defines a set of methods for converting between
 * objects and entities
 */
public interface BasePointMapper {

    /**
     * Converts BasePointDto instance to BasePoint instance
     * @param basePointDto BasePointDto instance
     * @return BasePoint instance
     */
    BasePoint toBasePoint(BasePointDto basePointDto);

    /**
     * Converts BasePoint instance to BasePointDto instance
     * @param basePoint BasePointDto instance
     * @return BasePointDto instance
     */
    BasePointDto toBasePointDto(BasePoint basePoint);

    /**
     * Converts BasePoint instance list to BasePointDto instance list
     * @param basePoints BasePointDto instance list
     * @return BasePointDto instance list
     */
    List<BasePointDto> toListBasePointDto(List<BasePoint> basePoints);

    /**
     * Converts BasePointDto instance list to BasePoint instance list
     * @param basePointsDto BasePointDto instance list
     * @return BasePoint instance list
     */
    List<BasePoint> toListBasePoint(List<BasePointDto> basePointsDto);
}
