import axios from 'axios';
import LoginService from '../service/LoginService';

const productList_url = "http://localhost:8079/api/product/user/getAll";
const admin_url = "http://localhost:8079/api/product/admin/";
class ProductService {
    constructor() {
        this.id = null;

    }
    productId = (id) => {
        if (!id) return this.id;
        this.id = id;
    }
    getProducts() {
        return axios.get(productList_url);
    }
    getProductsAdmin() {
        return axios.get(admin_url + "getAll", { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }
    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    async getByProductId(productId) {
        return await axios.get(admin_url + `getById/${productId}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }

    async updateProduct(product) {
        return await axios.put(admin_url + `update/${this.id}`, product, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }

    async addProduct(product) {
        return await axios.post(admin_url + `addProduct/`, product, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }

    async deleteProduct(productId) {
        return await axios.delete(admin_url + `deleteproduct/${productId}`, { headers: { authorization: this.createBasicAuthToken(LoginService.id, LoginService.password) } });
    }
}
const instance = new ProductService()
export default instance;
