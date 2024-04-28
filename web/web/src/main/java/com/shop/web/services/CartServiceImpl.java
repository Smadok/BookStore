package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.dto.CartDto;
import com.shop.web.models.Book;
import com.shop.web.models.Cart;
import com.shop.web.models.Category;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public Cart getCartById(int cartId) {
        return cartRepository.findById(cartId).get();
    }

    @Override
    public double calculateTotalPrice(CartDto cartDto) {
        if (cartDto == null || cartDto.getBooks() == null || cartDto.getBooks().isEmpty()) {
            return 0.0; // Return 0 if cart is empty or null
        }

        // Iterate over the list of books in the cart and calculate total price
        double totalPrice = 0.0;
        for (BookDto book : cartDto.getBooks()) {
            totalPrice += book.getPrice(); // Assuming each book has a 'getPrice' method
        }

        return totalPrice;
    }


}
