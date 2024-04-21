package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;
import com.shop.web.models.Cart;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



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
        Cart cart = cartRepository.findById(cartId)
                .orElse(new Cart());
        Book book = bookRepository.findById(bookId)
                .orElse(new Book());

        cart.getBooks().add(book);
        cartRepository.save(cart);
    }
    @Override
    public Cart getCartByUserName(String currentUserName)
    {
        return cartRepository.findByUserUsername(currentUserName);
    }

}
