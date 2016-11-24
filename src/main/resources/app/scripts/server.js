import React from 'react';
import {renderToString} from 'react-dom/server';
import {ServerRouter, createServerRenderContext} from 'react-router';
import App from 'components/App';

window.render = (template, model) => {
  const context = createServerRenderContext();
  const state = JSON.parse(model.get('state'));
  const markup = renderToString(
    <ServerRouter location={state.location} context={context}>
      <App/>
    </ServerRouter>
  );
  return template.replace('{{output}}', markup);
};