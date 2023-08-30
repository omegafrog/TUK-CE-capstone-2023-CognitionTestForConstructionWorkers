import React, { useEffect, useRef, useState } from 'react'
import { CCard, CCardBody, CCol, CCardHeader, CRow, CButton } from '@coreui/react'
import {
  CChartBar,
  CChartDoughnut,
  CChartLine,
  CChartPie,
  CChartPolarArea,
  CChartRadar,
} from '@coreui/react-chartjs'
import { DocsCallout } from 'src/components'
import axios from 'axios'
//import { VscClose } from 'react-icons/vsc'
//import * as S from './style'

class Test extends React.Component {
  state = { selectedFiles: null }
  onClickHandler = (event) => {
    const formData = new FormData()
    formData.append(`uploadImages`, this.state.selectedFiles, this.state.selectedFiles.name)
    const config = {
      headers: {
        'content-type': 'multipart/form-data',
      },
    }
    axios.post(`uploadAPI`, formData, config)
  }
  fileChangedHandler = (event) => {
    const files = event.target.files
    this.setState({
      selectedFiles: files,
    })
  }
  render() {
    return (
      <div className="App" style={{ marginTop: `100px` }}>
        <input type="file" multiple onChange={this.fileChangedHandler} />
        <button onClick={this.onClickHandler}>저장하기</button>
      </div>
    )
  }
}

export default Test
