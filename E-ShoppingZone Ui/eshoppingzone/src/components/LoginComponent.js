import axios from 'axios';
import { bind } from 'lodash';
import React, { Component, useState } from 'react'
import bcryptjs from 'bcryptjs';
import '../css/loginStyle.css';
import { Navigate } from 'react-router-dom';

export default class LoginComponent extends Component {
    constructor(props) {
      super(props)
    
      this.state = {
         userName : "",
         password : "",
         errorMessages : {},
         isSubmitted : false
      }
      this.renderErrorMessage = this.renderErrorMessage(bind);
      this.onSignup = this.onSignup(bind);
      this.renderForm = this.renderForm(bind);
      this.errors =this.errors(bind);
      this.handleSubmit = this.handleSubmit(bind);
    }

    errors = {
        uname: "user not found",
        pass: "invalid password"
    };

    handleSubmit = async event => {
        //Prevent page reload
        event.preventDefault();
    
        var { uname, pass } = document.forms[0];
        console.log(uname.value);
        
        const userDetails = await axios.get(`http://localhost:8079/api/user/user/${uname.value}`)
        .catch(err=>{
            this.state.errorMessages({ name: "uname", message: this.errors.uname });
        });
        
            
        // Compare user info
        if (userDetails.data.userName === uname.value) {
          if (!bcryptjs.compareSync(pass.value,userDetails.data.password)) {
            // Invalid password
            this.state.errorMessages({ name:"pass", message: this.errors.pass });
          } else {
            alert(`welcome ${userDetails.data.fullName}`);
            this.state.isSubmitted(true);
          }
        } else {
          // Username not found
          this.state.errorMessages({ name: "uname", message: this.errors.uname });
        }
    };

    renderErrorMessage = (name) =>
    name === this.state.errorMessages.name && (
      <div className="error1">{this.state.errorMessages.message}</div>
    );

    onSignup=()=>{
        window.location = '/register';
    };

    renderForm = (
        <div>
            <form className='form' onSubmit={this.handleSubmit}>
                <div className='register-Form'>
                <h6 className="error">{this.renderErrorMessage('uname')}</h6>

                    <h1 align="center">Login</h1>
                    <p align="center">Please enter username and password</p>
                    <hr></hr>
                    

                    <label><b>Username</b></label>
                    <input type="text" className='register' placeholder='username' name='uname'/>
                    {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}
                
                    <label><b>Password</b></label>
                    <input type="password" className='register' placeholder='password' name='pass'/>
                    {this.renderErrorMessage("pass")}
                    {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        onChange={e => this.confirmpassword = e.target.value}/> */}
                    
                    <button className='regBtn'>Sign in</button>
                </div>
            </form>
            <button className='regBtn1' onClick={this.onSignup}>Sign up</button>

            

        </div>
      );


  render() {
    return (
        <div className="app">
        <div className="login-form">
          <div className="title">Sign In</div>
          {this.state.isSubmitted ? <Navigate to={'/products'}>User is successfully logged in</Navigate> : this.renderForm}
        </div>
      </div>
    )
  }
}
