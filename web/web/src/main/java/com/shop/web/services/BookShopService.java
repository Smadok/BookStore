package com.shop.web.services;

import com.shop.web.dto.BookShopDto;
import com.shop.web.models.BookShop;

import java.util.List;

public interface BookShopService {
    List<BookShopDto>findAllBookShops();
    BookShop saveBookShop(BookShopDto bookShopDto);

    BookShopDto findBookShopsById(int bookShopId);

    void updateBookShop(BookShopDto bookShop);

    void delete(int bookShopId);
    List<BookShopDto> searchBookShops(String query);
}
