import { React, useEffect, useRef, useState } from 'react'
import axios from 'axios'
import {
  CAvatar,
  CBadge,
  CDropdown,
  CDropdownDivider,
  CDropdownHeader,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
} from '@coreui/react'
import {
  cilBell,
  cilCreditCard,
  cilCommentSquare,
  cilEnvelopeOpen,
  cilFile,
  cilLockLocked,
  cilSettings,
  cilTask,
  cilUser,
} from '@coreui/icons'
import CIcon from '@coreui/icons-react'

//import avatar8 from './../../assets/images/avatars/10.jpg'

const changeField = (userid, username, itemid) => {
  const temp1 = sessionStorage.getItem('token')
  console.log(`username: ${username}`)
  const Subjects = `name=${username}&fieldId=${itemid}`
  //admin.append('')
  const result = window.confirm('결과를 적용하시겠습니까?')
  if (result) {
    axios
      .post(
        `https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subject/${userid}`,
        Subjects,
        {
          headers: {
            Authorization: `${temp1}`,
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          },
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      )
      .then((response) => {
        console.log(response)
        window.location.reload()
      }, [])
      .catch((error) => {
        console.log(error)
      })
  } else {
    window.location.href = '/#/users'
  }
}

const Cite_Dropdown = (sitename) => {
  const user = useRef(null)
  const [citeData, setCiteData] = useState([])
  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/site', null, {
        headers: {
          Authorization: '$(temp1)',
        },
      })
      .then((response) => {
        //console.log(response.data.results.fields)
        //cite.current = response.data.results.fields
        setCiteData(response.data.results.fields)
      })
      .catch((error) => {
        console.error(error)
      })
  }, [])
  //console.log(`citedata : ${citeData[0]}`)
  console.log(sitename.userid)
  return (
    <CDropdown /*variant="nav-item"*/>
      <CDropdownToggle className="py-0">{sitename.sitename}</CDropdownToggle>
      <CDropdownMenu className="pt-0" placement="bottom-end">
        <CDropdownHeader className="bg-light fw-semibold py-2">Site</CDropdownHeader>
        {citeData !== null &&
          citeData.map((item) => (
            <CDropdownItem
              key={item.id}
              onClick={() => changeField(sitename.userid, sitename.username, item.id)}
            >
              {item.name}
            </CDropdownItem>
          ))}
      </CDropdownMenu>
    </CDropdown>
  )
}

export default Cite_Dropdown
