package com.codecool.oidascriptplatform.repository;

import com.codecool.oidascriptplatform.model.ScriptBody;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptBodyRepository extends CrudRepository<ScriptBody, String> {
}
