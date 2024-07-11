package ru.gb.finalAttestation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.gb.finalAttestation.dto.AreaDto;
import ru.gb.finalAttestation.dto.BasePointDto;
import ru.gb.finalAttestation.service.BasePointService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class provides methods that handle incoming HTTP requests
 */
@Slf4j
@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class BasePointController {

    private final BasePointService basePointService;

    /**
     * Gets all base points from database
     * @return base points list
     */
    @Operation(summary = "gets all the basic geodetic points", description =
            "Загружает данные обо всех геодезических пунктах, хранящихся в базе данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BasePointDto>> getAllBasePoints() {
        return ResponseEntity.ok().body(basePointService.getAll());
    }

    /**
     * Gets base point by id
     * @param id point id
     * @return BasePointDto instance
     */
    @Operation(summary = "get base points by id",
            description =
                    "Загружает данные о геодеческом пункте с указанным идентификатором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public ResponseEntity<BasePointDto> getBasePointById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(basePointService.getById(id));
        } catch (RuntimeException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Gets base point by name
     * @param name The name of the desired point
     * @return BasePointDto instance
     */
    @Operation(summary = "get base points by name",
            description =
                    "Загружает список пунктов, название которых содержит указанный фрагмент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("name/{name}")
    public ResponseEntity<List<BasePointDto>> getBasePointByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(basePointService.getByName(name));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Gets base point by sheet
     * @param sheet The sheet of the desired point
     * @return List of BasePointDto instance
     */
    @Operation(summary = "get base points by sheet",
            description =
                    "Загружает список пунктов, название листа или объекта которых содержит указанный фрагмент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("sheet/{sheet}")
    public ResponseEntity<List<BasePointDto>> getBasePointBySheet(@PathVariable String sheet) {
        try {
            return ResponseEntity.ok().body(basePointService.getBySheet(sheet));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Removes base point by id
     * @param id point id
     * @return Removed base point
     */
    @Operation(summary = "removes base point by id", description =
            "Удаляет запись с указанным идентификатором из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<BasePointDto> removeBasePointById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(basePointService.removeById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Creates a new base point and adds it to the database
     * @param basePointDto BasePointDto instance
     * @return BasePointDto instance
     */
    @Operation(summary = "creates new base point", description =
            "Создаёт в базе данных новую запись с информацией о геодезическом пункте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BasePointDto> createBasePoint(@RequestBody BasePointDto basePointDto) {
        return new ResponseEntity<>(basePointService.createBasePoint(basePointDto),HttpStatus.CREATED);
    }

    /**
     * Gets list of base points inside the specified area
     * @param areaDto AreaDto instance
     * @return List of BasePointDto instance
     */
    @Operation(summary =
            "Gets list of base points inside the specified area",
            description =
                    "Загружает список пунктов, внутри указанной области")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("area")
    public ResponseEntity<List<BasePointDto>> getByArea(@RequestBody AreaDto areaDto) {
        try {
            return ResponseEntity.ok().body(basePointService.getByArea(areaDto));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Updates base point with desired id
     * @param id desired id
     * @param basePointDto BasePointDto instance
     * @return BasePointDto instance
     */
    @Operation(summary =
            "Updates base point with desired id",
            description =
                    "Обновляет информацию о геодезическом пункте с указанным идентификатором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    public ResponseEntity<BasePointDto> updateBasePoint(@PathVariable long id, @RequestBody BasePointDto basePointDto) {
        try {
            return ResponseEntity.ok().body(basePointService.updatePoint(id, basePointDto));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
