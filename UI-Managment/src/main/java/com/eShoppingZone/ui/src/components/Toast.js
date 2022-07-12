import React from 'react'

function Toast(props) {
    return (
        <div>
            <p>Update Profile</p>
            <button onClick={props.navigate("/update")}>update</button>
        </div>
    )
}

export default Toast