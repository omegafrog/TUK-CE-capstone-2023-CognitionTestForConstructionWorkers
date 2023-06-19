import React, { Component, Suspense } from 'react'
import { HashRouter, Route, Routes } from 'react-router-dom'
import './scss/style.scss'

const loading = (
  <div className="pt-3 text-center">
    <div className="sk-spinner sk-spinner-pulse"></div>
  </div>
)

// Containers
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'))

// Pages
const Login_ad = React.lazy(() => import('./views/pages/login/Login_admin'))
const Login_sub = React.lazy(() => import('./views/pages/login/Login_subject'))
const Login = React.lazy(() => import('./views/pages/login/Login'))
const Register = React.lazy(() => import('./views/pages/register/Register'))
const Page404 = React.lazy(() => import('./views/pages/page404/Page404'))
const Page500 = React.lazy(() => import('./views/pages/page500/Page500'))
const Tables2 = React.lazy(() => import('./views/pages/tables/Tables2'))
const Chart2 = React.lazy(() => import('./views/pages/detail/Charts'))
const Details = React.lazy(() => import('./views/pages/detail/details'))
class App extends Component {
  render() {
    return (
      <HashRouter>
        <Suspense fallback={loading}>
          <Routes>
            <Route exact path="/login_ad" name="Admin Login Page" element={<Login_ad />} />
            <Route exact path="/login_sub" name="Subject Login Page" element={<Login_sub />} />
            <Route exact path="/login" name="Login Page" element={<Login />} />
            <Route exact path="/chart" name="detail page" element={<Chart2 />} />
            <Route exact path="/register" name="Register Page" element={<Register />} />
            <Route exact path="/404" name="Page 404" element={<Page404 />} />
            <Route exact path="/500" name="Page 500" element={<Page500 />} />
            <Route exact path="/table" name="Table Page" element={<Tables2 />} />
            <Route exact path="/detail" name="Detail Page" element={<Details />} />
            <Route path="*" name="Home" element={<DefaultLayout />} />
          </Routes>
        </Suspense>
      </HashRouter>
    )
  }
}

export default App
