package com.example.marinepath.repository;

import com.example.marinepath.entity.PortDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

public interface PortDocumentRepository extends JpaRepository<PortDocument, Integer> {
}
