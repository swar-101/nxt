
Cart Invariants
1. A cart belongs to exactly one user
2. Quantity >= 1
3. Product must be ACTIVE and product.stock > 0 and price != null during add 
4. If product becomes unavailable -> mark item invalid, don't silently delete
5. Cart is NOT the source of truth for price
6. Checkout must revalidate all items
7. User can have only ONE ACTIVE cart
8. Cart cannot be modified if state != ACTIVE 
9. Quantity must not exceed allowed limit (e.g. 10)
10. Cart must not contain duplicate product IDs

Cart State Model
1. ACTIVE, 
2. CHECKED_OUT, 
3. EXPIRED

Cart Item Structure 
1. productId
2. quantity 
3. priceAtAdd 
4. metadata snapshot 

Failures  
1. Invalid product? Reject immediately 
2. Price mismatch? allowed in cart, rejected at checkout 