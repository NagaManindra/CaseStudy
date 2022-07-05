import React, { Component } from 'react'
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';

export default class Logout extends Component {
    componentDidMount() {

    }

    logout() {
        LoginService.userId("Profile")
        alert("Logged out Successfully")
        window.location = '/login'
    }

    render() {
        return (
            <div>
                <HeaderComponent userName={LoginService.id}></HeaderComponent>

                <div className="head1" >
                    <h4>Do you want to Logout?</h4>
                    <button className='remove1' onClick={() => this.logout()}>logout</button>
                </div>
            </div>
        )
    }
}
