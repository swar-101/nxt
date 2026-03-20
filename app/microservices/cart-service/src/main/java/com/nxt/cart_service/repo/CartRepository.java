package com.nxt.cart_service.repo;

import com.nxt.cart_service.entity.Cart;
import com.nxt.cart_service.entity.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserIdAndState(Long userId, State state);
}