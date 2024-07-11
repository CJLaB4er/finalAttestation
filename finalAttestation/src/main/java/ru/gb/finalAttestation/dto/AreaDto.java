package ru.gb.finalAttestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is used to transfer search area data
 * between application subsystems.
 */
@Data
@NoArgsConstructor
@Schema(name = "Прямоугольная область поиска")
public class AreaDto {

    /**
     * X coordinate of the southwest corner of the search area
     */
    @NotNull
    @Schema(name = "Юго-западный угол X, мм.")
    private long x;

    /**
     * Y coordinate of the southwest corner of the search area
     */
    @NotNull
    @Schema(name = "Юго-западный угол Y, мм.")
    private long y;

    /**
     * Height of the search area
     */
    @NotNull
    @Schema(name = "Высота области (протяжённость с юга на север), мм.")
    private long areaHeight;

    /**
     * Width of the search area
     */
    @NotNull
    @Schema(name = "Ширина области (протяжённость с востока на запад), мм.")
    private long areaWidth;
}
