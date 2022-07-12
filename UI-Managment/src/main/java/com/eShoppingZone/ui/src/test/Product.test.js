import { render, waitFor, screen } from "@testing-library/react";
import axios from "axios";
import ProductList from "../components/ProductList";
import ProductService from "../service/ProductService"

jest.mock("axios");

test("Product List", async () => {
    const products = [
        {
            productId: "123",
            productType: "Full Sleeves",
            productName: "TS Athiletic",
            category: "T-Shirst",
            image: "abc.jpg",
            price: 499.0,
            rating: 3.8,
            description: "Fully Conforatable"
        },
        {
            productId: "124",
            productType: "Full Sleeves",
            productName: "TS Basic",
            category: "T-Shirt",
            image: "abd.jpg",
            price: 799.0,
            rating: 3.5,
            description: "Woolen,Fully Conforatable"
        },
        {
            productId: "126",
            productType: "Full Sleeves",
            productName: "TS ROG",
            category: "T-Shirt",
            image: "abf.jpg",
            price: 399.0,
            rating: 3.7,
            description: "Woolen,Yellow"
        }
    ]
    axios.get.mockResolvedValue({ data: products });
    render(<ProductList />);

    const todoList = waitFor(() => screen.findAllByTestId
        ("products"));
    expect((await todoList)).toHaveLength(3);
});

test('Get Product By Id', async () => {
    const product = {
        productId: "126",
        productType: "Full Sleeves",
        productName: "TS ROG",
        category: "T-Shirt",
        image: "abf.jpg",
        price: 399.0,
        rating: 3.7,
        description: "Woolen,Yellow"
    }
    axios.get.mockResolvedValue({ data: product });
    expect((await ProductService.getByProductId("126")).data).toBe(product);
})

test('Add Product', async () => {
    const product = {
        productId: "126",
        productType: "Full Sleeves",
        productName: "TS ROG",
        category: "T-Shirt",
        image: "abf.jpg",
        price: 399.0,
        rating: 3.7,
        description: "Woolen,Yellow"
    }
    axios.post.mockResolvedValue({ data: product });
    expect((await ProductService.addProduct(product)).data).toBe(product);
})

test('Update Product', async () => {
    const product = {
        productId: "126",
        productType: "Full Sleeves",
        productName: "TS ROG",
        category: "T-Shirt",
        image: "abf.jpg",
        price: 399.0,
        rating: 3.7,
        description: "Woolen,Yellow"
    }
    axios.put.mockResolvedValue({ data: product });
    expect((await ProductService.updateProduct(product)).data).toBe(product);
})

test('Delete Product', async () => {

    axios.delete.mockResolvedValue({ statusText: "NO_CONTENT" });
    expect((await ProductService.deleteProduct("126")).statusText).toBe("NO_CONTENT");
})