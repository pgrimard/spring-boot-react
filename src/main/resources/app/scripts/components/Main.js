import React from 'react';
import {Match, Link, Miss} from 'react-router';
import Home from 'components/Home';
import Child from 'components/Child';
import NotFound from 'components/NotFound';
import 'styles/main.css';

export default function Main() {
  return (
    <div>
      <h1>Hello Server Side Rendering!!</h1>

      <Link to="/">Home</Link>
      <Link to="/child">Child</Link>

      <Match exactly pattern="/" component={Home}/>
      <Match pattern="/child" component={Child}/>

      <Miss component={NotFound}/>
    </div>
  );
}