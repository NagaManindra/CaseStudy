import { render, fireEvent, getByTestId, screen } from '@testing-library/react';
import React from 'react';
import axios from "axios";
import { act } from 'react-dom/test-utils';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginPage, { LoginForm } from '../components/LoginForm';
import user from '@testing-library/user-event';

jest.mock("axios")


describe("singUp form", () => {
    describe("with valid inputs", () => {
        it("calls on submit function", async () => {
            const userDetails = {
                userName: 'by2900',
                fullName: 'B Sai Yogesh',
                email: 'by2900@srmist.edu.in',
                gender: '',
                dob: '',
                mobile_no: '',
                password: '$2a$12$qzgikrxhMrp4kH1xsMLHcu6SoHfgQwZgGZB4Ki4aqIdTT/aOLwaV.',
                address: {
                    house_no: "",
                    street_name: "",
                    colony_name: "",
                    city: "",
                    state: "",
                    pincode: ""
                }
            }
            jest.spyOn(window, 'alert').mockImplementation(() => { });
            render(<BrowserRouter>
                <Routes>
                    <Route path="*" element={<LoginPage />} />
                </Routes>
            </BrowserRouter>);
            axios.get.mockResolvedValue({ data: userDetails });
            console.log = jest.fn();
            await act(async () => {
                user.type(screen.getByPlaceholderText('username'), "by2900");
                user.type(screen.getByPlaceholderText('password'), "Naga@7550");
            })

            await act(async () => {
                user.click(screen.getByTestId("button"))
            })
            const alertMock = jest.spyOn(window, 'alert').mockImplementation();
            //expect(console.log).toHaveBeenCalledWith(user.userName)
            expect(alertMock).toHaveBeenCalledTimes(1)
        })
    })
})