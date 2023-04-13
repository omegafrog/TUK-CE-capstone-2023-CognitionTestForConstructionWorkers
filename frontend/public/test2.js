import React, {useState} from 'react';
function item({item}){
    return (
        <div>
            <span>{item.id}</span>
            <span>{item.title}</span>
            <span>{item.createdAt}</span>
        </div>
    )
}
export default item

