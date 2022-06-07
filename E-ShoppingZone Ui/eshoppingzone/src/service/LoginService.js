import axios from 'axios';
import { Component } from 'react';

const productList_url = "http://localhost:9001/user/";
class LoginService extends Component {
    constructor(props) {
      super(props)
    
      this.state = {
         userName : ""
      }
      this.getDetails = this.getDetails.bind(this);

    }
    
   
    async getDetails(username){
        var userDetails =  await axios.get(productList_url+`${username}`);
        this.setState({userName : userDetails.data.userName});
        console.log(this.state.userName)
        return userDetails;
    }
    getUserName(){
        console.log(this.state.userName)
        return this.state.userName
    }

    componentDidMount() {
        this.getDetails();
     }
}

export default new LoginService();