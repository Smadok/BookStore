package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.dto.CartDto;
import com.shop.web.models.Book;
import com.shop.web.models.Cart;
import com.shop.web.models.Category;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static com.shop.web.mapper.BookMapper.mapToBook;
import static com.shop.web.mapper.BookMapper.mapToBookDto;
import static com.shop.web.mapper.CartMapper.mapToCart;
import static com.shop.web.mapper.CartMapper.mapToCartDto;
import static com.shop.web.mapper.CategoryMapper.mapToCategoryDto;


@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;
    private BookRepository bookRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,BookRepository bookRepository) {

        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void deleteCart(int id) {

        cartRepository.deleteById(id);
    }

    @Override
    public void addBookToCart(int cartId, int bookId) {
        CartDto cartDto = mapToCartDto(cartRepository.findById(cartId).get());
        BookDto bookDto = mapToBookDto(bookRepository.findById(bookId).get());

        cartDto.getBooks().add(bookDto);

        Cart cart = mapToCart(cartDto);
        cartRepository.save(cart);

    }
    @Override
    public CartDto getCartByUserName(String currentUserName)
    {
        Cart cart=cartRepository.findByUserUsername(currentUserName);
        return mapToCartDto(cart);
    }

    @Override
    public void removeBookFromCart(int cartId, int bookId) {
        CartDto cartDto = mapToCartDto(cartRepository.findById(cartId).get());
        BookDto bookToRemoveDto = mapToBookDto(bookRepository.findById(bookId).get());

        cartDto.getBooks().remove(bookToRemoveDto);
        Cart cart = mapToCart(cartDto);
        cartRepository.save(cart);
    }

}
