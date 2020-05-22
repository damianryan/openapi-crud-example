package com.damianryan.websecurity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

interface AssetCRUDService<A extends Asset> {

    // returns persisted asset (with id)
    A createAsset(A asset);

    // returns empty optional if not found, asset if found
    Optional<A> readAssetByIdAndResourceIn(String id, Set<String> resources);

    // returns page of assets, may be empty
    Page<A> listAssetsByResourceIn(Pageable pageable, Set<String> resources);

    // returns page of assets, may be empty
    Page<A> listAssetsByTagsIncludingAndResourcesIn(Pageable pageable, Collection<String> tags, Set<String> resources);

    // returns empty optional if not found, updated asset if found and updated
    Optional<A> updateAssetByIdAndResourceIn(String id, A asset, Set<String> resources);

    // returns false if not found, contains true if found and deleted
    boolean deleteAssetByIdAndResourceIn(String id, Set<String> resources);
}

