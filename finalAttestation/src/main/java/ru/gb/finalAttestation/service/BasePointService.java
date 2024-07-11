package ru.gb.finalAttestation.service;

/**
 * This interface defines the main methods of the business logic of the project
 * @author Nizovkin A.V.
 */
public interface BasePointService {

    /**
     * Gets all base points from database
     * @return list of base points
     */
    List<BasePointDto> getAll();

    /**
     * Gets base point by id
     * @param id long id
     * @return BasePointDto instance
     */
    BasePointDto getById(long id);

    /**
     * Finds base points by name
     * @param name String name
     * @return list of BasePointDto instance
     */
    List<BasePointDto> getByName(String name);

    /**
     * Finds base points by sheet
     * @param sheet String name
     * @return list of BasePointDto instance
     */
    List<BasePointDto> getBySheet(String sheet);

    /**
     * Gets list of base points inside the specified area
     * @param areaDto AreaDto instance
     * @return List of BasePointDto instance
     */
    List<BasePointDto> getByArea(AreaDto areaDto);

    /**
     * Adds new base point to database
     * @param basePointDto BasePointDto instance
     * @return BasePointDto instance
     */
    BasePointDto createBasePoint(BasePointDto basePointDto);

    /**
     * Removes base point from database by id
     * @param id long id
     * @return BasePoint instance
     */
    BasePointDto removeById(long id);

    /**
     * Updates base point by id
     * @param id long id
     * @param basePointDto BasePointDto instance
     * @return BasePointDto instance
     */
    BasePointDto updatePoint(long id, BasePointDto basePointDto);


}
