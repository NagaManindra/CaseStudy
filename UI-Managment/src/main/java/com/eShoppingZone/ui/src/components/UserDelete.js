import React, { useState } from 'react';
import bcryptjs from 'bcryptjs';
import '../css/loginStyle.css';
import { Navigate } from 'react-router-dom';
import LoginService from '../service/LoginService';
import { toast } from 'react-toastify';


function UserDelete() {
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);
  const errors = {
    uname: "user not found",
    pass: "invalid password"
  };
  const userName = LoginService.id;
  const [password, setPassword] = useState('');

  const handleSubmit = async event => {
    //Prevent page reload
    event.preventDefault();

    const userDetails = await LoginService.getDetails(userName)
      .catch(err => {
        setErrorMessages({ name: "uname", message: errors.uname });
      });


    // Compare user info

    if (!bcryptjs.compareSync(password, userDetails.data.password)) {
      // Invalid password
      setErrorMessages({ name: "pass", message: errors.pass });
    } else {
      LoginService.deleteUser(userName)
      LoginService.userId("Profile")
      setIsSubmitted(true);
      toast.dark(`Deleted ${userDetails.data.fullName}`, { position: "top-center" });

    }

  };

  const validatePassword = (e) => {
    setPassword(e.target.value)
  }

  const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error1">{errorMessages.message}</div>
    );


  const renderForm = (
    <div>
      <form className='form' onSubmit={handleSubmit}>
        <div className='register-Form'>
          <h6 className="error">{renderErrorMessage('uname')}</h6>

          <h1 align="center">Delete User</h1>
          <p align="center">Please enter username and password</p>
          <hr></hr>


          <label><b>Username</b></label>
          <input type="text" className='register' value={userName} placeholder='username' name='uname' readOnly />
          {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}

          <label><b>Password</b></label>
          <input type="password" className='register' placeholder='password' name='pass' onChange={(e) => validatePassword(e)} />
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



