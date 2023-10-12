package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.response.CategoryGroupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupController.ROUTE_CATEGORY_GROUP;

@RestController
@RequestMapping(ROUTE_CATEGORY_GROUP)
public class CategoryGroupController {

    @Autowired
    private CategoryGroupService service;
    public static final String ROUTE_CATEGORY_GROUP = "/v1/category-group";

    public CategoryGroupResponseDto mapEntityToDto(CategoryGroupEntity entity) {
        CategoryGroupResponseDto dto = new CategoryGroupResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCode(),
                entity.getParentCategoryGroupEntity() == null ? null : entity.getParentCategoryGroupEntity().getId(),
                entity.getVisibility(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedBy(),
                entity.getDeletedDate()
        );
        return dto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('CATEGORY_GROUP_GET_ALL')")
    @Operation(summary = "Gets Categories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<CategoryGroupResponseDto> findAll (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid CategoryGroupParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<CategoryGroupEntity> entities = service.findAll(paging, request);
        List<CategoryGroupResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('CATEGORY_GROUP_GET_ONE')")
    @Operation(summary = "Gets one Category", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<CategoryGroupResponseDto> findOne(@PathVariable UUID id) {
        CategoryGroupEntity entity = service.findById(id);
        CategoryGroupResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<CategoryGroupResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CATEGORY_GROUP_CREATE')")
    @Operation(summary = "Creates an Category Group", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<CategoryGroupResponseDto> create(@RequestBody @Valid CategoryGroupCreateDto request) {
        CategoryGroupEntity entity = service.create(request);
        CategoryGroupResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('CATEGORY_GROUP_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Category Group", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Category no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<CategoryGroupResponseDto> update(@PathVariable UUID id, @Valid @RequestBody CategoryGroupUpdateDto request) {
        CategoryGroupEntity entity = service.update(id, request);
        CategoryGroupResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('CATEGORY_GROUP_DELETE')")
    @Operation(summary = "Deletes an Category Group", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding social action"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }


}
