package com.example.library.service.impl;

import com.example.library.dto.ProductDto;
import com.example.library.model.CartItem;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CartItemRepository;
import com.example.library.repository.ShoppingCartRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CustomerService customerService;
    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart getCart(String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getCart();
        return cart;
    }

    @Override
    public ShoppingCart getCartByIdCustomer(Long id) {
        Customer customer = customerService.findById(id);
        ShoppingCart cart = customer.getCart();
        return cart;
    }

    @Override
    @Transactional
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        //Tìm user của cart
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getCart();

        //Khởi tạo cart mới nếu rỗng
        if (cart == null) {
            cart = new ShoppingCart();
        }

        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItemList, productDto.getId());
        Product product = transfer(productDto);

        double unitPrice = productDto.getCostPrice();
        int itemQuantity = 0;

        //cart chưa có item
        if (cartItemList == null){
            cartItemList = new HashSet<>();
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            }
            else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        //cart có item
        else if(cartItemList != null) {
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            }
            else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        cart.setCartItems(cartItemList);
        double totalPrice = totalPrice(cart.getCartItems());
        int totalItem = totalItem(cart.getCartItems());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setCustomer(customer);

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public ShoppingCart updateCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getCart();
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItemList, productDto.getId());

        int itemQuantity = quantity;
        cartItem.setQuantity(itemQuantity);
        cartItemRepository.save(cartItem);
        cart.setCartItems(cartItemList);

        int totalItem = totalItem(cartItemList);
        double totalPrice = totalPrice(cartItemList);
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getCart();
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem item = findCartItem(cartItemList, productDto.getId());
        cartItemList.remove(item);
        cartItemRepository.delete(item);

        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);

        cart.setCartItems(cartItemList);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart cart = cartRepository.getById(id);
        if(!ObjectUtils.isEmpty(cart) && !ObjectUtils.isEmpty(cart.getCartItems())){
            cartItemRepository.deleteAll(cart.getCartItems());
        }
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cart.setTotalItems(0);
        cartRepository.save(cart);
    }

    //--------Working with Session----------

    @Override
    public ShoppingCart addItemToCartSession(ShoppingCart cart, ProductDto productDto, int quantity) {
        CartItem cartItem = findCartItem2(cart, productDto.getId());
        if (cart == null){
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItemList = cart.getCartItems();
        Product product = transfer(productDto);
        double unitPrice = productDto.getCostPrice();
        int itemQuantity = 0;

        if  (cartItemList == null){
            cartItemList = new HashSet<>();
            if (cartItem ==null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                System.out.println("added new item");
            }else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        }
        else {
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                System.out.println("added new item");
            }else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        }
        cart.setCartItems(cartItemList);
        double totalPrice = totalPrice(cart.getCartItems());
        int totalItem = totalItem(cart.getCartItems());
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);

        System.out.println("total item guest: "+ cart.getTotalItems());
        System.out.println("total price guest: "+ cart.getTotalPrice());
        System.out.println("success added item guest");
        return cart;
    }

    @Override
    public ShoppingCart updateCartSession(ShoppingCart cart, ProductDto productDto, int quantity) {
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem item = findCartItem2(cart, productDto.getId());

        item.setQuantity(quantity);
        cart.setCartItems(cartItemList);

        cart.setTotalPrice(totalPrice(cartItemList));
        cart.setTotalItems(totalItem(cartItemList));
        System.out.println(cart.getTotalItems());
        return cart;
    }

    @Override
    public ShoppingCart removeItemFromCartSession(ShoppingCart cart, ProductDto productDto) {
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem item = findCartItem2(cart, productDto.getId());
        cartItemList.remove(item);

        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);
        cart.setCartItems(cartItemList);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        System.out.println("Deleted: " + item);
        return cart;
    }


    //Tìm item cart theo id product
    private CartItem findCartItem(Set<CartItem> cartItems, Long productId){
        if (cartItems == null){
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems){
            if(item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }

    //Tìm item cart khách vãng lai
    private CartItem findCartItem2(ShoppingCart shoppingCart, long productId){
        if (shoppingCart == null){
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : shoppingCart.getCartItems()){
            if (item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }

    //Tính tổng giá của cart
    private double totalPrice(Set<CartItem> cartItemList){
        double totalPrice = 0.0;
        for (CartItem item : cartItemList){
            totalPrice = totalPrice + item.getUnitPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    //Tính tổng số item trong list cart
    private int totalItem(Set<CartItem> cartItemList){
        int totalItem = 0;
        for (CartItem item : cartItemList){
            totalItem = totalItem + item.getQuantity();
        }
        return totalItem;
    }
    // đổi sang product
    private Product transfer(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.set_activated(productDto.isActivated());
        product.set_deleted(productDto.isDeleted());
        product.setCategory(productDto.getCategory());
        return product;
    }








}
