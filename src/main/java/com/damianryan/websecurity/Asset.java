package com.damianryan.websecurity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nullable;
import java.util.Collection;

interface Asset {

    String name();

    String type();

    String resource();

    String id();

    Collection<String> tags();
}

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
@Accessors(fluent = true)
@Data
class AbstractAsset implements Asset {

    @NonNull String name;

    @NonNull String type;

    @Nullable @Id String id;

    @NonNull String resource;

    @Nullable Collection<String> tags;
}

@Document(collection = "dishes")
@ToString
class Dish extends AbstractAsset {
    public Dish(String name, String id, String resource, Collection<String> tags) {
        super(name, "dish", id, resource, tags);
    }
}

@Document(collection = "ingredients")
@ToString
class Ingredient extends AbstractAsset {
    public Ingredient(String name, String id, String resource, Collection<String> tags) {
        super(name, "ingredient", id, resource, tags);
    }
}
