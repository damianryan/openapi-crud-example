package com.damianryan.websecurity;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AssetCRUDControllerDelegate<A extends Asset> {

    @NonNull AssetCRUDService<A> assetCRUDService;
    @Nullable Action writeAction;
    @Nullable Action readAction;
    @Nullable Action deleteAction;
    @NonNull AuthHelper authHelper;

    ResponseEntity<A> createAsset(A asset) {
        Set<String> resources = authHelper.resourcesForAction(writeAction);
        if (!resources.contains(asset.resource())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (null != asset.id()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        return ResponseEntity.ok(assetCRUDService.createAsset(asset));
    }

    ResponseEntity<A> updateAssetById(String id, A asset) {
        Set<String> resources = authHelper.resourcesForAction(writeAction);
        if (!resources.contains(asset.resource())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (null == asset.id()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        // assumes that if a user has write permission for a resource, that they also have read permission
        return ResponseEntity.of(assetCRUDService.updateAssetByIdAndResourceIn(id, asset, resources));
    }

    ResponseEntity<Void> deleteByAssetId(String id) {
        Set<String> resources = authHelper.resourcesForAction(deleteAction);
        boolean deleted = assetCRUDService.deleteAssetByIdAndResourceIn(id, resources);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    ResponseEntity<A> readAssetById(String id) {
        Set<String> resources = authHelper.resourcesForAction(readAction);
        return ResponseEntity.of(assetCRUDService.readAssetByIdAndResourceIn(id, resources));
    }

    Page<A> listAssets(Pageable pageable) {
        Set<String> resources = authHelper.resourcesForAction(readAction);
        return assetCRUDService.listAssetsByResourceIn(pageable, resources);
    }

    Page<A> listTaggedAssets(Pageable pageable, Collection<String> tags) {
        Set<String> resources = authHelper.resourcesForAction(readAction);
        return assetCRUDService.listAssetsByTagsIncludingAndResourcesIn(pageable, tags, resources);
    }
}

