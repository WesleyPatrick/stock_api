package io.github.WesleyPatrick.stock_api.infra.repository;

import io.github.WesleyPatrick.stock_api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
