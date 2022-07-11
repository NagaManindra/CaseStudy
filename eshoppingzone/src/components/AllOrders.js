import React, { Component } from 'react'
import OrderService from '../service/OrderService';
import LoginService from '../service/LoginService';
import '../css/orderStyle.css'
import { useNavigate } from 'react-router-dom';

export class AllOrdersClass extends Component {
    constructor(props) {
        super(props)

        this.state = {
            order: [],
            productlist: [],
            empty: false
        }
    }
    componentDidMount() {
        OrderService.getUserAllOrder(LoginService.id).then(res => {
            if (res.data.items !== null) {
                this.setState({ order: res.data });
                this.setState({ productlist: res.data.items })
                this.setState({ empty: true })
            }
        }
        );
    }

    clearOrder() {
        OrderService.deleteOrder(LoginService.id)
    }



    toOrders() {
        this.props.navigate("/toorder")
    }


    render() {
        if (this.state.empty) {
            return (
                <div>
                    <div className="remo">
                        <button className="buttonAllOrders" onClick={() => this.toOrders()} >Todays Order</button>
                        {this.state.order.map(
                            order =>

                                <div data-testid="toOrders" className="head2" key={order.orderId} >
                                    <h3>OrderId : {order.orderId}</h3>
                                    <h3>Date : {order.orderDate}</h3>
                                    <h3>Total Price : {order.totalPrice}</h3>
                                    <h3>Order Status : {order.orderStatus}</h3>
                                    <h3>Address : {order.address.house_no}, {order.address.street_name}, {order.address.colony_name}, {order.address.city}, {order.address.state}, {order.address.pincode}. Phone : {order.mobile_No}</h3>

                                    <div className="contant2">
                                        {
                                            order.items.map(
                                                list =>

                                                    <div data-testid="items" className="card2" key={list.product.productId}>
                                                        <div className="cardBody2">
                                                            <h5 className="card1-title">{list.product.productName}</h5>
                                                            <h6 className="price2">${list.product.price}</h6>
                                                            <h6 className="category2">Quantity : {list.quantity}</h6>
                                                            <h6 className="category2" >SubTotal : {list.subTotal}</h6><br />
                                                        </div>

                                                    </div>
                                            )}
                                    </div>
                                    <br />

                                </div>
                        )}
                        <div className="remo">
                            <button className="remove4" onClick={() => this.allOrders()} >All Order</button>
                        </div>
                    </div>

                </div>

            )
        }
        else {
            <div>

                <div className='head1'>
                    <h2>No Order made Today</h2>
                </div>
            </div>
        }

    }
}

export default function AllOrders() {
    const navigate = useNavigate();
    const login = () => {
        navigate("/login")
    }
    if (LoginService.id !== "Profile") {

        return (
            <div>
                <AllOrdersClass navigate={navigate}></AllOrdersClass>
            </div>
        )
    }
    else {
        return (
            <div>
                <div className='head1'>
                    <h2>Please Login to Access Order Services</h2>
                    <button className='regBtn3' onClick={() => login()}>SignIn / SignUp</button>
                </div>
            </div>
        )
    }
}
