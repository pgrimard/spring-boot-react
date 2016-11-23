import React from 'react';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import App from 'components/App';
import Main from 'components/Main';

export default (
  <Router history={browserHistory}>
    <Route path="/" component={App}>
      <Route path="hello" components={Main}/>
    </Route>
  </Router>
);