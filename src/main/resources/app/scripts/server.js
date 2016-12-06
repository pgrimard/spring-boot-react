import React from 'react';
import {renderToString} from 'react-dom/server';
import {ServerRouter, createServerRenderContext} from 'react-router';
import {createStore, applyMiddleware} from 'redux';
import thunkMiddleware from 'redux-thunk';
import {Provider} from 'react-redux';
import serialize from 'serialize-javascript';
import reducer from './reducers';
import App from 'components/App';

window.render = (template, model, messages) => {
  const context = createServerRenderContext();
  const {req, initialState} = model;
  const store = createStore(reducer, JSON.parse(initialState), applyMiddleware(thunkMiddleware));

  const markup = renderToString(
    <Provider store={store}>
      <ServerRouter location={req.location} context={context}>
        <App/>
      </ServerRouter>
    </Provider>
  );

  return template
    .replace('SERVER_RENDERED_HTML', markup)
    .replace('SERVER_RENDERED_STATE', serialize(JSON.parse(initialState), {isJSON: true}))
    .replace('SERVER_RENDERED_MESSAGES', serialize(messages, {isJSON: true}))
};