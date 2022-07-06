import React, { useEffect, useState } from "react";
import validator from 'validator';
import { useNavigate } from 'react-router-dom';
import LoginService from '../service/LoginService';

const UserUpdate = () => {

    const [errorMessages, setErrorMessages] = useState('');
    const [dob, setDob] = useState('');
    const [fullName, setFullName] = useState('');
    const [gender, setGender] = useState('');
    const [email, setEmail] = useState('');
    const [mobile_no, setMobile_no] = useState('');
    const [house_no, setHouse_no] = useState('');
    const [street_name, setStreet_name] = useState('');
    const [colony_name, setColony_name] = useState('');
    const [city, setCity] = useState('');
    const [state, setState] = useState('');
    const [pincode, setPincode] = useState('');
    const [emailError, setEmailError] = useState(true);
    const [numError, setNumError] = useState(true);
    const [pinError, setPinError] = useState(true);
    const [emailErrorMessage, setEmailErrorMessage] = useState('');
    const [numErrorMessage, setNumErrorMessage] = useState('');
    const [pinErrorMessage, setPinErrorMessage] = useState('');
    const navigate = useNavigate();




    useEffect(() => {
        LoginService.getDetails(LoginService.id).then(
            res => {
                const user = res.data
                const address = res.data.address
                setFullName(user.fullName)
                setEmail(user.email)
                setDob(user.dob)
                setGender(user.gender)
                setMobile_no(user.mobile_no)
                setHouse_no(address.house_no)
                setStreet_name(address.street_name)
                setColony_name(address.colony_name)
                setCity(address.city)
                setState(address.state)
                setPincode(address.pincode)
            }
        )
    }, [])


    const onUpdate = async event => {
        event.preventDefault();
        if (emailError & numError & pinError) {
            const user = {
                userName: LoginService.id,
                fullName: fullName,
                email: email,
                gender: gender,
                dob: dob,
                mobile_no: mobile_no,
                address: {
                    house_no: house_no,
                    street_name: street_name,
                    colony_name: colony_name,
                    city: city,
                    state: state,
                    pincode: pincode
                }
            }

            LoginService.updateUser(LoginService.id, user);



            alert("Updated Successful");
            navigate("/profile");

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

    const validMobile_no = (e) => {
        setMobile_no(e.target.value)
        if (e.target.value.length > 10 || e.target.value.length < 10) {
            setNumErrorMessage('enter valid number')
            setNumError(false);
        }
        else {
            setNumErrorMessage('')
            setNumError(true);
        }
    }

    const validPin = (e) => {
        setPincode(e.target.value)

        if (e.target.value.length > 6 || e.target.value.length < 6) {
            setPinErrorMessage('enter valid Pincode')
            setPinError(false);
        }
        else {
            setPinErrorMessage('')
            setPinError(true);
        }
    }

    const validName = (e) => {
        setFullName(e.target.value)
    }

    const validDob = (e) => {
        setDob(e.target.value)
    }

    const validGender = (e) => {
        setGender(e.target.value)
    }

    const validHouse_no = (e) => {
        setHouse_no(e.target.value)
    }

    const validStreet_name = (e) => {
        setStreet_name(e.target.value)
    }

    const validColony_name = (e) => {
        setColony_name(e.target.value)
    }
    const validCity = (e) => {
        setCity(e.target.value)
    }
    const validState = (e) => {
        setState(e.target.value)
    }





    return (
        <div>
            <form className='form' onSubmit={onUpdate}>
                <div className='register-Form'>
                    <h6 className="error">
                        <div className="error1">{errorMessages}</div>
                    </h6>

                    <h1 align="center">Update Profile</h1>
                    <p align="center">Please enter required Details</p>
                    <hr></hr>

                    <label><b>Full Name</b></label>
                    <input type="text" value={fullName} className='register' placeholder='fullName' name='fullName'
                        onChange={e => validName(e)} required />

                    <label><b>Email</b></label>
                    <input type="text" value={email} className='register' placeholder='email' name='email'
                        onChange={(e) => validateEmail(e)} required />
                    <div className="error1">{emailErrorMessage}</div>

                    <label><b>Gender</b></label>
                    <select className='register' onChange={(e) => validGender(e)} id="roleDropMenu" name="gender">
                        <option value={gender}>{gender}</option>
                        <option value="Male">male</option>
                        <option value="Female">female</option>
                        <option value="Other">other</option>
                    </select>

                    <label htmlFor="dob"><b>Date of Birth</b></label>
                    <input type="date" value={dob} className='register' name="dob" onChange={(e) => validDob(e)} required></input>

                    <label htmlFor="mobile_no"><b>Mobile No</b></label>
                    <input type="number" value={mobile_no} className='register' name="mobile_no" onChange={(e) => validMobile_no(e)} required></input>
                    <div className="error1">{numErrorMessage}</div>

                    <br /><br />
                    <p><b>Address</b></p>
                    <hr />
                    <label htmlFor="address.house_no"><b>House No</b></label>
                    <input type="text" value={house_no} className='register' onChange={(e) => validHouse_no(e)} name="address.house_no" required />

                    <label htmlFor="address.street_name"><b>Street</b></label>
                    <input type="text" value={street_name} className='register' onChange={(e) => validStreet_name(e)} name="address.street_name" required />

                    <label htmlFor="address.colony_name"><b>Colony</b></label>
                    <input type="text" value={colony_name} className='register' onChange={(e) => validColony_name(e)} name="address.colony_name" required />

                    <label htmlFor="address.city"><b>City</b></label>
                    <input type="text" value={city} className='register' onChange={(e) => validCity(e)} name="address.city" required />

                    <label htmlFor="address.state"><b>State</b></label>
                    <input type="text" value={state} className='register' onChange={(e) => validState(e)} name="address.state" required />

                    <label htmlFor="address.pincode"><b>Pin Code</b></label>
                    <input type="text" value={pincode} className='register' onChange={(e) => validPin(e)} name="address.pincode" required />
                    <div className="error1">{pinErrorMessage}</div>

                    <button className='regBtn'>Update</button>
                </div>
            </form>
        </div>
    );
}
export default UserUpdate;