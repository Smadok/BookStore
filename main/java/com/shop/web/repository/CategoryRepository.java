package com.shop.web.repository;


import com.shop.web.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer>
{
 Optional<Category>findByName(String url);
 @Query("SELECT bs FROM Category bs WHERE bs.name LIKE concat('%', :query,'%') ")
 List<Category> searchCategories(String query);
}
