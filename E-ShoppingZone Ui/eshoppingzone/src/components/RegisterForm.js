import axios from 'axios'
import React, { Component } from 'react'

export default class RegisterForm extends Component {
  onsubmit = e => {
    e.preventDefault()
    const data = {
      userName : "",
      fullName : "",
      email : "",
      password : ""
    }

    console.log(data)

    const user = axios.post("http://localhost:9001/user/new/register",data).then(
      res => {
        console.log(res)
      }
    )


  }
  render() {
    return (
<div>
            <form className='form' onSubmit={this.onsubmit}>
                <div className='register-Form'>
                

                    <h1 align="center">Sign Up</h1>
                    <p align="center">Please enter userName and password</p>
                    <hr></hr>
                    

                    <label><b>UserName</b></label>
                    <input type="text" className='register' placeholder='userName' name='userName'
                         required/>

                    <label><b>Full Name</b></label>
                    <input type="text" className='register' placeholder='fullName' name='fullName'
                          required/>

                    <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='email' name='email'
                         required/>
                    {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        = e.target.value}/> */}
                
                    <label><b>Password</b></label>
                    <input type="password" className='register' placeholder='password' name='password'
                         required/>
                    {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        mpassword = e.target.value}/> */}
                    
                    <button className='regBtn'>Sign Up</button>
                </div>
            </form>
        </div>    )
  }
}
