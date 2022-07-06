import React, { useState } from "react";
import validator from 'validator';
import { useNavigate } from 'react-router-dom';
import LoginService from '../service/LoginService';
import '../css/loginStyle.css'

const SignupForm = () => {

    const [errorMessages, setErrorMessages] = useState('');
    const [userName, setUserName] = useState('');
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [unameError, setUnameError] = useState(false);
    const [emailError, setEmailError] = useState(false);
    const [passError, setPassError] = useState(false);
    const [cPassError, setCPassError] = useState(false);
    const [unameErrorMessage, setUnameErrorMessage] = useState('');
    const [emailErrorMessage, setEmailErrorMessage] = useState('');
    const [passErrorMessage, setPassErrorMessage] = useState('');
    const [cPassErrorMessage, setCPassErrorMessage] = useState('');
    const navigate = useNavigate();



    const onSignup = async event => {
        event.preventDefault();
        if (unameError & emailError & passError & cPassError) {
            const user = {
                userName: userName,
                fullName: fullName,
                email: email,
                gender: '',
                dob: '',
                mobile_no: '',
                password: password,
                address: {
                    house_no: "",
                    street_name: "",
                    colony_name: "",
                    city: "",
                    state: "",
                    pincode: ""
                }
            }
            console.log(userName)

            LoginService.addUser(user)



            alert("Registration Successful");
            navigate("/login");

        }
        else {
            setErrorMessages('enter valid details')
        }

    }

    const validateEmail = (e) => {
        setEmail(e.target.value)

        if (!validator.isEmail(e.target.value)) {
            setEmailErrorMessage('enter valid email')
            setEmailError(false);
        }
        else {
            setEmailErrorMessage('')
            setEmailError(true);
        }
    }

    const validateUserName = async e => {
        setUserName(e.target.value)
        const uname = e.target.value;
        const userDetails = await LoginService.getDetails(uname).catch(
            err => {
                console.log(err)
            }
        )
        if (!validator.isLength(e.target.value, {
            min: 6, max: 10
        })) {
            setUnameErrorMessage('username should be min 6 and max 10');
            setUnameError(false);
        }
        else if (userDetails != null) {
            setUnameErrorMessage('username already exist');
            setUnameError(false);
        }
        else {
            setUnameErrorMessage('')
            setUnameError(true);
        }
    }

    const validatePassword = (e) => {
        setPassword(e.target.value)

        if (!validator.isStrongPassword(e.target.value, {
            minLength: 8, minLowercase: 1, minUppercase: 1, minNumbers: 1, minSymbols: 1
        })) {
            setPassErrorMessage('password should contain atleast 1 lowercase, 1 uppercase, 1 numerical, 1 special charecter and length min 8')
            setPassError(false);
        }
        else {
            setPassErrorMessage('')
            setPassError(true);
        }
    }

    const validateCPassword = (e) => {

        if (password !== e.target.value) {
            setCPassErrorMessage("Password deosn't match")
            setCPassError(false);
        }
        else {
            setCPassErrorMessage('')
            setCPassError(true);
        }
    }

    const validName = (e) => {
        setFullName(e.target.value)
    }
    return (
        <div>
            <form className='form' onSubmit={onSignup}>
                <div className='register-Form'>
                    <h6 className="error">
                        <div className="error1">{errorMessages}</div>
                    </h6>


                    <h1 align="center">Sign Up</h1>
                    <p align="center">Please enter userName and password</p>
                    <hr></hr>


                    <label><b>UserName</b></label>
                    <input type="text" className='register' placeholder='userName' name='userName'
                        onChange={e => validateUserName(e)} on required />
                    <div className="error1">{unameErrorMessage}</div>

                    <label><b>Full Name</b></label>
                    <input type="text" className='register' placeholder='fullName' name='fullName'
                        onChange={e => validName(e)} required />

                    <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='email' name='email'
                        onChange={(e) => validateEmail(e)} required />
                    <div className="error1">{emailErrorMessage}</div>
                    {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}

                    <label><b>Password</b></label>
                    <input type="password" className='register' placeholder='password' name='password'
                        onChange={(e) => validatePassword(e)} required />
                    <div className="error1">{passErrorMessage}</div>
                    {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        onChange={e => this.confirmpassword = e.target.value}/> */}

                    <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='re-enter password' name='password'
                        onChange={(e) => validateCPassword(e)} required />
                    <div className="error1">{cPassErrorMessage}</div>

                    <button className='regBtn'>Sign Up</button>
                </div>
            </form>
        </div>
    );
}
export default SignupForm;