// import React, { Component } from 'react';
// import { Link } from 'react-router-dom';
// import '../css/HeaderFooter.css';


// class HeaderComponent extends Component {
//     constructor(props) {
//         super(props);

//         this.state = {
//             user: 'profile'
//         }

//     }

//     componentDidMount(){
//         this.setState({user : this.props.userName})
//     }


//     render() {
//         return (
//             <div>
//                 <div className="fixed-header">
//                     <div className="container">  
//                         <nav>
//                             <span className="logo"> E-ShoppingZone</span>
//                             <Link to={'/'} >Home</Link>
//                             <Link to={'/cart'} >Cart</Link>
//                             <Link to={'/'} >Order</Link>
//                             <Link to={'/profile'} >{this.state.user}</Link>
//                             <Link to={'/login'}>login</Link>
//                         </nav>
//                     </div>
//                 </div>
//             </div>
//         );
//     }
// }

// export default HeaderComponent;

import LoginService from '../service/LoginService';
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import '../css/HeaderFooter.css';
import 'primeicons/primeicons.css';


function HeaderComponent(props) {
    const [name, setName] = useState("Profile");
    const [role, setRole] = useState(null);

    useEffect(() => {
        setInterval(() => {
            setName(LoginService.id);
            setRole(LoginService.role)
        }, 1)
    })
    if (name === "Profile") {
        return (
            <div>
                <div className="fixed-header">
                    <div className="container">
                        <nav>
                            <span className="logo"> E-ShoppingZone</span>
                            <Link to={'/'} >
                                <i className="pi pi-home" style={{paddingRight: '10px'}}></i>
                                Home
                            </Link>

                            <Link to={'/login'}>
                                <i className="pi pi-sign-in" style={{paddingRight: '10px'}}></i>
                                login
                            </Link>
                        </nav>
                    </div>
                </div>
            </div>
        )
    }
    else {
        if (role === "user") {
            return (
                <div>
                    <div className="fixed-header">
                        <div className="container">
                            <nav>
                                <span className="logo"> E-ShoppingZone</span>
                                <Link to={'/'} >
                                    <i className="pi pi-home" style={{paddingRight: '10px'}}></i>
                                    Home
                                </Link>
                                <Link to={'/cart'} >
                                    <i className="pi pi-shopping-cart" style={{paddingRight: '10px'}}></i>
                                    Cart
                                </Link>
                                <Link to={'/toorder'} >
                                    <i className="pi pi-shopping-bag" style={{paddingRight: '10px'}}></i>
                                    Order
                                </Link>
                                <Link to={'/profile'} >
                                    <i className="pi pi-user" style={{paddingRight: '10px'}}></i>
                                    {LoginService.id}
                                </Link>
                                <Link to={'/logout'} >
                                    <i className="pi pi-sign-out" style={{paddingRight: '10px'}}></i>
                                    logout
                                </Link>

                            </nav>
                        </div>
                    </div>
                </div>
            )
        }
        else {
            return (
                <div>
                    <div className="fixed-header">
                        <div className="container">
                            <nav>
                                <span className="logo">
                                    <img src= {require('../images/online-shopping.png')} width="30px" height="30px"></img>
                                    E-ShoppingZone
                                </span>
                                <Link to={'/admin'} >
                                    <i className="pi pi-home" style={{paddingRight: '10px'}}></i>
                                    Home
                                </Link>
                                <Link to={'/addPro'} >
                                    <i className="pi pi-plus" style={{paddingRight: '10px'}}></i>
                                    Add
                                </Link>
                                <Link to={'/profile'} >
                                    <i className="pi pi-user" style={{paddingRight: '10px'}}></i>
                                    {LoginService.id}
                                </Link>
                                <Link to={'/logout'} >
                                    <i className="pi pi-sign-out" style={{paddingRight: '10px'}}></i>
                                    logout
                                </Link>

                            </nav>
                        </div>
                    </div>
                </div>
            )
        }
    }

}

export default HeaderComponent