package org.cm.doan2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Comment extends JpaRepository<org.cm.doan2.model.Comment, Integer> {
}
