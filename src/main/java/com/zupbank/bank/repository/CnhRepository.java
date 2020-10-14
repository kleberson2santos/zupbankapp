package com.zupbank.bank.repository;

import com.zupbank.bank.domain.model.CNH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnhRepository extends JpaRepository<CNH, Long> {
}
