import React from 'react';
import { Container, Typography, Card, CardMedia, CardContent, Grid2 } from '@mui/material';

import { useNavigate } from 'react-router-dom';

const products = [
    { id: 1, name: 'Chaqueta la designador', price: '$13.99', image: 'assets/mock-jacket-1.webp' },
    { id: 2, name: 'Camiseta de lana Lyocell', price: '$6.99', image: 'assets/lyocell-camisa-1.jpg' },
];

const Homepage = () => {
    // const navigate = useNavigate();
    console.log(products);
    return (
        <Container maxWidth="lg" sx={{ py:4 }}>
            <Typography variant="h4" gutterBottom sx={{ textAlign: 'center', mb: 4 }}>
                Featured products
            </Typography>
            <Grid2 container spacing={4}>
                {products.map((product) => (
                    <Grid2 item xs={12} sm={6} md={4} key={product.id}>
                        <Card
                            sx={{
                                height: '100%', 
                                display: 'flex',
                                flexDirection: 'column',
                                cursor: 'pointer',
                                '&:hover': { boxShadow: 3 }
                            }}
                            // onClick={() => navigate(`/product/${product.id}`)}
                        >
                            <CardMedia
                                component="img"
                                image={product.image}
                                alt={product.name}
                                sx={{ height: 300, objectFit: 'cover' }}
                            />
                            <CardContent sx={{ flexGrow: 1 }}>
                                <Typography variant="h6">{product.name}</Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {product.price}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid2>
                ))}
            </Grid2>
        </Container>
    );
};

export default Homepage;