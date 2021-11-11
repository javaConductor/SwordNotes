package org.swordexplorer.notes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.swordexplorer.notes.model.BibleTopic;

public interface BibleTopicRepository extends JpaRepository<BibleTopic, Long>, JpaSpecificationExecutor<BibleTopic> {

}
