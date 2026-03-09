import React from 'react'
import {Routes, Route, Link} from "react-router-dom";
import Home from './Home';
import ModelGuide from './ModelGuide';
import Header from './Header';
import '../style/App.css'


function App(){
    return(
        <div>
			<Header></Header>
			<Routes>
				<Route path='/' element={<Home></Home>}></Route>
				<Route path='/Home' element={<Home></Home>}></Route>
				<Route path='/Guide' element={<ModelGuide></ModelGuide>}></Route>
			</Routes>
		</div>
    );
}

export default App
