import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter} from 'react-router';
import App from 'components/App';

const markup = (
  <BrowserRouter>
    <App/>
  </BrowserRouter>
);

render(markup, document.getElementById('app'));