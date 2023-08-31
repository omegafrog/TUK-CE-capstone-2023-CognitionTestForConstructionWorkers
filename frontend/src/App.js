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
const Register_sub = React.lazy(() => import('./views/pages/register/Register_sub'))
const Register_sub2 = React.lazy(() => import('./views/pages/register/Register_sub_multi2'))
const Register_sub3 = React.lazy(() => import('./views/pages/register/Register_sub_multi3'))
const Page404 = React.lazy(() => import('./views/pages/page404/Page404'))
const Page500 = React.lazy(() => import('./views/pages/page500/Page500'))
const Tables = React.lazy(() => import('./views/pages/tables/Tables2'))
const Chart2 = React.lazy(() => import('./views/pages/detail/Charts'))
const Details = React.lazy(() => import('./views/pages/detail/details'))
const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'))
const Regi_info = React.lazy(() => import('./views/pages/register/Register_info'))
const Regiser_ad = React.lazy(() => import('./views/pages/register/Register_ad'))
const Admins = React.lazy(() => import('./views/pages/tables/Tables_Admin.js'))
const Site = React.lazy(() => import('./views/pages/register/Register_site'))
const Test = React.lazy(() => import('./views/pages/register/file_upload/test'))
const Details_sub = React.lazy(() => import('./views/pages/detail/details_sub'))
class App extends Component {
  render() {
    return (
      <HashRouter>
        <Suspense fallback={loading}>
          <Routes>
            <Route exact path="/register_site" name="new Site page" element={<Site />} />
            <Route exact path="/login_ad" name="Admin Login Page" element={<Login_ad />} />
            <Route exact path="/login_sub" name="Subject Login Page" element={<Login_sub />} />
            <Route exact path="/login" name="Login Page" element={<Login />} />
            <Route exact path="/detail_sub" name="detail page" element={<Details_sub />} />
            <Route exact path="/chart" name="detail page" element={<Chart2 />} />
            <Route exact path="/register_sub" name="Register Page" element={<Register_sub />} />
            <Route
              exact
              path="/register_sub2"
              name="Register Page multi2"
              element={<Register_sub2 />}
            />
            <Route
              exact
              path="/register_sub3"
              name="Register Page multi3"
              element={<Register_sub3 />}
            />
            <Route exact path="/404" name="Page 404" element={<Page404 />} />
            <Route exact path="/500" name="Page 500" element={<Page500 />} />
            <Route exact path="/table" name="Table Page" element={<Tables />} />
            <Route exact path="/detail" name="Detail Page" element={<Details />} />
            <Route exact path="/infor_change" name="information change" element={<Regi_info />} />
            <Route exact path="/register_ad" name="Register Page" element={<Regiser_ad />} />
            <Route exact path="/test" name="test Page" element={<Test />} />
            <Route path="*" name="Home" element={<DefaultLayout />} />
          </Routes>
        </Suspense>
      </HashRouter>
    )
  }
}

export default App
