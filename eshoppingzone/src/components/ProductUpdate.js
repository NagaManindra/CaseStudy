import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import ProductService from '../service/ProductService';
import LoginService from '../service/LoginService';


function ProductUpdate() {
    const [productName, setProductName] = useState('');
    const [productType, setProductType] = useState('');
    const [category, setCategory] = useState('');
    const [image, setImage] = useState('');
    const [price, setPrice] = useState();
    const [rating, setRating] = useState('');
    const [description, setDescription] = useState('');
    const navigate = useNavigate();


    useEffect(() => {
        ProductService.getByProductId(ProductService.id).then(
            res => {
                const product = res.data
                setProductName(product.productName)
                setProductType(product.productType)
                setCategory(product.category)
                setImage(product.image)
                setPrice(product.price)
                setRating(product.rating)
                setDescription(product.description)
            }
        )
    }, [])

    const onUpdate = async event => {
        event.preventDefault();
        const product = {
            productId: ProductService.id,
            productName: productName,
            productType: productType,
            category: category,
            image: image,
            price: price,
            rating: rating,
            description: description
        }

        ProductService.updateProduct(product).then(res => { console.log(res) });
        alert("Product Updated")
        navigate("/admin")
    }

    const validName = (e) => {
        setProductName(e.target.value);
    }
    const validCategory = (e) => {
        setCategory(e.target.value);
    }
    const validProductType = (e) => {
        setProductType(e.target.value);
    }
    const validImage = (e) => {
        setImage(e.target.value);
    }
    const validPrice = (e) => {
        setPrice(e.target.value);
    }
    const validRating = (e) => {
        setRating(e.target.value);
    }
    const validDescription = (e) => {
        setDescription(e.target.value);
    }

    if (LoginService.role === "admin") {
        return (
            <div>
                <form className='form' onSubmit={onUpdate}>
                    <div className='register-Form'>

                        <h1 align="center">Update Product</h1>
                        <p align="center">Please enter required Details</p>
                        <hr></hr>

                        <label><b>Product Name</b></label>
                        <input type="text" value={productName} className='register' placeholder='fullName' name='fullName'
                            onChange={e => validName(e)} required />
                        <label><b>Product Type</b></label>
                        <input type="text" value={productType} className='register' placeholder='productType' name='productType'
                            onChange={e => validProductType(e)} required />
                        <label><b>Category</b></label>
                        <input type="text" value={category} className='register' placeholder='category' name='category'
                            onChange={e => validCategory(e)} required />
                        <label><b>Image</b></label>
                        <input type="text" value={image} className='register' placeholder='image.jpg' name='image'
                            onChange={e => validImage(e)} required />
                        <label><b>Price</b></label>
                        <input type="text" value={price} className='register' placeholder='price' name='price'
                            onChange={e => validPrice(e)} required />
                        <label><b>Rating</b></label>
                        <input type="text" value={rating} className='register' placeholder='rating' name='rating'
                            onChange={e => validRating(e)} required />
                        <label><b>Description</b></label>
                        <input type="text" value={description} className='register' placeholder='description' name='description'
                            onChange={e => validDescription(e)} required />
                        <button className='regBtn'>Update</button>
                    </div>
                </form>
            </div>
        )
    }
    else {
        return (
            <div>

                <div className='head1'>
                    <h2>You are not Allowed to this Page</h2>
                </div>
            </div>
        )
    }
}

export default ProductUpdate
