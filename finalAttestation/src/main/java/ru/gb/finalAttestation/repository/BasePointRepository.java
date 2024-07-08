package ru.gb.finalAttestation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.finalAttestation.model.BasePoint;

import java.util.List;

/**
 * This interface provides communication between the database and the project classes
 */
@Repository
public interface BasePointRepository extends JpaRepository<BasePoint, Long> {

    /**
     * Finds base points with specified name fragment
     * @param name String name
     * @return List of base point
     */
    @Query("select b from BasePoint b where b.name like %?1%")
    List<BasePoint> findByName(String name);

    /**
     * Finds base points with specified sheet fragment
     * @param sheet sheet
     * @return List of base point
     */
    @Query("select b from BasePoint b where b.sheet like %?1%")
    List<BasePoint> findBySheet(String sheet);


    /**
     * Finds base points inside the area
     * @param xSouth The x coordinate of the South border of the area
     * @param yWest The y coordinate of the West border of the area
     * @param xNorth The x coordinate of the North border of the area
     * @param yEast The y coordinate of the East border of the area
     * @return List of base point
     */
    @Query("select p from BasePoint p where (p.x between :xSouth and :xNorth) and (p.y between :yWest and :yEast)")
    List<BasePoint> findByArea(@Param("xSouth") long xSouth,
                               @Param("yWest") long yWest,
                               @Param("xNorth") long xNorth,
                               @Param("yEast") long yEast);
}