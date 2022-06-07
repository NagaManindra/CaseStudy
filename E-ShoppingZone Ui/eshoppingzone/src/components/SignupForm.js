import axios from "axios";
import React, { useState } from "react";
import validator from 'validator';
import {useNavigate} from 'react-router-dom';
import userService from "../service/userService";
  
const SignupForm = () => {

    const [errorMessages, setErrorMessages] = useState({});
    const [userName, setUserName] = useState('');
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();


    const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error1">{errorMessages.message}</div>
    );

    const onSignup = async event => {
        event.preventDefault();
        if(error ==='true'){
            const user = {
                userName : userName,
                fullName : fullName,
                email : email,
                password : password
            }
            console.log(userName)

            const userDetails = await axios.post("http://localhost:9001/user/new/register",user).then(res => {
                console.log(res);
            });

            
            
            alert("Registration Successful");
            navigate("/login");
            
        }
        
    }

    const validateEmail = (e) => {
        setEmail(e.target.value)
    
        if (!validator.isEmail(e.target.value)) {
            setErrorMessages({name:"email",message: 'enter valid email'})
            setError('false');
        }
        else{
            setErrorMessages({name:"email",message: ''})
            setError('true');
        }
    }

    const validateUserName = async e => {
        setUserName(e.target.value)
        const uname = e.target.value;
        const userDetails = await axios.get(`http://localhost:8079/api/user/user/${uname}`).catch(
            err => {
                console.log(err)
            }
        )
        if(!validator.isLength(e.target.value,{
            min : 6 , max : 10})){
            setErrorMessages({name:"userName",message: 'username should be min 6 and max 10'});
            setError('false');
        }
        else if(userDetails != null){
            setErrorMessages({name:"userName",message: 'username already exist'});
            setError('false');
        }
        else{
            setErrorMessages({name:"userName",message: ''})
            setError('true');
        }
    }

    const validatePassword = (e) => {
        setPassword(e.target.value)

        if(!validator.isStrongPassword(e.target.value,{
            minLength:8,minLowercase:1,minUppercase:1,minNumbers:1,minSymbols:1})){
            setErrorMessages({name:"password",message: 'password should contain atleast 1 lowercase, 1 uppercase, 1 numerical, 1 special charecter and length min 8'})
            setError('false');
        }
        else{
            setErrorMessages({name:"password",message: ''})
            setError('true');
        }
    }

    const validName = (e) => {
        setFullName(e.target.value)
    }
    return(
        <div>
            <form className='form' onSubmit={onSignup}>
                <div className='register-Form'>
                {renderErrorMessage("user")}


                    <h1 align="center">Sign Up</h1>
                    <p align="center">Please enter userName and password</p>
                    <hr></hr>
                    

                    <label><b>UserName</b></label>
                    <input type="text" className='register' placeholder='userName' name='userName'
                        onChange={e=>validateUserName(e)} required/>
                    {renderErrorMessage("userName")}

                    <label><b>Full Name</b></label>
                    <input type="text" className='register' placeholder='fullName' name='fullName'
                         onChange={e=>validName(e)} required/>
                    {renderErrorMessage("fname")}

                    <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='email' name='email'
                        onChange={(e)=>validateEmail(e)} required/>
                    {renderErrorMessage("email")}
                    {/* <label><b>Email</b></label>
                    <input type="text" className='register' placeholder='abc.@' name='email'
                        onChange={e => this.email = e.target.value}/> */}
                
                    <label><b>Password</b></label>
                    <input type="password" className='register' placeholder='password' name='password'
                        onChange={(e)=>validatePassword(e)} required/>
                    {renderErrorMessage("password")}                
                    {/* <label><b>Confirm Password</b></label>
                    <input type="password" className='register' placeholder='password' name='confirmpassword'
                        onChange={e => this.confirmpassword = e.target.value}/> */}
                    
                    <button className='regBtn'>Sign Up</button>
                </div>
            </form>
        </div>
    );
}
export default SignupForm;