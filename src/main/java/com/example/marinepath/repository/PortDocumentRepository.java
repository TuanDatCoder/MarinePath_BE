package com.example.marinepath.repository;

import com.example.marinepath.entity.Enum.PortDocumentStatusEnum;
import com.example.marinepath.entity.PortDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortDocumentRepository extends JpaRepository<PortDocument, Integer> {
    List<PortDocument> findByStatusNot(PortDocumentStatusEnum portDocumentStatusEnum);
}
