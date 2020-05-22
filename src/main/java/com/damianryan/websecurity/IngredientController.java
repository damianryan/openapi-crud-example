package com.damianryan.websecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springdoc.data.rest.converters.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${cookery.api.ingredients-path}")
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ApiResponses(value = {@ApiResponse(responseCode = "401",
                                    description = "Unauthenticated",
                                    content = @Content(schema = @Schema(hidden = true)))})
@Tag(name = "ingredients", description = "Create, find, list, update and delete ingredients")
class IngredientController {

    static final String ID = "/{id}";

    AssetCRUDControllerDelegate<Ingredient> delegate;

    public IngredientController(@NonNull IngredientCRUDService ingredientCRUD,
                                @NonNull AuthHelper authHelper) {
        delegate = new AssetCRUDControllerDelegate<>(ingredientCRUD,
                                                     Action.WRITE_INGREDIENT,
                                                     Action.READ_INGREDIENT,
                                                     Action.DELETE_INGREDIENT,
                                                     authHelper);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create ingredient", description = "Creates a new ingredient",
               operationId = "createIngredient")
    @ApiResponses({@ApiResponse(responseCode = "403", description = "Forbidden for specified resource",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "405", description = "Not supported with non-blank id",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "201", description = "Created")})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).WRITE_INGREDIENT)")
    ResponseEntity<Ingredient> createAsset(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New ingredient") @RequestBody Ingredient asset) {
        return delegate.createAsset(asset);
    }

    @GetMapping(path = IngredientController.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find ingredient by id", description = "Returns a single ingredient",
               operationId = "findIngredientById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Ingredient entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_INGREDIENT)")
    ResponseEntity<Ingredient> readAssetById(@Parameter(description = "id of the ingredient being requested") @PathVariable String id) {
        return delegate.readAssetById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List ingredients", description = "Returns all the ingredients you have permission to see",
               operationId = "listIngredients")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Ingredients entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PageableAsQueryParam
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_INGREDIENT)")
    Page<Ingredient> listAssets(Pageable pageable) {
        return delegate.listAssets(pageable);
    }

    @PutMapping(path = IngredientController.ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update ingredient by id", description = "Updates an existing ingredient",
               operationId = "updateIngredientById")
    @ApiResponses({@ApiResponse(responseCode = "403", description = "Forbidden for specified resource",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "405", description = "Not supported with blank id",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "200", description = "Ok")})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).WRITE_INGREDIENT)")
    ResponseEntity<Ingredient> updateAssetById(@Parameter(description = "id of the ingredient being updated") @PathVariable String id,
                                               @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated ingredient") @RequestBody Ingredient asset) {
        return delegate.updateAssetById(id, asset);
    }

    @DeleteMapping(path = IngredientController.ID)
    @Operation(summary = "Delete ingredient by id", description = "Deletes an existing ingredient",
               operationId = "deleteIngredientById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "204", description = "Deleted",
                                content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).DELETE_INGREDIENT)")
    ResponseEntity<Void> deleteByAssetId(@Parameter(description = "id of the ingredient being deleted") @PathVariable String id) {
        return delegate.deleteByAssetId(id);
    }
}
