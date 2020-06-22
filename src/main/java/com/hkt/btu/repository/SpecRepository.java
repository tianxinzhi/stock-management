package com.hkt.btu.repository;

import com.hkt.btu.domain.Spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Spec entity.
 */
@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {

    @Query(value = "select distinct spec from Spec spec left join fetch spec.attrs",
        countQuery = "select count(distinct spec) from Spec spec")
    Page<Spec> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct spec from Spec spec left join fetch spec.attrs")
    List<Spec> findAllWithEagerRelationships();

    @Query("select spec from Spec spec left join fetch spec.attrs where spec.id =:id")
    Optional<Spec> findOneWithEagerRelationships(@Param("id") Long id);
}
