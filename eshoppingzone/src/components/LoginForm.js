import React, { useState } from 'react';
import bcryptjs from 'bcryptjs';
import '../css/loginStyle.css';
import { useNavigate } from 'react-router-dom';
import LoginService from '../service/LoginService';

function LoginForm(props) {
  const [errorMessages, setErrorMessages] = useState({});
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
    if (userDetails.data.userName === uname.value) {
      if (!bcryptjs.compareSync(pass.value, userDetails.data.password)) {
        // Invalid password
        setErrorMessages({ name: "pass", message: errors.pass });
      } else {
        alert(`welcome ${userDetails.data.fullName}`);
        LoginService.userId(uname.value)
        LoginService.userPassword(pass.value)
        LoginService.userRole(userDetails.data.role)
        if (userDetails.data.role === "user") {
          props.navigate("/products")
        }
        else {
          props.navigate("/admin")
        }
        console.log(userDetails.data.role)

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

  const onSignup = () => {
    window.location = '/register';
  };

  return (
    <div>
      <form className='form' onSubmit={handleSubmit}>
        <div className='register-Form'>
          <h6 className="error">{renderErrorMessage('uname')}</h6>

          <h1 align="center">Login</h1>
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

          <button className='regBtn'>Sign in</button>
        </div>
      </form>
      <button className='regBtn1' onClick={onSignup}>Sign up</button>

    </div>

  );

}

function LoginPage() {
  const navigate = useNavigate();
  return (
    <LoginForm navigate={navigate}></LoginForm>
  )
}

export default LoginPage



