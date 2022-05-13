package com.toanlv.springmvc.repositories;

import com.toanlv.springmvc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Day la noi de goi cac ham lay data ve (co the goi la service)
public interface ProductRepository extends JpaRepository<Product,Long> {
// trong nay khong can viet gi vi cac ham xu ly data da co trong JpaRepository
    // Neu can tim theo thuoc tinh moi thi co the viet them ham nhu sau:
    List<Product> findProductByProductName(String productName);
}
