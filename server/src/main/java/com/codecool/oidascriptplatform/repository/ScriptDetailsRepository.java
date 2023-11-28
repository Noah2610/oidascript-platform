package com.codecool.oidascriptplatform.repository;

import com.codecool.oidascriptplatform.model.ScriptDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptDetailsRepository extends CrudRepository<ScriptDetails, Long> {
    @Query(value = """
        select script_details.* from script_details
        where script_details.user_id in (
            select id from users
            where users.username = ?1
            limit 1
        )
    """, nativeQuery = true)
    List<ScriptDetails> filterByUsername(String username);

    @Query(value = """
        select script_details.* from script_details
        where script_details.id = ?1
        and script_details.user_id in (
            select id from users
            where users.username = ?2
            limit 1
        )
    """, nativeQuery = true)
    Optional<ScriptDetails> findByIdAndUsername(Long id, String username);
}
