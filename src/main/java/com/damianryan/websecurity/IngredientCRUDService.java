package com.damianryan.websecurity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Component
class IngredientCRUDService implements AssetCRUDService<Ingredient> {

    @Override
    public Ingredient createAsset(Ingredient asset) {
        return asset;
    }

    @Override
    public Optional<Ingredient> readAssetByIdAndResourceIn(String id, Set<String> resources) {
        return Optional.of(new Ingredient("organo", "123", "italian", Collections.singleton("herb")));
    }

    @Override
    public Page<Ingredient> listAssetsByResourceIn(Pageable pageable, Set<String> resources) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public Page<Ingredient> listAssetsByTagsIncludingAndResourcesIn(Pageable pageable,
                                                                    Collection<String> tags,
                                                                    Set<String> resources) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public Optional<Ingredient> updateAssetByIdAndResourceIn(String id, Ingredient asset, Set<String> resources) {
        return Optional.of(asset);
    }

    @Override
    public boolean deleteAssetByIdAndResourceIn(String id, Set<String> resources) {
        return true;
    }
}
