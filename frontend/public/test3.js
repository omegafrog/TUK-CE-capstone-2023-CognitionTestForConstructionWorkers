import React from 'react'
import { ReactDOM } from 'react-dom';
import List from './component/list';
import axios from 'axios';
import { useState, useEffect } from 'react';
function test() {
    const list = [
        {id:1, title: "test1", createdAt:"2023.04.13"},
        {id:2, title: "test2", createdAt:"2023.04.14"}
    ]
    return (
        <div>
            <List items={list}/>
        </div>
    );
}
ReactDOM.render(<Test />, document.getElementById('root'));