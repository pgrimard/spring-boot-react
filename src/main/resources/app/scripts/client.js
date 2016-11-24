import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter} from 'react-router';
import Main from 'components/Main';

const markup = (
  <BrowserRouter>
    <Main/>
  </BrowserRouter>
);

render(markup, document.getElementById('app'));