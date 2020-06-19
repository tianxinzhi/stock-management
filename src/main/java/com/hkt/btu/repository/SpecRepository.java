package com.hkt.btu.repository;

import com.hkt.btu.domain.Spec;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Spec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
}
