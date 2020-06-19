package com.hkt.btu.repository;

import com.hkt.btu.domain.Attr;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Attr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttrRepository extends JpaRepository<Attr, Long> {
}
