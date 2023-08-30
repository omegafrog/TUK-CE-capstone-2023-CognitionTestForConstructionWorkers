import React, { useEffect, useState } from 'react'
import { CCard, CCardBody, CCol, CCardHeader, CRow } from '@coreui/react'
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

const Charts = () => {
  const random = () => Math.round(Math.random() * 100)
  const [chart1Data, setChart1Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'User data',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart2Data, setChart2Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'User data',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart3Data, setChart3Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'User data',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart4Data, setChart4Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'User data',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response1 = await axios.get('/dummy2.json')
        const Data1 = response1.data

        const labels = Data1.map((item) => item.date)
        const values = Data1.map((item) => item.value)

        setChart1Data((prevChart1Data) => ({
          ...prevChart1Data,
          labels,
          datasets: [
            {
              ...prevChart1Data.datasets[0],
              data: values,
            },
            prevChart1Data.datasets[1],
          ],
        }))

        const response2 = await axios.get('/standard.json')
        const data2 = response2.data

        const stan1value = data2[0].value
        const stan1data = Array.from({ length: 7 }, () => stan1value)
        const stan2value = data2[1].value
        const stan2data = Array.from({ length: 7 }, () => stan2value)
        const stan3value = data2[2].value
        const stan3data = Array.from({ length: 7 }, () => stan3value)
        const stan4value = data2[3].value
        const stan4data = Array.from({ length: 7 }, () => stan4value)

        setChart1Data((prevChart1Data) => ({
          ...prevChart1Data,
          labels,
          datasets: [
            prevChart1Data.datasets[0],
            {
              ...prevChart1Data.datasets[1],
              data: stan1data,
            },
          ],
        }))

        const response3 = await axios.get('/dummy3.json')
        const Data3 = response3.data

        const labels3 = Data3.map((item) => item.date)
        const values3 = Data3.map((item) => item.value)

        setChart2Data((prevChart2Data) => ({
          ...prevChart2Data,
          labels3,
          datasets: [
            {
              ...prevChart2Data.datasets[0],
              data: values3,
            },
            prevChart2Data.datasets[1],
          ],
        }))

        setChart2Data((prevChart2Data) => ({
          ...prevChart2Data,
          labels,
          datasets: [
            prevChart2Data.datasets[0],
            {
              ...prevChart2Data.datasets[1],
              data: stan2data,
            },
          ],
        }))
        const response4 = await axios.get('/dummy4.json')
        const Data4 = response4.data

        const labels4 = Data4.map((item) => item.date)
        const values4 = Data4.map((item) => item.value)

        setChart3Data((prevChart3Data) => ({
          ...prevChart3Data,
          labels4,
          datasets: [
            {
              ...prevChart3Data.datasets[0],
              data: values4,
            },
            prevChart3Data.datasets[1],
          ],
        }))

        setChart3Data((prevChart3Data) => ({
          ...prevChart3Data,
          labels,
          datasets: [
            prevChart3Data.datasets[0],
            {
              ...prevChart3Data.datasets[1],
              data: stan3data,
            },
          ],
        }))
        const response5 = await axios.get('/dummy5.json')
        const Data5 = response5.data

        const labels5 = Data5.map((item) => item.date)
        const values5 = Data5.map((item) => item.value)

        setChart4Data((prevChart4Data) => ({
          ...prevChart4Data,
          labels5,
          datasets: [
            {
              ...prevChart4Data.datasets[0],
              data: values5,
            },
            prevChart4Data.datasets[1],
          ],
        }))

        setChart4Data((prevChart4Data) => ({
          ...prevChart4Data,
          labels,
          datasets: [
            prevChart4Data.datasets[0],
            {
              ...prevChart4Data.datasets[1],
              data: stan4data,
            },
          ],
        }))
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }
    fetchData()
  }, [])

  return (
    <CRow>
      <CCol xs={12}>
        <CCardHeader>
          <strong>subject_name</strong>
          <p></p>
          <small>subject_information</small>
          <p></p>
        </CCardHeader>
      </CCol>
      <CCol xs={6}>
        <CCard className="mb-4">
          <CCardHeader>test1</CCardHeader>
          <CCardBody>
            <CChartLine
              data={
                chart1Data /*{
                labels: ['6/1', '6/2', '6/3', '6/4', '6/5', '6/6', '6/7'],
                datasets: [
                  {
                    label: 'User data',
                    backgroundColor: 'rgba(151, 187, 205, 0.2)',
                    borderColor: 'rgba(151, 187, 205, 1)',
                    pointBackgroundColor: 'rgba(220, 220, 220, 1)',
                    pointBorderColor: '#fff',
                    data: [],
                  },
                  {
                    label: 'standard',
                    backgroundColor: 'rgba(255, 70, 50, 0.2)',
                    borderColor: 'rgba(255,70, 50, 1)',
                    pointBackgroundColor: 'rgba(151, 187, 205, 1)',
                    pointBorderColor: '#fff',
                    data: [],
                  },
                ],
              }*/
              }
            />
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={6}>
        <CCard className="mb-4">
          <CCardHeader>test2</CCardHeader>
          <CCardBody>
            <CChartLine data={chart2Data} />
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={6}>
        <CCard className="mb-4">
          <CCardHeader>test3</CCardHeader>
          <CCardBody>
            <CChartLine data={chart3Data} />
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={6}>
        <CCard className="mb-4">
          <CCardHeader>test4</CCardHeader>
          <CCardBody>
            <CChartLine data={chart4Data} />
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  )
}

export default Charts
