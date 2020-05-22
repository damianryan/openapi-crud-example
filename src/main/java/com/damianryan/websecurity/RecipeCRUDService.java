package com.damianryan.websecurity;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class RecipeCRUDService implements AssetCRUDService<Recipe> {

    @NonNull RecipeRepository repo;

    @Override
    public Recipe createAsset(Recipe asset) {
        return repo.save(asset);
    }

    @Override
    public Optional<Recipe> readAssetByIdAndResourceIn(String id, Set<String> resources) {
        return repo.findByIdAndResourceIn(id, resources);
    }

    @Override
    public Page<Recipe> listAssetsByResourceIn(Pageable pageable, Set<String> resources) {
        return repo.findAllByResourceIn(pageable, resources);
    }

    @Override
    public Page<Recipe> listAssetsByTagsIncludingAndResourcesIn(Pageable pageable,
                                                                Collection<String> tags,
                                                                Set<String> resources) {
        return repo.findAllByResourceIn(pageable, resources); // tags ignored for now
    }

    @Override
    public Optional<Recipe> updateAssetByIdAndResourceIn(String id, Recipe asset, Set<String> resources) {
        Optional<Recipe> found = repo.findByIdAndResourceIn(id, resources);
        if (found.isPresent()) {
           found = Optional.of(repo.save(asset));
        }
        return found;
    }

    @Override
    public boolean deleteAssetByIdAndResourceIn(String id, Set<String> resources) {
        Optional<Recipe> found = repo.findByIdAndResourceIn(id, resources);
        if (found.isPresent()) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

@Repository
interface RecipeRepository extends MongoRepository<Recipe, String> {

    Optional<Recipe> findByIdAndResourceIn(String id, Set<String> resources);

    Page<Recipe> findAllByResourceIn(Pageable pageable, Set<String> resources);
}
