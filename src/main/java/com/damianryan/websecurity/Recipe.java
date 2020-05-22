package com.damianryan.websecurity;

import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "recipes")
@ToString
public class Recipe extends AbstractAsset {

    public Recipe(String name, String id, String resource, Collection<String> tags) {
        super(name, "recipe", id, resource, tags);
    }
}
