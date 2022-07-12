
import React from "react";
import GooglePayButton from "@google-pay/button-react";
import "../css/Gpay.css";
import '../css/payment.css'
import OrderService from "../service/OrderService";
import LoginService from '../service/LoginService';
import { useNavigate } from "react-router-dom";
import CartService from "../service/CartService";
import { toast } from 'react-toastify'


export default function GPay() {
    const navigate = useNavigate();
    const paymentRequest = {
        apiVersion: 2,
        apiVersionMinor: 0,
        allowedPaymentMethods: [
            {
                type: "CARD",
                parameters: {
                    allowedAuthMethods: ["PAN_ONLY", "CRYPTOGRAM_3DS"],
                    allowedCardNetworks: ["MASTERCARD", "VISA"]
                },
                tokenizationSpecification: {
                    type: "PAYMENT_GATEWAY",
                    parameters: {
                        gateway: "example"
                    }
                }
            }
        ],
        merchantInfo: {
            merchantId: "12345678901234567890",
            merchantName: "Demo Merchant"
        },
        transactionInfo: {
            totalPriceStatus: "FINAL",
            totalPriceLabel: "Total",
            totalPrice: `${OrderService.price}`,
            currencyCode: "IND",
            countryCode: "IN"
        }
    };

    const shift = () => {

        navigate("/toorder")
        toast.success("Payment Successfull Order placed", { position: "top-center" })
    }


    return (


        <div className="register-Form1">
            <h1 align="center">Payment</h1>
            <hr></hr>


            <label><b>OrderId</b></label>
            <input type="text" className='register' value={OrderService.id} readOnly />

            <label><b>Amount</b></label>
            <input type="text" className='register' value={OrderService.price} readOnly />

            <div className="demo">
                <GooglePayButton
                    environment="TEST"
                    buttonColor="default"
                    buttonType="buy"
                    buttonSizeMode="static"
                    paymentRequest={paymentRequest}
                    onLoadPaymentData={paymentRequest => {
                        OrderService.updateOrder(OrderService.id);
                        CartService.deleteCart(LoginService.id)
                        toast(`${OrderService.id} \n ${OrderService.price}`)
                        console.log("load payment data", paymentRequest);

                        shift();
                    }}
                />
            </div>
        </div>
    );
}