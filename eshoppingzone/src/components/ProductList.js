import React, { Component } from 'react';
import ProductService from '../service/ProductService';
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';
import CartService from '../service/CartService';



class ProductList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            user: LoginService.id
        }

    }

    componentDidMount() {
        ProductService.getProducts().then(res => {
            this.setState({ products: res.data });
        }
        );
    }

    addToCart(productId, productName) {
        if (LoginService.id !== "Profile") {
            CartService.addToCart(this.state.user, productId)
            alert(`${productName} is added to cart`);
        }
        else {
            alert("Please Login to access Cart service");
            window.location = "/login"
        }
    }

    render() {
        return (
            <div>
                <HeaderComponent userName={this.state.user}></HeaderComponent>
                <div className="contant">
                    {
                        this.state.products.map(
                            product =>
                                <div className="card" data-testid='todo' key={product.productId}>
                                    <img className="cardImg" src={`./image/${product.image}`} alt={`${product.image}`}></img>
                                    <div className="cardBody">
                                        <h5 className="card-title" >{product.productName}</h5>
                                        <h6 className="price">${product.price}</h6>
                                        <h6 className="category">{product.productType}</h6>
                                        <div className="add">
                                            <button className="button" onClick={() => this.addToCart(product.productId, product.productName)} type="submit">Add to cart</button>
                                        </div>
                                    </div>
                                </div>
                        )
                    }
                </div>
            </div>
        );
    }
}

export default ProductList;