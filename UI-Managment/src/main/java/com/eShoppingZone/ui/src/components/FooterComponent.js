import React, { Component } from 'react'
import '../css/home.css';

export default class FooterComponent extends Component {
  render() {
    return (
      <div>
        <div className="fixed-footer">
            <div className="container">Copyright &copy; 2022 Capgemini</div>        
        </div>
      </div>
    )
  }
}
