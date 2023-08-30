import React from 'react'
class Today extends React.Component {
  render() {
    const today = new Date()
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
    const formattedDate = today.toLocaleDateString('en-US', options)
    return <div>Today is {formattedDate}</div>
  }
}


export default Today