import { render, waitFor, screen, getByTestId } from "@testing-library/react";
import axios from "axios";
import { CartList } from "../components/CartList";
import CartService from "../service/CartService"

jest.mock("axios");

test("Get Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.get.mockResolvedValue({ data: cart });
    render(<CartList />);

    const cartDetails = waitFor(() => screen.findAllByTestId
        ("cart"));
    const cartItems = waitFor(() => screen.findAllByTestId
        ("items"));
    expect((await cartDetails)).toHaveLength(1);
    expect((await cartItems)).toHaveLength(2);

});

test("Add to Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.post.mockResolvedValue({ data: cart });

    expect((await (CartService.addToCart("by2900", 131))).data).toBe(cart);

});

test("update Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.put.mockResolvedValue({ data: cart });

    expect((await (CartService.updateCart("by2900", 131, 3))).data).toBe(cart);

});

test("Add to Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.post.mockResolvedValue({ data: cart });

    expect((await (CartService.addToCart("by2900", 131))).data).toBe(cart);

});

test("update Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.put.mockResolvedValue({ data: cart });

    expect((await (CartService.updateCart("by2900", 131, 3))).data).toBe(cart);

});

test("Delete Item", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 1665.0,
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

    axios.delete.mockResolvedValue({ data: cart });

    expect((await (CartService.deleteCartItem("by2900", 131))).data).toBe(cart);

});

test("Delete Cart", async () => {
    const cart = {
        cartId: "by2900",
        totalPrice: 0,
        items: []
    }

    axios.delete.mockResolvedValue({ data: cart });

    expect((await (CartService.deleteCart("by2900"))).data).toBe(cart);

});