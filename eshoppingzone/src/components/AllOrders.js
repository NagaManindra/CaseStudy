import React, { Component } from 'react'
import OrderService from '../service/OrderService';
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';
import '../css/cartStyle.css'
import { Link } from 'react-router-dom';

export class AllOrders extends Component {
    constructor(props) {
        super(props)

        this.state = {
            order: [],
            productlist: [],
        }
    }
    componentDidMount() {
        OrderService.getUserAllOrder(LoginService.id).then(res => {
            console.log(res)
            this.setState({ order: res.data });
            this.setState({ productlist: res.data.items })

        }
        );
    }

    clearOrder() {
        OrderService.deleteOrder(LoginService.id)
    }



    render() {
        return (
            <div>
                <HeaderComponent userName={LoginService.id}></HeaderComponent>
                <div className="remo">
                    <Link className="remove4" to={'/toorder'} >Todays Orders</Link>
                </div>
                {this.state.order.map(
                    order =>

                        <div className="head1" key={order.orderId} >
                            <h3>OrderId : {order.orderId}</h3>
                            <h3>Date : {order.orderDate}</h3>
                            <h3>Total Price : {order.totalPrice}</h3>
                            <h3>Order Status : {order.orderStatus}</h3>
                            <h3>Address : {order.address.house_no}, {order.address.street_name}, {order.address.colony_name}, {order.address.city}, {order.address.state}, {order.address.pincode}. Phone : {order.mobile_No}</h3>
                            <div className="contant1">
                                {
                                    order.items.map(
                                        list =>

                                            <div className="card1" key={list.product.productId}>
                                                <div className="cardBody1">
                                                    <h5 className="card1-title">{list.product.productName}</h5>
                                                    <h6 className="price1">${list.product.price}</h6>
                                                    <h6 className="category1">Quantity : {list.quantity}</h6>
                                                    <h6 className="category1" >SubTotal : {list.subTotal}</h6><br />
                                                </div>

                                            </div>
                                    )}
                            </div>
                            <br />

                        </div>
                )}

            </div>

        )
    }
}


