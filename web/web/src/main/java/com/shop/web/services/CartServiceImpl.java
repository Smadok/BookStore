package com.shop.web.services;

import com.shop.web.dto.BookDto;
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
        Cart cart = cartRepository.findById(cartId).get();
        Book book = bookRepository.findById(bookId).get();

        cart.getBooks().add(book);
        cartRepository.save(cart);

    }
    @Override
    public Cart getCartByUserName(String currentUserName)
    {
        return cartRepository.findByUserUsername(currentUserName);
    }

    @Override
    public void removeBookFromCart(int cartId, int bookId) {
        Cart cart = cartRepository.findById(cartId).get();
        Book bookToRemove = bookRepository.findById(bookId).get();

        cart.getBooks().remove(bookToRemove);
        cartRepository.save(cart);
    }

}
