package com.shop.web.services;

import com.shop.web.dto.BookShopDto;
import com.shop.web.models.BookShop;
import com.shop.web.repository.BookShopRepository;
import com.shop.web.services.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shop.web.mapper.BookShopMapper.mapToBookShop;
import static com.shop.web.mapper.BookShopMapper.mapToBookShopDto;

@Service
public class BookShopImpl implements BookShopService
{
    private BookShopRepository bookShopRepository;
    @Autowired
    public BookShopImpl(BookShopRepository bookShopRepository) {
        this.bookShopRepository = bookShopRepository;
    }
    @Override
    public List<BookShopDto>findAllBookShops()
    {
        List<BookShop> bookShops = bookShopRepository.findAll();
        return bookShops.stream().map((bookShop) -> mapToBookShopDto(bookShop)).collect(Collectors.toList());

    }
    @Override
    public BookShop saveBookShop(BookShopDto bookShopDto)
    {
        BookShop bookShop= mapToBookShop(bookShopDto);
        return bookShopRepository.save(bookShop);
    }

    @Override
    public BookShopDto findBookShopsById(int bookShopId)
    {
        BookShop bookShop = bookShopRepository.findById(bookShopId).get();
        return mapToBookShopDto(bookShop);
    }

    @Override
    public void updateBookShop(BookShopDto bookShopDto)
    {
        BookShop bookShop =mapToBookShop(bookShopDto);
        bookShopRepository.save(bookShop);
    }

    @Override
    public void delete(int bookShopId) {
        bookShopRepository.deleteById(bookShopId);
    }

    @Override
    public List<BookShopDto> searchBookShops(String query) {
        List<BookShop>bookShops = bookShopRepository.searchBookShops(query);
        return bookShops.stream().map(bookShop -> mapToBookShopDto(bookShop)).collect(Collectors.toList());

    }



}
