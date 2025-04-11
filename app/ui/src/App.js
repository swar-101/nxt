import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
// import Login from './pages/Login';
import Homepage from './pages/Homepage';
import Login from './pages/Login';
// import DesignerDashboard from './pages/DesignerDashboard';

// const PrivateRoute = ({ children }) => {
//   const token = localStorage.getItem('token');
//   return token ? children : <Navigate to="/login" />;
// };

const App = () => {
  return (
    <BrowserRouter>
   <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>
      // <h1>HI ARKAAN</h1>

    );
};

export default App;