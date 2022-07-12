import { render, waitFor, screen } from "@testing-library/react";
import axios from "axios";
import { AllOrdersClass } from "../components/AllOrders";
import { TodayOrdersClass } from "../components/TodayOrders";

jest.mock("axios");

test("Get todays Order", async () => {
    const order = [{
        orderId: "62c24fde32a2e90ed596ffde",
        customerId: "by2900",
        orderDate: "2022-07-04",
        totalPrice: 998.0,
        orderStatus: "Order Failed",
        address: {
            house_no: "89/2-2-11-3",
            street_name: "Majestic function hall line",
            colony_name: "Balaji Nagar",
            city: "Kurnool",
            state: "Andhra Pradesh",
            pincode: 518006
        },
        mobile_No: 8179607355,
        items: [
            {
                product: {
                    productId: "130",
                    productName: "TS Black Mac",
                    price: 555.0
                },
                quantity: 3,
                subTotal: 1665.0
            },
            {
                product: {
                    productId: "131",
                    productName: "TS Mac",
                    price: 500.0
                },
                quantity: 3,
                subTotal: 1500.0
            }
        ]
    }]

    axios.get.mockResolvedValue({ data: order });
    render(<TodayOrdersClass />);

    const todayOrderDetails = waitFor(() => screen.findAllByTestId
        ("toOrders"));
    const todayOrderItems = waitFor(() => screen.findAllByTestId
        ("items"));
    expect((await todayOrderDetails)).toHaveLength(1);
    expect((await todayOrderItems)).toHaveLength(2);


});


test("Get all Order", async () => {
    const order = [
        {
            orderId: "62c24fde32a2e90ed596ffde",
            customerId: "by2900",
            orderDate: "2022-07-04",
            totalPrice: 998.0,
            orderStatus: "Order Failed",
            address: {
                house_no: "89/2-2-11-3",
                street_name: "Majestic function hall line",
                colony_name: "Balaji Nagar",
                city: "Kurnool",
                state: "Andhra Pradesh",
                pincode: 518006
            },
            mobile_No: 8179607355,
            items: [
                {
                    product: {
                        productId: "130",
                        productName: "TS Black Mac",
                        price: 555.0
                    },
                    quantity: 3,
                    subTotal: 1665.0
                },
                {
                    product: {
                        productId: "131",
                        productName: "TS Mac",
                        price: 500.0
                    },
                    quantity: 3,
                    subTotal: 1500.0
                }
            ]
        },
        {
            orderId: "62c24fde32a2e90ed596ffdf",
            customerId: "by2900",
            orderDate: "2022-07-04",
            totalPrice: 998.0,
            orderStatus: "Order Failed",
            address: {
                house_no: "89/2-2-11-3",
                street_name: "Majestic function hall line",
                colony_name: "Balaji Nagar",
                city: "Kurnool",
                state: "Andhra Pradesh",
                pincode: 518006
            },
            mobile_No: 8179607355,
            items: [
                {
                    product: {
                        productId: "130",
                        productName: "TS Black Mac",
                        price: 555.0
                    },
                    quantity: 3,
                    subTotal: 1665.0
                },
                {
                    product: {
                        productId: "131",
                        productName: "TS Mac",
                        price: 500.0
                    },
                    quantity: 3,
                    subTotal: 1500.0
                }
            ]
        }
    ]

    axios.get.mockResolvedValue({ data: order });
    render(<AllOrdersClass />);

    const allOrderDetails = screen.findAllByTestId
        ("toOrders");
    const allOrderItems = waitFor(() => screen.findAllByTestId
        ("items"));
    expect((await allOrderDetails)).toHaveLength(2);
    expect((await allOrderItems)).toHaveLength(4);


});