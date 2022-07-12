import React, { Component } from 'react';
import ProductService from '../service/ProductService';
import LoginService from '../service/LoginService';
import CartService from '../service/CartService';
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom';


export class ProductListClass extends Component {
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
            toast(`${productName} is added to cart`, { position: "top-right", autoClose: 3000 });
        }
        else {
            toast.error("Please Login to access Cart service", { position: "top-center" });
            this.props.navigate("/login")
        }
    }

    render() {
        return (
            <div>
                <div className="contant">
                    {
                        this.state.products.map(
                            product =>
                                <div className="card" data-testid='products' key={product.productId}>
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


function ProductList() {
    const navigate = useNavigate();
    return (
        <div>
            <ProductListClass navigate={navigate}></ProductListClass>
        </div>
    )
}

export default ProductList