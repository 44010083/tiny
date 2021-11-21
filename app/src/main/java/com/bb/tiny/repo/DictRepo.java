package com.bb.tiny.repo;

import com.bb.tiny.model.entity.Dict;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface DictRepo extends CrudRepository<Dict, String> {
    Optional<Dict> findByValue(String value);

    Optional<Dict> findByName(String name);
}
