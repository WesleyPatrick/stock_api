package io.github.WesleyPatrick.stock_api.web.controller.commons;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface BaseController {
    default URI getBaseUri(UUID uuid) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uuid).toUri();
    }
}
