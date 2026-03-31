package com.spring.smbs_backend.repository;

import com.spring.smbs_backend.DTO.Response.Dashboard.*;
import com.spring.smbs_backend.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Bill, Integer> {
    @Query(value = """
        SELECT
            (SELECT COALESCE(SUM(b.total_amt), 0)
            FROM bill b
            WHERE DATE(b.created_at) = CURRENT_DATE) AS totalSalesToday,
                                                                    
            (SELECT COUNT(*)
            FROM bill b
            WHERE DATE(b.created_at) = CURRENT_DATE) AS totalCustomersToday,
                                                                    
            (SELECT COALESCE(SUM(bi.quantity), 0)
            FROM bill_items bi
            JOIN bill b ON b.bill_id = bi.bill_id
            WHERE DATE(b.created_at) = CURRENT_DATE) AS productsSoldToday,
                                                                    
            (SELECT COALESCE(SUM((bi.selling_price - bi.cost_price) * bi.quantity), 0)
            FROM bill_items bi
            JOIN bill b ON bi.bill_id = b.bill_id
            WHERE DATE(b.created_at) = CURRENT_DATE) AS totalProfitToday
    """, nativeQuery = true)
    DashboardStats getDashboardStats();

    @Query(value = """
    SELECT 
        YEAR(b.created_at) AS year,
        COALESCE(SUM(b.total_amt), 0) AS sales,
        COALESCE(SUM((bi.selling_price - bi.cost_price) * bi.quantity), 0) AS profit
    FROM bill b
    JOIN bill_items bi ON b.bill_id = bi.bill_id
    WHERE YEAR(b.created_at) >= YEAR(CURDATE()) - 4
    GROUP BY YEAR(b.created_at)
    ORDER BY YEAR(b.created_at) DESC
    """, nativeQuery = true)
    List<SalesOverview> getSalesOverview();

    @Query(value = """
    SELECT 
        p.name AS productName,
        SUM(bi.quantity) AS totalQuantitySold
    FROM product p
    JOIN bill_items bi ON p.product_id = bi.product_id
    JOIN bill b ON bi.bill_id = b.bill_id
    WHERE DATE(b.created_at) = CURDATE()
    GROUP BY p.product_id, p.name
    ORDER BY SUM(bi.quantity) DESC
    LIMIT 5
    """, nativeQuery = true)
    List<TopSellingProductsData> getTopSellingProducts();

    @Query(value = """
    SELECT 
        YEAR(b.created_at) AS year,
        COUNT(DISTINCT b.customer_id) AS numberOfCustomers
    FROM bill b
    WHERE b.customer_id IS NOT NULL
        AND YEAR(b.created_at) >= YEAR(CURDATE()) - 4
    GROUP BY YEAR(b.created_at)
    ORDER BY YEAR(b.created_at) ASC
    """, nativeQuery = true)
    List<CustomerGrowthData> getCustomerGrowthData();

    @Query(value = """
    SELECT 
        c.cashier_id,
        c.name,
        c.phone
    FROM cashier c
    WHERE c.status = 'ACTIVE'
    ORDER BY c.name ASC
    """, nativeQuery = true)
    List<ActiveCashiers> getActiveCashiers();
}
