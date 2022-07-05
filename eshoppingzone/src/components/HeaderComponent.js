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
import React from 'react'
import { Link } from 'react-router-dom';
import '../css/HeaderFooter.css';

function HeaderComponent() {
    if (LoginService.id !== "Profile") {
        if (LoginService.role === "user") {
            return (
                <div>
                    <div className="fixed-header">
                        <div className="container">
                            <nav>
                                <span className="logo"> E-ShoppingZone</span>
                                <Link to={'/'} >Home</Link>
                                <Link to={'/cart'} >Cart</Link>
                                <Link to={'/toorder'} >Order</Link>
                                <Link to={'/profile'} >{LoginService.id}</Link>
                                <Link to={'/logout'} >logout</Link>

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
                                <span className="logo"> E-ShoppingZone</span>
                                <Link to={'/admin'} >Home</Link>
                                <Link to={'/addPro'} >Add</Link>
                                <Link to={'/profile'} >{LoginService.id}</Link>
                                <Link to={'/logout'} >logout</Link>

                            </nav>
                        </div>
                    </div>
                </div>
            )
        }
    }
    return (
        <div>
            <div className="fixed-header">
                <div className="container">
                    <nav>
                        <span className="logo"> E-ShoppingZone</span>
                        <Link to={'/'} >Home</Link>

                        <Link to={'/login'}>login</Link>
                    </nav>
                </div>
            </div>
        </div>
    )
}

export default HeaderComponent