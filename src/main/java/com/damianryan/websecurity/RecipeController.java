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
import java.util.Collection;

@RestController
@RequestMapping("${cookery.api.recipes-path}")
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ApiResponses(value = {@ApiResponse(responseCode = "401",
                                    description = "Unauthenticated",
                                    content = @Content(schema = @Schema(hidden = true)))})
@Tag(name = "recipes", description = "Create, find, list, update and delete recipes")
class RecipeController {

    static final String ID = "/{id}";

    AssetCRUDControllerDelegate<Recipe> delegate;

    public RecipeController(@NonNull RecipeCRUDService recipeCRUD,
                            @NonNull AuthHelper authHelper) {
        delegate = new AssetCRUDControllerDelegate<>(recipeCRUD,
                                                     Action.WRITE_RECIPE,
                                                     Action.READ_RECIPE,
                                                     Action.DELETE_RECIPE,
                                                     authHelper);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary= "Create recipe", description = "Creates a new recipe",
               operationId = "createRecipe")
    @ApiResponses({@ApiResponse(responseCode = "403", description = "Forbidden for specified resource",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "405", description = "Not supported with non-blank id",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "201", description = "Created")})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).WRITE_RECIPE)")
    ResponseEntity<Recipe> createAsset(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New recipe") @RequestBody Recipe asset) {
        return delegate.createAsset(asset);
    }

    @GetMapping(path = RecipeController.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary= "Find recipe by id", description = "Returns a single recipe",
               operationId = "findRecipeById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Recipe entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_RECIPE)")
    ResponseEntity<Recipe> readAssetById(@Parameter(description = "id of the recipe being requested") @PathVariable String id) {
        return delegate.readAssetById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary= "List tagged recipes", description = "Returns all the recipes you have permission to see that have the specified tags",
               operationId = "listTaggedRecipes")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Recipes entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PageableAsQueryParam
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_RECIPE)")
    Page<Recipe> listTaggedAssets(Pageable pageable, @Parameter(description = "one or more tags", required = false) @RequestParam Collection<String> tags) {
        return delegate.listTaggedAssets(pageable, tags);
    }

    @PutMapping(path = RecipeController.ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary= "Update recipe by id", description = "Updates an existing recipe",
               operationId = "updateRecipeById")
    @ApiResponses({@ApiResponse(responseCode = "403", description = "Forbidden for specified resource",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "405", description = "Not supported with blank id",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "200", description = "Ok")})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).WRITE_RECIPE)")
    ResponseEntity<Recipe> updateAssetById(@Parameter(description = "id of the recipe being updated") @PathVariable String id, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated recipe") @RequestBody Recipe asset) {
        return delegate.updateAssetById(id, asset);
    }

    @DeleteMapping(path = RecipeController.ID)
    @Operation(summary= "Delete recipe by id", description = "Deletes an existing recipe",
               operationId = "deleteRecipeById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "204", description = "Deleted",
                                content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).DELETE_RECIPE)")
    ResponseEntity<Void> deleteByAssetId(@Parameter(description = "id of the recipe being deleted") @PathVariable String id) {
        return delegate.deleteByAssetId(id);
    }
}
