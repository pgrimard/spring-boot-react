import React from 'react';
import {renderToString} from 'react-dom/server';
import {ServerRouter, createServerRenderContext} from 'react-router';
import {createStore, applyMiddleware} from 'redux';
import thunkMiddleware from 'redux-thunk'
import {Provider} from 'react-redux';
import reducer from './reducers';
import App from 'components/App';

window.render = (template, model) => {
  const context = createServerRenderContext();
  const req = JSON.parse(model.get('req'));
  const initialState = JSON.parse(model.get('initialState'));

  const store = createStore(reducer, initialState, applyMiddleware(thunkMiddleware));

  const markup = renderToString(
    <Provider store={store}>
      <ServerRouter location={req.location} context={context}>
        <App/>
      </ServerRouter>
    </Provider>
  );

  return template
    .replace('SERVER_RENDERED_HTML', markup)
    .replace('SERVER_RENDERED_STATE', JSON.stringify(initialState));
};