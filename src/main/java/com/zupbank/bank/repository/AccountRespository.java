package com.zupbank.bank.repository;

import com.zupbank.bank.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRespository extends JpaRepository<Account, Long> {

    Optional<Account> findByProposalClientEmailContaining(String email);
}
