import axios from 'axios';

const productList_url = "http://localhost:8079/api/product/user/getAll";
class ProductService  {
    getProducts(){
        return axios.get(productList_url);
    }
}

export default new ProductService();