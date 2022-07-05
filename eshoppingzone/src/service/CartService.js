import axios from 'axios';
import LoginService from '../service/LoginService';

const cart_url = "http://localhost:8079/api/cart/user/";
class CartService {

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    async getCart(cartId) {
        return axios.get(cart_url + `getcart/${cartId}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }
    addToCart(username, productId) {
        axios.post(cart_url + `additem/${username}/${productId}`, null, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }

    updateCart(username, productId, quantity) {
        axios.put(cart_url + `updateitem/${username}/${productId}/${quantity}`, null, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }

    deleteCartItem(username, productId) {
        axios.delete(cart_url + `deleteitem/${username}/${productId}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }

    deleteCart(username) {
        axios.delete(cart_url + `deletecart/${username}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } })
    }
}

export default new CartService();