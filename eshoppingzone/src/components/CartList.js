import React, { Component } from 'react'
import CartService from '../service/CartService';
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';
import '../css/cartStyle.css'
import { Link, useNavigate } from 'react-router-dom';
import OrderService from '../service/OrderService';

export class CartList extends Component {
    constructor(props) {
        super(props)

        this.state = {
            cart: {},
            productlist: [],
            empty: false
        }
    }
    componentDidMount() {
        this.getCart();
    }


    componentDidUpdate() {
        this.getCart();
    }

    getCart() {
        CartService.getCart(LoginService.id).then(res => {
            if (res.data.items != null) {
                this.setState({ productlist: res.data.items })
                this.setState({ cart: res.data })
                this.setState({ empty: true })
            }
        }
        );
    }

    cartUpdate = (e, productId) => {
        let quantity = e.target.value
        let list = this.state.productlist.map((item) => {
            if (item.product.productId === productId) {
                const updatedItem = {
                    ...item,
                    quantity: quantity,
                };

                return updatedItem;
            }

            return item;
        });

        this.setState({ productlist: list });


        CartService.updateCart(LoginService.id, productId, quantity)
    }

    deleteItem(productId) {
        CartService.deleteCartItem(LoginService.id, productId)
    }

    clearCart() {
        CartService.deleteCart(LoginService.id)
    }

    async placeOrder() {
        if (this.state.cart.totalPrice !== 0) {
            var cartDetails = await OrderService.addOrder(LoginService.id);
            OrderService.orderId(cartDetails.data.orderId);
            this.props.navigate("/pay")
        }
        else {
            alert("Add Items to Cart")
        }
    }


    render() {
        if (this.state.empty) {
            return (
                <div>
                    <HeaderComponent userName={LoginService.id}></HeaderComponent>

                    <div className="head1" >
                        <h3>UserName : {this.state.cart.cartId}</h3>
                        <h3>Total Price : {this.state.cart.totalPrice}</h3>
                        <div className="contant1">
                            {
                                this.state.productlist.map(
                                    list =>

                                        <div className="card1" key={list.product.productId}>
                                            <div className="cardBody1">
                                                <h5 className="card1-title">{list.product.productName}</h5>
                                                <h6 className="price1">${list.product.price}</h6>
                                                <h6 className="category1">Quantity :
                                                    <input type="number" value={list.quantity} className="category1" min="1" name="quantity" onChange={e => this.cartUpdate(e, list.product.productId)} />
                                                </h6>
                                                <h6 className="category1" >SubTotal : {list.subTotal}</h6><br />
                                                <div className="remo">
                                                    <button className="remove1" onClick={() => this.deleteItem(list.product.productId)}>Remove</button>
                                                </div>

                                            </div>

                                        </div>
                                )}
                        </div>
                        <br />
                        <div className="rem">
                            <button className="remove3" onClick={() => this.placeOrder()} >Order</button>

                            <button className="remove1" onClick={() => this.clearCart()}>Clear</button>
                        </div>
                    </div>
                </div>
            )
        }

        else {
            return (
                <div>
                    <HeaderComponent userName={LoginService.id}></HeaderComponent>

                    <div className='head1'>
                        <h2>Add items to Cart</h2>
                    </div>
                </div>
            )
        }
    }
}


function CartListFunction() {
    const navigate = useNavigate();
    if (LoginService.id !== "Profile") {
        return (
            <CartList navigate={navigate}></CartList>
        )
    }
    else {
        return (
            <div>
                <HeaderComponent userName={LoginService.id}></HeaderComponent>
                <div className='head1'>
                    <h2>Please Login to Access Cart service</h2>
                    <Link className="regBtn3" to={"/login"}>SignIn / SingUp</Link>
                </div>
            </div>
        )
    }
}

export default CartListFunction