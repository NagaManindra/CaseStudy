import React, { useState } from 'react';
import bcryptjs from 'bcryptjs';
import '../css/loginStyle.css';
import { useNavigate } from 'react-router-dom';
import LoginService from '../service/LoginService';

export function LoginForm(props) {
  const [errorMessages, setErrorMessages] = useState({});
  const errors = {
    uname: "user not found",
    pass: "invalid password"
  };
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async event => {
    //Prevent page reload
    event.preventDefault();

    const userDetails = await LoginService.getDetails(userName)
      .catch(err => {
        setErrorMessages({ name: "uname", message: errors.uname });
      });


    // Compare user info
    if (userDetails.data.userName === userName) {
      if (!bcryptjs.compareSync(password, userDetails.data.password)) {
        // Invalid password
        setErrorMessages({ name: "pass", message: errors.pass });
      } else {
        alert(`welcome ${userDetails.data.fullName}`);
        LoginService.userId(userName)
        LoginService.userPassword(password)
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

  const validateUserName = (e) => {
    setUserName(e.target.value)
  }
  const validatePassword = (e) => {
    setPassword(e.target.value)
  }

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
          <input type="text" className='register' placeholder='username' name='uname' onChange={(e) => validateUserName(e)} />
          {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}

          <label><b>Password</b></label>
          <input type="password" className='register' placeholder='password' name='pass' onChange={(e) => validatePassword(e)} />
          {renderErrorMessage("pass")}
          {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        onChange={e => this.confirmpassword = e.target.value}/> */}

          <button className='regBtn' data-testid="button">Sign in</button>
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



