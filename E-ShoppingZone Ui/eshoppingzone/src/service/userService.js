import axios from 'axios';

const productList_url = "http://localhost:8079/api/user/user/new/register";
class userService  {
    createUser(user){
        return axios.post(productList_url,user);
    }
}

export default new userService();