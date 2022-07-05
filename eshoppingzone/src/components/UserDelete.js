import React, { useState } from 'react';
import bcryptjs from 'bcryptjs';
import '../css/loginStyle.css';
import { Navigate } from 'react-router-dom';
import LoginService from '../service/LoginService';
import HeaderComponent from './HeaderComponent';



function UserDelete() {
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);
  const errors = {
    uname: "user not found",
    pass: "invalid password"
  };
  const handleSubmit = async event => {
    //Prevent page reload
    event.preventDefault();

    var { uname, pass } = document.forms[0];
    const userDetails = await LoginService.getDetails(uname.value)
      .catch(err => {
        setErrorMessages({ name: "uname", message: errors.uname });
      });


    // Compare user info
    if (userDetails.data.userName === uname.value & uname.value === LoginService.id) {
      if (!bcryptjs.compareSync(pass.value, userDetails.data.password)) {
        // Invalid password
        setErrorMessages({ name: "pass", message: errors.pass });
      } else {
        LoginService.deleteUser(uname.value)
        LoginService.userId("Profile")
        setIsSubmitted(true);
        alert(`Deleted ${userDetails.data.fullName}`);

      }
    } else {
      // Username not found
      setErrorMessages({ name: "uname", message: errors.uname });
    }
  };

  const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error1">{errorMessages.message}</div>
    );


  const renderForm = (
    <div>
      <HeaderComponent userName={LoginService.id}></HeaderComponent>

      <form className='form' onSubmit={handleSubmit}>
        <div className='register-Form'>
          <h6 className="error">{renderErrorMessage('uname')}</h6>

          <h1 align="center">Delete User</h1>
          <p align="center">Please enter username and password</p>
          <hr></hr>


          <label><b>Username</b></label>
          <input type="text" className='register' placeholder='username' name='uname' />
          {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}

          <label><b>Password</b></label>
          <input type="password" className='register' placeholder='password' name='pass' />
          {renderErrorMessage("pass")}
          {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        onChange={e => this.confirmpassword = e.target.value}/> */}

          <button className='regBtn'>Delete</button>
        </div>
      </form>

    </div>

  );


  return (

    <div className="app">
      <div className="login-form">
        {isSubmitted ? <Navigate to={'/login'}>User is successfully logged in</Navigate> : renderForm}
      </div>
    </div>
  );

}
export default UserDelete;



