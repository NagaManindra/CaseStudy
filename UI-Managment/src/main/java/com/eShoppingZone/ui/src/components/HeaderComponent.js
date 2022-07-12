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
                            <Link to={'/'} >Home</Link>

                            <Link to={'/login'}>login</Link>
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

}

export default HeaderComponent