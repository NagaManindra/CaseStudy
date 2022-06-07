import LoginService from '../service/LoginService'
import React, { Component } from 'react'

export default class userProfile extends Component {

  constructor(props) {
    super(props)
  
    this.state = {
       userName : ''
    }
  }

  componentDidMount(){
    this.setState({userName : LoginService.getUserName()})

  }
  

  render() {
    return (
      <div>userProfile
        <h1>{this.state.userName}</h1>
      </div>
    )
  }
}
