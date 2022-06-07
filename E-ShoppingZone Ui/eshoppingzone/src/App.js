import './App.css';
import './css/home.css';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import ProductList from './components/ProductList';
import {BrowserRouter as Router,Route,Routes} from 'react-router-dom';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignupForm';
import UserProfile from './components/UserProfile';

function App() {
  return (
    <div className="App">
      <Router>
        <div className='contain'>
          <HeaderComponent/>
          <div className='contain1'>
            <Routes>
              <Route path='/' element={<ProductList/>}></Route>
              <Route path='/products' element={<ProductList/>}></Route>
              <Route path='/login' element={<LoginForm/>}></Route>
              <Route path='/register' element={<SignupForm/>}></Route>
              <Route path='/profile' element={<UserProfile/>}></Route>


            </Routes>  
          </div>  
          <FooterComponent/> 
        </div>
      </Router>  
    </div>
  );
}

export default App;
