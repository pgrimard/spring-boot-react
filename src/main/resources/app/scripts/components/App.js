import React from 'react';
import {connect} from 'react-redux';
import {Match, Link, Miss} from 'react-router';
import Home from 'components/Home';
import Child from 'components/Child';
import NotFound from 'components/NotFound';
import 'styles/main.css';

function App({messages}) {
  const {title} = messages;

  return (
    <div>
      <h1>{title}</h1>

      <ul>
        <li><Link to="/">Home</Link></li>
        <li><Link to="/child">Child</Link></li>
      </ul>

      <Match exactly pattern="/" component={Home}/>
      <Match pattern="/child" component={Child}/>

      <Miss component={NotFound}/>
    </div>
  );
}

function mapStateToProps({messages}) {
  return {messages};
}

export default connect(mapStateToProps)(App);