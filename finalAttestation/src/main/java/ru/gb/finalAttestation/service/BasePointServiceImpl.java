package ru.gb.finalAttestation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.gb.finalAttestation.dto.AreaDto;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.mapper.BasePointMapper;
import ru.gb.finalAttestation.model.BasePoint;
import ru.gb.finalAttestation.repository.BasePointRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class implements the methods of the BasePointService interface,
 * representing the business logic of the project
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BasePointServiceImpl implements BasePointService{
    private static final String NOT_FOUND_POINT_MESSAGE = "Couldn't find the point with the ";

    private final BasePointRepository basePointRepository;
    private final BasePointMapper basePointMapper;

    @EventListener(ContextRefreshedEvent.class)
    public void onCreatedDatabase() {

        basePointRepository.save(new BasePoint(
                "Утренний",
                5501650050L,
                75913715790L,
                1123250,
                "L-37-09",
                "2кл",
                "1942г"
        ));
        basePointRepository.save(new BasePoint(
                "Равнинная",
                5212365400L,
                7575821450L,
                1159780L,
                "L-37-09",
                "3кл",
                "1942г"
        ));
        basePointRepository.save(new BasePoint(
                "Шахта98",
                5232554480L,
                7152770570L,
                456900L,
                "L-37-09",
                "3кл",
                "1942г"
        ));
        basePointRepository.save(new BasePoint(
                "Лисицын",
                4235210786L,
                2145989526L,
                17480L,
                "L-37-32",
                "4кл",
                "МСК-61-2"
        ));
        basePointRepository.save(new BasePoint(
                "3102",
                428461170L,
                2248594713L,
                48505L,
                "1р",
                "L-37-32",
                "МСК-61-2"
        ));
        basePointRepository.save(new BasePoint(
                "2901",
                473688734L,
                2244514037L,
                71237L,
                "L-37-32",
                "2р",
                "МСК-61-2"
        ));
        basePointRepository.save(new BasePoint(
                "2901",
                471238734L,
                1244454037L,
                1232456L,
                "L-37-31",
                "2р",
                "МСК-61-1"
        ));

    }

    /**
     * Gets all base points from database
     * @return list of base points
     */
    @Override
    public List<BasePointDto> getAll() {
        return basePointMapper.toListBasePointDto(basePointRepository.findAll());
    }

    /**
     * Gets base point by id
     * @param id long id
     * @return BasePointDto instance
     */
    @Override
    public BasePointDto getById(long id) {
        BasePoint basePoint = basePointRepository.findById(id).orElse(null);
        if (basePoint != null) {
            return basePointMapper.toBasePointDto(basePoint);
        } else {
            throw new NoSuchElementException(NOT_FOUND_POINT_MESSAGE + "id = " + id);
        }
    }

    /**
     * Finds base points by name
     * @param name String name
     * @return list of BasePointDto instance
     */
    @Override
    public List<BasePointDto> getByName(String name) {
        List<BasePoint> basePoints = basePointRepository.findByName(name);
        if (basePoints.isEmpty()) {
            throw new RuntimeException(NOT_FOUND_POINT_MESSAGE + "name = " + name);
        }
        return basePointMapper.toListBasePointDto(basePoints);
    }

    @Override
    public List<BasePointDto> getBySheet(String sheet) {
        List<BasePoint> basePoints = basePointRepository.findBySheet(sheet);
        if (basePoints.isEmpty()) {
            throw new RuntimeException(NOT_FOUND_POINT_MESSAGE + "sheet = " + sheet);
        }

        return basePointMapper.toListBasePointDto(basePoints);
    }


    @Override
    public List<BasePointDto> getByArea(AreaDto areaDto) {
        long xNorthEast = areaDto.getX() + areaDto.getAreaHeight();
        long yNorthEast = areaDto.getY() + areaDto.getAreaWidth();
        List<BasePoint> basePoints = basePointRepository
                .findByArea(areaDto.getX(),
                        areaDto.getY(),
                        xNorthEast,
                        yNorthEast);
        if (basePoints.isEmpty()) {
            throw new RuntimeException("No points were found inside the specified area");
        }
        return basePointMapper.toListBasePointDto(basePoints);
    }

    /**
     * Adds new base point to database
     * @param basePointDto BasePointDto instance
     * @return BasePointDto instance
     */
    @Override
    @Transactional
    public BasePointDto createBasePoint(BasePointDto basePointDto) {
        BasePoint basePoint = basePointMapper.toBasePoint(basePointDto);
        return basePointMapper.toBasePointDto(basePointRepository.saveAndFlush(basePoint));
    }

    /**
     * Removes base point from database by id
     * @param id long id
     * @return BasePoint instance
     */
    @Override
    @Transactional
    public BasePointDto removeById(long id) {
        BasePoint basePoint = basePointRepository.findById(id).orElse(null);
        if (basePoint == null) {
            throw new RuntimeException(NOT_FOUND_POINT_MESSAGE + "id = " + id);
        }
        basePointRepository.deleteById(id);
        return basePointMapper.toBasePointDto(basePoint);
    }


    @Override
    @Transactional
    public BasePointDto updatePoint(long id, BasePointDto basePointDto) {
        BasePoint basePoint = basePointRepository.findById(id).orElse(null);
        if (basePoint == null) {
            throw new RuntimeException(NOT_FOUND_POINT_MESSAGE + "id = " + id);
        }
        basePoint.setName(basePointDto.getName());
        basePoint.setX(basePointDto.getX());
        basePoint.setY(basePointDto.getY());
        basePoint.setZ(basePointDto.getZ());
        basePoint.setSheet(basePointDto.getSheet());
        basePoint.setAccuracyClass(basePointDto.getAccuracyClass());
        basePoint.setCoordinateSystem(basePointDto.getCoordinateSystem());

        return basePointMapper.toBasePointDto(basePointRepository.saveAndFlush(basePoint));
    }

}
