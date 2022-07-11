import './App.css';
import FooterComponent from './components/FooterComponent';
import ProductList from './components/ProductList';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignupForm from './components/SignupForm';
import UserDetails from './components/UserDetails';
import UserDelete from './components/UserDelete';
import UserUpdate from './components/UserUpdate';
import Logout from './components/Logout';
import { PaytmButton } from './components/paytmButton';
import CartListFunction from './components/CartList';
import TodayOrders from './components/TodayOrders';
import AllOrders from './components/AllOrders';
import AdminProductList from './components/AdminProductList';
import LoginPage from './components/LoginForm';
import ProductUpdate from './components/ProductUpdate';
import ProductAdd from './components/ProductAdd';
import HeaderComponent from './components/HeaderComponent';

function App() {

  return (
    <div className="App">
      <Router>
        <div className='contain'>
          <HeaderComponent></HeaderComponent>
          <div className='contain1'>
            <Routes>
              <Route path='/' element={<ProductList />}></Route>
              <Route path='/products' element={<ProductList />}></Route>
              <Route path='/login' element={<LoginPage />}></Route>
              <Route path='/register' element={<SignupForm />}></Route>
              <Route path='/profile' element={<UserDetails />}></Route>
              <Route path='/update' element={<UserUpdate />}></Route>
              <Route path='/delete' element={<UserDelete />}></Route>
              <Route path='/cart' element={<CartListFunction />}></Route>
              <Route path='/logout' element={<Logout />}></Route>
              <Route path='/pay' element={<PaytmButton />}></Route>
              <Route path='/toorder' element={<TodayOrders />}></Route>
              <Route path='/allorder' element={<AllOrders />}></Route>
              <Route path='/admin' element={<AdminProductList />}></Route>
              <Route path='/proUpdate' element={<ProductUpdate />}></Route>
              <Route path='/addPro' element={<ProductAdd />}></Route>

            </Routes>
          </div>
          <FooterComponent />
        </div>
      </Router>
    </div>
  );
}

export default App;
