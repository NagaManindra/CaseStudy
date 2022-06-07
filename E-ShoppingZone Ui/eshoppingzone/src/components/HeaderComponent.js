import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class HeaderComponent extends Component {
    render() {
        return (
            <div>
                <div className="fixed-header">
                    <div className="container">  
                        <nav>
                            <span className="logo"> E-ShoppingZone</span>
                            <Link to={'/'} >Home</Link>
                            <span >Cart</span>
                            <span >Order</span>
                            <span>Services</span>
                            <Link to={'/login'}>login</Link>
                        </nav>
                    </div>
                </div>
            </div>
        );
    }
}

export default HeaderComponent;