package com.damianryan.websecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("${cookery.api.dishes-path}")
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ApiResponses(value = {@ApiResponse(responseCode = "401",
                                    description = "Unauthenticated",
                                    content = @Content(schema = @Schema(hidden = true)))})
@SecurityRequirement(name = "cookery_auth")
@Tag(name = "dishes", description = "Find, list, and cancel dishes")
class DishController {

    static final String ID = "/{id}";

    AssetCRUDControllerDelegate<Dish> delegate;

    public DishController(@NonNull DishCRUDService dishCRUD,
                          @NonNull AuthHelper authHelper) {
        delegate = new AssetCRUDControllerDelegate<>(dishCRUD,
                                                     null,
                                                     Action.READ_DISH,
                                                     Action.CANCEL_DISH,
                                                     authHelper);
    }

    @GetMapping(path = DishController.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find dish by id", description = "Returns a single dish",
               operationId = "findDishById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Dish entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_DISH)")
    ResponseEntity<Dish> readAssetById(@Parameter(description = "id of the dish being requested") @PathVariable String id) {
        return delegate.readAssetById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List dishes", description = "Returns all the dishes you have permission to see",
               operationId = "listDishes")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ok",
                                headers = {@Header(name = "ETag", description = "Dishes entity tag",
                                                   schema = @Schema(type = "string"))})})
    @PageableAsQueryParam
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).READ_DISH)")
    Page<Dish> listAssets(Pageable pageable) {
        return delegate.listAssets(pageable);
    }

    @DeleteMapping(path = DishController.ID)
    @Operation(summary = "Cancel dish by id", description = "Cancels a dish that is cooking",
               operationId = "cancelDishById")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found",
                                content = @Content(schema = @Schema(hidden = true))),
                   @ApiResponse(responseCode = "204", description = "Deleted",
                                content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasPermission(null, 'GrantedAuthority', T(com.damianryan.websecurity.Action).CANCEL_DISH)")
    ResponseEntity<Void> cancelByAssetId(@Parameter(description = "id of the dish being cancelled") @PathVariable String id) {
        return delegate.deleteByAssetId(id);
    }
}
