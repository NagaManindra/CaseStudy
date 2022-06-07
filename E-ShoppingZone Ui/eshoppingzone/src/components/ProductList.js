import React, { Component } from 'react';
import ProductService from '../service/ProductService';

class ProductList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: []
        }
        
    }

    componentDidMount(){
        ProductService.getProducts().then(res =>
            {
                this.setState({products:res.data});
            }
            );
    }
    
    render() {
        return (
            <div>
                <div className="contant">
                    {
                        this.state.products.map(
                            product => 
                            <div className="card" key={product.productId}>
                                <img className="cardImg" src={`./image/${product.image}`} alt="Card image cap"></img>
					            <div className="cardBody">
                                    <h5 className="card-title" >{product.productName}</h5>
                                    <h6 className="price">{product.price}</h6>
                                    <h6 className="price">{product.image}</h6>
                                    <h6 className="category">{product.productType}</h6>
                                    <div className="add">
                                        <form method="post">
                                            <button className="button" type="submit">Add to cart</button>
                                        </form>
                                    </div>
					            </div>
				            </div>
                        )
                    }
			    </div> 
            </div>
        );
    }
}

export default ProductList;