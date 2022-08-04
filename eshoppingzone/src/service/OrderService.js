import axios from 'axios'
import LoginService from '../service/LoginService';

const order_url = "http://localhost:8079/api/order/user/";
class OrderService {
    constructor() {
        this.id = "null";
        this.price = "null";
    }

    orderId = (id) => {
        if (!id) return this.id;
        this.id = id;
    }
    priceId = (id) => {
        if (!id) return this.price;
        this.price = id;
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }
    async addOrder(username) {
        return await axios.post(order_url + `addOrder/${username}`, null, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }
    async updateOrder(orderId) {
        axios.put(order_url + `updateOrder/${orderId}`, null, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }
    async getOrder(orderId) {
        return await axios.get(order_url + `getByOrderId/${orderId}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }
    async getUserOrder(username) {
        return await axios.get(order_url + `getTodaysOrders/${username}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }
    async getUserAllOrder(username) {
        return await axios.get(order_url + `getOrder/${username}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }


}

export default new OrderService();
