package me.drunkcat.jsonschemamanagerserver.schema.domain.repository;

import me.drunkcat.jsonschemamanagerserver.schema.domain.entity.Schema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaRepository extends JpaRepository<Schema, Long> {
}
