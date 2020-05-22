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
class DishCRUDService implements AssetCRUDService<Dish> {

    @Override
    public Dish createAsset(Dish asset) {
        return asset;
    }

    @Override
    public Optional<Dish> readAssetByIdAndResourceIn(String id, Set<String> resources) {
        return Optional.of(new Dish("spaghetti bolognese", "123", "italian", Collections.singleton("pasta")));
    }

    @Override
    public Page<Dish> listAssetsByResourceIn(Pageable pageable, Set<String> resources) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public Page<Dish> listAssetsByTagsIncludingAndResourcesIn(Pageable pageable,
                                                              Collection<String> tags,
                                                              Set<String> resources) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public Optional<Dish> updateAssetByIdAndResourceIn(String id, Dish asset, Set<String> resources) {
        return Optional.of(asset);
    }

    @Override
    public boolean deleteAssetByIdAndResourceIn(String id, Set<String> resources) {
        return true;
    }
}
