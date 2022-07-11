import { render, screen, waitFor, within, fireEvent } from '@testing-library/react';
import React from 'react';
import user from '@testing-library/user-event';
import SignupForm from '../components/SignupForm';
import axios from "axios";
import { act } from 'react-dom/test-utils';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginService from '../service/LoginService';

jest.mock("axios")


describe("singUp form", () => {
    describe("with valid inputs", () => {
        it("calls on submit function", async () => {
            const user = {
                userName: 'by2900',
                fullName: 'B Sai Yogesh',
                email: 'by2900@srmist.edu.in',
                gender: '',
                dob: '',
                mobile_no: '',
                password: 'Byogesh@2900',
                address: {
                    house_no: "",
                    street_name: "",
                    colony_name: "",
                    city: "",
                    state: "",
                    pincode: ""
                }
            }
            const mockOnSubmit = jest.fn();
            jest.spyOn(window, 'alert').mockImplementation(() => { });

            const { getByPlaceholderText, getByRole } = render(<BrowserRouter>
                <Routes>
                    <Route path="*" element={<SignupForm onSubmit={mockOnSubmit} />} />
                </Routes>
            </BrowserRouter>);
            axios.get.mockResolvedValue(null);
            axios.post.mockResolvedValue({ data: user });
            console.log = jest.fn();
            await act(async () => {
                fireEvent.change(getByPlaceholderText('userName'), { target: { value: "by2900" } });
                fireEvent.change(getByPlaceholderText('fullName'), { target: { value: "B Sai Yogesh" } });
                fireEvent.change(getByPlaceholderText('email'), { target: { value: "by2900@srmist.edu.in" } });
                fireEvent.change(getByPlaceholderText('password'), { target: { value: "Byogesh@2900" } });
                fireEvent.change(getByPlaceholderText('re-enter password'), { target: { value: "Byogesh@2900" } });
            })

            await act(async () => {
                fireEvent.click(getByRole("button"))
            })
            const alertMock = jest.spyOn(window, 'alert').mockImplementation();
            expect(console.log).toHaveBeenCalledWith(user.userName)
            expect(alertMock).toHaveBeenCalledTimes(1)
        })
    })

    describe("with invalid inputs", () => {
        it("calls on submit function", async () => {
            const user = {
                userName: 'by2900',
                fullName: 'B Sai Yogesh',
                email: 'by2900@srmist.edu.in',
                gender: '',
                dob: '',
                mobile_no: '',
                password: 'Byogesh@2900',
                address: {
                    house_no: "",
                    street_name: "",
                    colony_name: "",
                    city: "",
                    state: "",
                    pincode: ""
                }
            }
            const mockOnSubmit = jest.fn();
            jest.spyOn(window, 'alert').mockImplementation(() => { });

            const { getByPlaceholderText, getByRole } = render(<BrowserRouter>
                <Routes>
                    <Route path="*" element={<SignupForm onSubmit={mockOnSubmit} />} />
                </Routes>
            </BrowserRouter>);
            axios.get.mockResolvedValue({ data: user });
            axios.post.mockResolvedValue({ data: user });
            console.log = jest.fn();
            await act(async () => {
                fireEvent.change(getByPlaceholderText('userName'), { target: { value: "by2900" } });
                fireEvent.change(getByPlaceholderText('fullName'), { target: { value: "B Sai Yogesh" } });
                fireEvent.change(getByPlaceholderText('email'), { target: { value: "by2900@srmist.edu.in" } });
                fireEvent.change(getByPlaceholderText('password'), { target: { value: "Byogesh@2900" } });
                fireEvent.change(getByPlaceholderText('re-enter password'), { target: { value: "Byogesh@2900" } });
            })

            await act(async () => {
                fireEvent.click(getByRole("button"))
            })
            const alertMock = jest.spyOn(window, 'alert').mockImplementation();
            expect(console.log).toHaveBeenCalledWith('user not found')
            //            expect(alertMock).toHaveBeenCalledTimes(1)
        })
    })
})