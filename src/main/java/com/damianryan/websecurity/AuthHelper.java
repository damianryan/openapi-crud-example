package com.damianryan.websecurity;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Set;

interface AuthHelper {

    Set<String> resourcesForAction(Action action);
}

@Component
class BasicAuthHelper implements AuthHelper {

    @Override
    public Set<String> resourcesForAction(Action action) {
        return Sets.newHashSet("italian", "french", "indian");
    }
}
