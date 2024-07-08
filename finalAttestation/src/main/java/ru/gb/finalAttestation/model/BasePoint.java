package ru.gb.finalAttestation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class provides the basic entity of the project
 */
@Entity
@Data
@Table(name = "points")
@NoArgsConstructor
public class BasePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Points name
     */
    @Column(name = "name")
    private String name;

    /**
     * The X coordinate of the point in millimeters
     */
    @Column(name = "x")
    private long x;

    /**
     * The Y coordinate of the point in millimeters
     */
    @Column(name = "y")
    private long y;

    /**
     * The Z coordinate of the point in millimeters
     */
    @Column(name = "z")
    private long z;

    /**
     * Nomenclature of a 1:100 000 scale map sheet
     */
    @Column(name = "sheet")
    private String sheet;

    /**
     * The accuracy class of the point
     */
    @Column(name = "accuracy_class")
    private String accuracyClass;

    /**
     * The coordinate system
     */
    @Column(name = "coordinate_system")
    private String coordinateSystem;

    public BasePoint(String name,
                     long x,
                     long y,
                     long z,
                     String sheet,
                     String pointType,
                     String coordinateSystem) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sheet = sheet;
        this.accuracyClass = pointType;
        this.coordinateSystem = coordinateSystem;
    }
}
