import React, { Component } from 'react';
import ProductService from '../service/ProductService';
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';
import { useNavigate } from 'react-router-dom';



class AdminProductList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            user: LoginService.id
        }

    }

    componentDidMount() {
        ProductService.getProductsAdmin().then(res => {
            this.setState({ products: res.data });
        }
        );
    }

    componentDidUpdate() {
        ProductService.getProductsAdmin().then(res => {
            this.setState({ products: res.data });
        }
        );
    }

    updateItem(productId) {
        ProductService.productId(productId);
        this.props.navigate("/proUpdate")

    }

    deleteItem(productId) {
        ProductService.deleteProduct(productId);
    }

    render() {
        if (LoginService.role === "admin") {
            return (
                <div>
                    <HeaderComponent userName={this.state.user}></HeaderComponent>
                    <div className="contant">
                        {
                            this.state.products.map(
                                product =>
                                    <div className="card" key={product.productId}>
                                        <img className="cardImg" src={`./image/${product.image}`} alt={`${product.image}`}></img>
                                        <div className="cardBody">
                                            <h5 className="card-title" >{product.productName}</h5>
                                            <h6 className="price">${product.price}</h6>
                                            <h6 className="category">{product.productType}</h6>
                                            <div className="add">
                                                <button className="button" onClick={() => this.updateItem(product.productId)} type="submit">Edit</button>
                                                <button className="button12" onClick={() => this.deleteItem(product.productId)} type="submit">Delete</button>
                                            </div>
                                        </div>
                                    </div>
                            )
                        }
                    </div>
                </div>
            );
        }
        else {
            return (
                <div>
                    <HeaderComponent userName={LoginService.id}></HeaderComponent>

                    <div className='head1'>
                        <h2>You are not Allowed to this Page</h2>
                    </div>
                </div>
            )
        }
    }
}


function AdminProduct() {
    const navigate = useNavigate();
    return (
        <AdminProductList navigate={navigate}></AdminProductList>
    )
}

export default AdminProduct