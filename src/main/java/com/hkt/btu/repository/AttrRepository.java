package com.hkt.btu.repository;

import com.hkt.btu.domain.Attr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Attr entity.
 */
@Repository
public interface AttrRepository extends JpaRepository<Attr, Long> {

    @Query(value = "select distinct attr from Attr attr left join fetch attr.attrValues",
        countQuery = "select count(distinct attr) from Attr attr")
    Page<Attr> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct attr from Attr attr left join fetch attr.attrValues")
    List<Attr> findAllWithEagerRelationships();

    @Query("select attr from Attr attr left join fetch attr.attrValues where attr.id =:id")
    Optional<Attr> findOneWithEagerRelationships(@Param("id") Long id);
}
