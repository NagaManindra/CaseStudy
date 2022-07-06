import React, { Component } from 'react'
import LoginService from '../service/LoginService';
import '../css/userDetails.css'
import { useNavigate } from 'react-router-dom';


export class UserDetailsClass extends Component {
    constructor(props) {
        super(props)

        this.state = {
            user: {},
            address: {}
        }
    }

    componentDidMount() {
        LoginService.getDetails(LoginService.id).then(res => {
            this.setState({ user: res.data, address: res.data.address });
        }
        );
    }

    update() {
        this.props.navigate("/update");
    }

    delete() {
        this.props.navigate("/delete");
    }

    render() {
        var user = this.state.user
        var address = this.state.address
        return (
            <div>
                <div data-testid="user" className="userDetails">
                    <div className="user">
                        <h4 className="Name">{user.fullName}</h4>
                        <div className="details">
                            <h5>Personal Details</h5>
                            <hr />
                            <div className="details1">
                                <h6 className="userName">Username : {user.userName}</h6>
                                <h6 className="email">Email : {user.email}</h6>
                                <h6 className="gender" >Gender : {user.gender}</h6>
                                <h6 className="dob" >Date of Birth : {user.dob}</h6>
                                <h6 className="mobile_no" >Number : {user.mobile_no}</h6>
                            </div>
                        </div>
                        <div className="address">
                            <h5>Address</h5>
                            <hr />
                            <div className="address1">
                                <h6 className="house_no" >House No : {address.house_no}</h6>
                                <h6 className="street_name">Street : {address.street_name}</h6>
                                <h6 className="colony_name" >Colony : {address.colony_name}</h6>
                                <h6 className="city" >City : {address.city}</h6>
                                <h6 className="state" >State : {address.state}</h6>
                                <h6 className="pincode" >Pincode : {address.pincode}</h6>
                            </div>
                        </div>
                        <div>
                            <span className='span'>
                                <button className='buttonUserUpdate' onClick={() => this.update()}>Update Profile</button>
                            </span>
                            <span className='span'>
                                <button className='buttonUserDelete' onClick={() => this.delete()}>Delete Profile</button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


export default function UserDetails() {
    const navigate = useNavigate();
    return (
        <div>
            <UserDetailsClass navigate={navigate}></UserDetailsClass>
        </div>
    )
}
