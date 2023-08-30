import React, { useState, useEffect } from 'react'
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CRow,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableHead,
  CTableHeaderCell,
  CTableRow,
} from '@coreui/react'
import { DocsExample } from 'src/components'
import axios from 'axios'

const Tables2 = () => {
  const [test, settest] = useState([])
  const [data, setData] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [itemsPerPage] = useState(10)
  const token = sessionStorage.getItem('token')
  axios.defaults.headers.common['Authorization'] = `${token}`
  const subjectname = sessionStorage.getItem('subjectname')
  if (subjectname) {
    console.log(subjectname)
    sessionStorage.removeItem('subjectname')
    console.log('subjectName removed from session')
  } else {
    console.log('subjectName not found in session')
  }

  useEffect(() => {
    axios
      .get('/dummy.json')
      .then((response) => {
        setData(response.data)
      })
      .catch((error) => {
        console.error('Error fetching data:', error)
      })
  }, [])

  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subjects', {
        params: {
          curPageNum: 1,
          contentPerPage: 10,
        },
      })
      .then((response) => {
        console.log(response)
      })
      .catch((error) => {
        console.error('Error aws data:', error)
      })
  }, [])

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentItems = data.slice(indexOfFirstItem, indexOfLastItem)

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber)
  }

  const saveSubjectName = (item) => {
    sessionStorage.setItem('subjectname', item.name)
  }
  return (
    <>
      <CRow>
        <CCol xs={12}>
          <CCard className="mb-4">
            <CCardHeader>
              <strong>Subject Table</strong> <small></small>
            </CCardHeader>
            <CCardBody>
              <DocsExample href="components/table">
                <CTable>
                  <CTableHead>
                    <CTableRow>
                      <CTableHeaderCell scope="col">#</CTableHeaderCell>
                      <CTableHeaderCell scope="col">Name</CTableHeaderCell>
                      <CTableHeaderCell scope="col">Age</CTableHeaderCell>
                      <CTableHeaderCell scope="col">Phone Number</CTableHeaderCell>
                    </CTableRow>
                  </CTableHead>
                  <CTableBody>
                    {currentItems.map((item, index) => (
                      <CTableRow scope="col" key={item.num}>
                        <CTableDataCell>{indexOfFirstItem + index + 1}</CTableDataCell>
                        <CTableDataCell>
                          <a href="#/details" onClick={() => saveSubjectName(item)}>
                            {item.name}
                          </a>
                        </CTableDataCell>
                        <CTableDataCell>{item.age}</CTableDataCell>
                        <CTableDataCell>{item.phoneNumber}</CTableDataCell>
                      </CTableRow>
                    ))}
                  </CTableBody>
                </CTable>
                <div>
                  {data.length > itemsPerPage && (
                    <nav>
                      <ul className="pagination">
                        {Array(Math.ceil(data.length / itemsPerPage))
                          .fill()
                          .map((_, index) => (
                            <li
                              key={index}
                              className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}
                            >
                              <button className="page-link" onClick={() => paginate(index + 1)}>
                                {index + 1}
                              </button>
                            </li>
                          ))}
                      </ul>
                    </nav>
                  )}
                </div>
              </DocsExample>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  )
}

export default Tables2
