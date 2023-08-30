import { React, useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import qs from 'qs'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import axios from 'axios'
const Register_info = () => {
  const userinfo = JSON.parse(localStorage.getItem('userinfo'))
  const dispatch = useDispatch()
  const token = sessionStorage.getItem('token')
  const [ID, setID] = useState(userinfo.username)
  const [Name, setName] = useState(userinfo.name)
  const [Password, setPassword] = useState(userinfo.password)
  const [Age, setAge] = useState(userinfo.age)
  const [Field, setField] = useState(userinfo.field.id)
  const [loading, setLoading] = useState(false)
  const [msg, setMsg] = useState('')
  const [Carrer, setCarrer] = useState(userinfo.career)
  const [detailedJob, setdetailedJob] = useState('')
  const carrerhandler = (e) => {
    setCarrer(e.target.value)
  }
  useEffect(() => {
    /*...*/
  }, [msg])

  console.log(userinfo)
  const id = sessionStorage.getItem('sub_id')

  useEffect(() => {
    axios
      .get(`https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subject/${id}`, {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
        withCredentials: true,
        baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
      })
      .then((response) => {
        console.log(response)
        let sub = response.data.results.subject
        setField(sub.field.id)
        //setName(userinfo.name)
        //setAge(userinfo.age)
        //setCarrer(userinfo.career)
        //setID(userinfo.username)
        //setPassword(userinfo.password)
      })
      .catch((error) => {
        alert(error.message)
      })
  })

  const UserFunc = (e) => {
    e.preventDefault()
    const formdata = new FormData()
    formdata.append('name', Name)
    formdata.append('username', ID)
    formdata.append('password', Password)
    formdata.append('age', Age)
    formdata.append('career', Carrer)
    formdata.append('fieldId', Field)
    for (let key of formdata.values()) {
      console.log(key)
    }
    console.log(formdata.values())
    const Subjects = `name=${Name}&username=${ID}&age=${Age}&fieldId=${Field}`
    console.log(Subjects)
    axios.defaults.headers.common['Authorization'] = `${token}`
    axios
      .post(`https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subject/${id}`, Subjects, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
        withCredentials: true,
        baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
      })
      .then((response) => {
        console.log(response.data)
        localStorage.removeItem('userinfo')
        window.location.href = '/#/users'
      })
      .catch((error) => {
        alert(error.message)
      })
  }
  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={UserFunc}>
                  <h2>Subject Create</h2>
                  <p className="text-medium-emphasis">Create subject account</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Username"
                      autoComplete="Username"
                      value={Name}
                      onChange={(e) => setName(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="ID"
                      autoComplete="ID"
                      value={ID}
                      onChange={(e) => setID(e.target.value)}
                    />
                  </CInputGroup>
                  {/*
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autoComplete="new-password"
                      value={Password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Repeat password"
                      autoComplete="new-password"
                    />
                  </CInputGroup>
  */}
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Age"
                      autoComplete="Age"
                      value={Age}
                      onChange={(e) => setAge(e.target.value)}
                    />
                  </CInputGroup>
                  {/*
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    
                    <CFormInput
                      placeholder="Field"
                      autoComplete="Field"
                      value={Field}
                      onChange={(e) => setField(e.target.value)}
                    />
                  </CInputGroup>
*/}
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Carrer"
                      autoComplete="Carrer"
                      value={Carrer}
                      onChange={(e) => setCarrer(e.target.value)}
                    />
                  </CInputGroup>
                  <div className="d-grid">
                    <CButton type="submit" color="success">
                      Change Information
                    </CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}
export default Register_info
