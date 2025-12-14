import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Sweet from './pages/Sweet';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/sweet" element={<Sweet />} />
        {/* Support old/alternate URLs that might be used externally */}
        {/* <Route path="/auth/register" element={<Register />} /> */}
       
      </Routes>
    </BrowserRouter>
  );
}

export default App;