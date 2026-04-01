package com.spring.smbs_backend.repository;

import com.spring.smbs_backend.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, Integer> {
    Integer countByStatusAndShift(String status, String shift);
    Integer countByStatus(String status);
    Integer countByShift(String shift);
    @Query(value = "SELECT cashier_id FROM cashier WHERE user_id = :userId", nativeQuery = true)
    Integer findCashierIdByUserId(@Param("userId") Integer userId);

}

