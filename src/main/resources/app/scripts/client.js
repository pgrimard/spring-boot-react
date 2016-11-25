import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter} from 'react-router';
import {createStore, applyMiddleware} from 'redux';
import thunkMiddleware from 'redux-thunk';
import {Provider} from 'react-redux';
import reducer from './reducers';
import App from 'components/App';

const store = createStore(reducer, window.__PRELOADED_STATE__, applyMiddleware(thunkMiddleware));

const markup = (
  <Provider store={store}>
    <BrowserRouter>
      <App/>
    </BrowserRouter>
  </Provider>
);

render(markup, document.getElementById('app'));