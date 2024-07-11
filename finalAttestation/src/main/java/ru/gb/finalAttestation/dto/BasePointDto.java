package ru.gb.finalAttestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class is used to transfer geodetic point data
 * between application subsystems.
 */
@Data
@NoArgsConstructor
@Schema(name = "Пункт геодезической сети")
public class BasePointDto {

    private long id;

    /**
     * Points name
     */
    @NotNull
    @Schema(name = "Название пункта")
    private String name;

    /**
     * The X coordinate of the point in millimeters
     */
    @NotNull
    @Schema(name = "Координата X, мм.")
    private long x;

    /**
     * The Y coordinate of the point in millimeters
     */
    @NotNull
    @Schema(name = "Координата Y, мм.")
    private long y;

    /**
     * The Z coordinate of the point in millimeters
     */
    @NotNull
    @Schema(name = "Координата Z, мм.")
    private long z;

    /**
     * Nomenclature of a 1:100 000 scale map sheet or project name
     */
    @Schema(name = "Номенклатура листа карты Масштаба 1:100 000 или название объекта")
    private String sheet;

    /**
     * The accuracy class of the point
     */
    @Schema(name = "Класс точности сети пункта")
    private String accuracyClass;

    /**
     * The coordinate system
     */
    @Schema(name = "Название системы координат")
    private String coordinateSystem;
}
