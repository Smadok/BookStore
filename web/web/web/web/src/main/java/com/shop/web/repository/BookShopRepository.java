package com.shop.web.repository;


import com.shop.web.models.BookShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BookShopRepository extends JpaRepository<BookShop,Integer>
{
 Optional<BookShop>findByName(String url);
 @Query("SELECT bs FROM BookShop bs WHERE bs.name LIKE concat('%', :query,'%') ")
 List<BookShop> searchBookShops(String query);
}
