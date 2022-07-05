import React, { useEffect, useState } from "react"
import OrderService from "../service/OrderService";
import LoginService from '../service/LoginService';
import HeaderComponent from "./HeaderComponent";
import '../css/payment.css'
import CartService from "../service/CartService";

const PaytmChecksum = require('./paytmChecksum').default;
const https = require('https');

export function PaytmButton() {
  const [paymentData, setPaymentData] = useState({
    token: "",
    order: "",
    mid: "",
    amount: ""
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    initialize();
  }, []);



  const initialize = async e => {

    var cartDetails = await OrderService.getOrder(OrderService.id);
    let amount = cartDetails.data.totalPrice;
    let custId = cartDetails.data.customerId;
    let orderId = cartDetails.data.orderId;

    // Sandbox Credentials
    let mid = "wtYSWO67585826770371"; // Merchant ID
    let mkey = "LWShhf%1AYDLT@b8"; // Merhcant Key
    var paytmParams = {};

    paytmParams.body = {
      "requestType": "Payment",
      "mid": mid,
      "websiteName": "WEBSTAGING",
      "orderId": orderId,
      "callbackUrl": "https://merchant.com/callback",
      "txnAmount": {
        "value": amount,
        "currency": "INR",
      },
      "userInfo": {
        "custId": custId,
      }
    };

    PaytmChecksum.generateSignature(JSON.stringify(paytmParams.body), mkey).then(function (checksum) {
      console.log(checksum);
      paytmParams.head = {
        "signature": checksum
      };

      var post_data = JSON.stringify(paytmParams);

      var options = {
        /* for Staging */
        hostname: 'securegw-stage.paytm.in',

        /* for Production */
        // hostname: 'securegw.paytm.in',

        port: 443,
        path: `/theia/api/v1/initiateTransaction?mid=${mid}&orderId=${orderId}`,
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Content-Length': post_data.length
        }
      };

      var response = "";
      var post_req = https.request(options, function (post_res) {
        post_res.on('data', function (chunk) {
          response += chunk;
        });
        post_res.on('end', function () {
          console.log('Response: ', response);
          // res.json({data: JSON.parse(response), orderId: orderId, mid: mid, amount: amount});
          setPaymentData({
            ...paymentData,
            token: JSON.parse(response).body.txnToken,
            order: orderId,
            mid: mid,
            amount: amount
          })
        });
      });

      post_req.write(post_data);
      post_req.end();
    });
  }

  const makePayment = () => {
    setLoading(true);
    var config = {
      "root": "",
      "style": {
        "bodyBackgroundColor": "#fafafb",
        "bodyColor": "",
        "themeBackgroundColor": "#0FB8C9",
        "themeColor": "#ffffff",
        "headerBackgroundColor": "#284055",
        "headerColor": "#ffffff",
        "errorColor": "",
        "successColor": "",
        "card": {
          "padding": "",
          "backgroundColor": ""
        }
      },
      "data": {
        "orderId": paymentData.order,
        "token": paymentData.token,
        "tokenType": "TXN_TOKEN",
        "amount": paymentData.amount /* update amount */
      },
      "payMode": {
        "labels": {},
        "filter": {
          "exclude": []
        },
        "order": [
          "CC",
          "DC",
          "NB",
          "UPI",
          "PPBL",
          "PPI",
          "BALANCE"
        ]
      },
      "website": "WEBSTAGING",
      "flow": "DEFAULT",
      "merchant": {
        "mid": paymentData.mid,
        "redirect": false
      },
      "handler": {
        "transactionStatus": function transactionStatus(paymentStatus) {
          console.log("paymentStatus => ", paymentStatus);
          OrderService.updateOrder(paymentData.order);
          CartService.deleteCart(LoginService.id)
          console.log(paymentData.order)
          alert("Payment Successfull /n Order placed")
          alert("Please Login Agian")
          window.location = "/"
          setLoading(false);
        },
        "notifyMerchant": function notifyMerchant(eventName, data) {
          console.log("Closed");
          setLoading(false);
        }
      }
    };

    if (window.Paytm && window.Paytm.CheckoutJS) {
      // initialze configuration using init method
      window.Paytm.CheckoutJS.init(config).then(function onSuccess() {
        console.log('Before JS Checkout invoke');
        // after successfully update configuration invoke checkoutjs
        window.Paytm.CheckoutJS.invoke();
      }).catch(function onError(error) {
        console.log("Error => ", error);
      });
    }
  }

  return (
    <div>
      {
        loading ? (
          <img alt="" src="https://c.tenor.com/I6kN-6X7nhAAAAAj/loading-buffering.gif" />
        ) : (
          <div>
            <HeaderComponent userName={LoginService.id}></HeaderComponent>

            <div className="register-Form1">
              <h1 align="center">Payment</h1>
              <hr></hr>


              <label><b>OrderId</b></label>
              <input type="text" className='register' value={paymentData.order} />

              <label><b>Amount</b></label>
              <input type="text" className='register' value={paymentData.amount} />

              <button className="regBtn2" onClick={makePayment}>Pay Now</button>
            </div>
          </div>
        )}

    </div>
  )
}